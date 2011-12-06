import java.util.List;

/**
 * These internal nodes to the Quad Tree don't have data, but they have
 * four children. 
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 */
public class PRQuadTreeInternal extends PRQuadTreeNode {
	private PRQuadTreeNode[] child;
	
	// Enumerate the Children
	public static final int NW = 0;
	public static final int NE = 1;
	public static final int SW = 2;
	public static final int SE = 3;
	
	/**
	 * Instantiate a new Internal node that only has Fly Weight children.
	 */
	public PRQuadTreeInternal()
	{
		child = (PRQuadTreeNode[])(new PRQuadTreeNode[4]);
		child[NW] = PRQuadTreeFlyWeight.getFlyWeight();
		child[NE] = PRQuadTreeFlyWeight.getFlyWeight();
		child[SW] = PRQuadTreeFlyWeight.getFlyWeight();
		child[SE] = PRQuadTreeFlyWeight.getFlyWeight();
	}
	
	public PRQuadTreeNode insert(int x, int y, Handle data, int ul_x, int ul_y, int size)
	{
		int quad;
		int dx;
		int dy;
		
		// Determine the quadrant to insert into.
		Triple<Integer, Integer, Integer> q_data = decompose(x, y, ul_x, ul_y, size);
		quad = q_data.getFirst();
		dx = q_data.getSecond();
		dy = q_data.getThird();
		
		// Do the insert
		child[quad] = child[quad].insert(x, y, data, ul_x + dx, ul_y + dy, size/2);
		return this;
	}
	
	public PRQuadTreeNode remove(int x, int y, Handle[] data, int ul_x, int ul_y, int size)
	{
		int quad;
		int dx;
		int dy;
		
		// Determine the quadrant to insert into.
		Triple<Integer, Integer, Integer> q_data = decompose(x, y, ul_x, ul_y, size);
		quad = q_data.getFirst();
		dx = q_data.getSecond();
		dy = q_data.getThird();
		
		// Do the remove
		child[quad] = child[quad].remove(x, y, data, ul_x + dx, ul_y + dy, size/2);
		
		// If we have less than 4 items total, condense to a single Leaf node.
		if (size() < 4) {
			PRQuadTreeNode leaf = new PRQuadTreeLeaf();
			for( Triple<Integer, Integer, Handle> point : getPoints()) {
				leaf.insert(point.getFirst(), point.getSecond(), point.getThird(), ul_x, ul_y, size);
			}
			return leaf;
		} else {
			return this;
		}
		
	}
	
	/**
	 * Return a triple representing the quadrant, the dx, and the dy of a point 
	 * decomposed based on the upper left point (ul_x,ul_y) and the size of the
	 * quad tree internal node. 
	 * 
	 * @param x The x point to place
	 * @param y The y point to place
	 * @param ul_x The x coordinate of the upper left hand point
	 * @param ul_y The y coordinate of the upper left hand point
	 * @param size The size of this internal node
	 * @return (quadrant, dx, dy)
	 */
	private Triple<Integer, Integer, Integer> decompose(int x, int y, int ul_x, int ul_y, int size)
	{
		int quad;
		int dx;
		int dy;
		
		// Determine the quadrant to insert into.
		if (x < (ul_x + (size/2))) {
			dx = 0;
			if (y < (ul_y + (size/2))) {
				dy = 0;
				quad = NW;
			} else {
				dy = (size/2);
				quad = SW;
			}
		} else {
			dx = (size/2);
			if (y < (ul_y + (size/2))) {
				dy = 0;
				quad = NE;
			} else {
				dy = (size/2);
				quad = SE;
			}
		}
		
		return new Triple<Integer, Integer, Integer>(quad, dx, dy);
	}
	
	/**
	 * The size of an internal node is the sum of the child nodes.
	 * @return the combined sum of the child nodes.
	 */
	public int size()
	{
		int size = 0;
		for (int i = 0; i < 4; i++) { size += child[i].size(); }
		return size;
	}
	
	/**
	 * Get a list of the points contained in the children of this internal node.
	 * @return A list of the points in the children of this list.
	 */
	public List<Triple<Integer, Integer, Handle>> getPoints()
	{
		List<Triple<Integer, Integer, Handle>> list = new ArrayList<Triple<Integer, Integer, Handle>>();
		for (int i = 0; i < 4; i++) {
			list.addAll(child[i].getPoints());
		}
		return list;
	}
	
	/**
	 * Return a string representation of this internal node.
	 * @return a string representation of the internal node.
	 */
	public String toString()
	{
		String str = "I";
		for (int i = 0; i < 4; i++) {
			str += child[i].toString();
		}
		return str;
	}

	@Override
	public int radius_search(int x, int y, int radius, List<Handle> list, int ul_x, int ul_y, int size) {
		int sum = 1;
		if (CircleSquare.intersection(ul_x, ul_y, size/2, x, y, radius)) {
			sum += child[NW].radius_search(x, y, radius, list, ul_x, ul_y, size/2);
		}
		if (CircleSquare.intersection(ul_x + size/2, ul_y, size/2, x, y, radius)) {
			sum += child[NE].radius_search(x, y, radius, list, ul_x + size/2, ul_y, size/2);
		}
		if (CircleSquare.intersection(ul_x, ul_y + size/2, size/2, x, y, radius)) {
			sum += child[SW].radius_search(x, y, radius, list, ul_x, ul_y + size/2, size/2);
		}
		if (CircleSquare.intersection(ul_x + size/2, ul_y + size/2, size/2, x, y, radius)) {
			sum += child[SE].radius_search(x, y, radius, list, ul_x + size/2, ul_y + size/2, size/2);
		}
		return sum;
	}
}
