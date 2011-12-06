import java.util.List;

/**
 * These are the leaves of the Quad Tree. Unlike internal nodes, Leaves don't
 * have children, but they do have data.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 */
public class PRQuadTreeLeaf extends PRQuadTreeNode {
	public static final int MAX_ENTRIES = 3;
	private ArrayList<Triple<Integer, Integer, Handle>> points;
	
	/**
	 * Instantiate a new empty PRQuadTree Leaf.
	 */
	public PRQuadTreeLeaf()
	{
		points = new ArrayList<Triple<Integer, Integer, Handle>>();
	}
	
	/**
	 * The size of a leaf node is the number of points it contains.
	 * @return the number of points in this leaf.
	 */
	public int size() {
		return points.size();
	}
	
	public PRQuadTreeNode insert(int x, int y, Handle data, int ul_x, int ul_y, int size)
	{
		// Make sure that we don't already have a point like that
		for (Triple<Integer, Integer, Handle> point : points) {
			if ((point.getFirst() == x) && (point.getSecond() == y)) {
				throw new DuplicateEntryException();
			}
		}
		
		if (points.size() == MAX_ENTRIES) {
			// Make a new Internal node.
			PRQuadTreeNode internal = new PRQuadTreeInternal();
			
			// Insert all the old stuff
			for (Triple<Integer, Integer, Handle> point : points) {
				internal = internal.insert(point.getFirst(), point.getSecond(), point.getThird(),
										   ul_x, ul_y, size);
			}
			
			// Insert the new point.
			internal = internal.insert(x, y, data, ul_x, ul_y, size);
			
			// Return the internal node.
			return internal;
		} else {
			points.add(new Triple<Integer, Integer, Handle>(x, y, data));
			return this;
		}
	}
	
	/**
	 * Remove a point from this node. Returns the data that was removed in the
	 * pointer data. Returns what this node looks like afterwards
	 * @param x The x coordinate of the point to remove.
	 * @param y The y coordinate of the point to remove.
	 * @param data A pointer to a data object array of size 1.
	 * @param ul_x The upper left hand point's x component
	 * @param ul_y The upper left hand point's y component
	 * @param size The size of this node.
	 * @return What the leaf looks like afterwards.
	 */
	public PRQuadTreeNode remove(int x, int y, Handle[] data, int ul_x, int ul_y, int size)
	{
		data[0] = null;
		// Set the data and remove the point (if found).
		for (int i = 0; i < points.size(); i++) {
			Triple<Integer, Integer, Handle> point = points.get(i);
			if ((point.getFirst() == x) && (point.getSecond() == y)) {
				points.remove(i);
				data[0] = point.getThird();
				break;
			}
		}
		
		// return what the node looks like afterwards.
		if (points.isEmpty()) {
			return PRQuadTreeFlyWeight.getFlyWeight();
		} else {
			return this;
		}
	}
	
	/**
	 * Return a list of the points in the leaf. This list does not back
	 * the node and changes to it do not change anything.
	 * @return a list of the points contained in this leaf.
	 */
	public List<Triple<Integer, Integer, Handle>> getPoints()
	{
		List<Triple<Integer, Integer, Handle>> list = new ArrayList<Triple<Integer, Integer, Handle>>();
		for (Triple<Integer, Integer, Handle> point : points) {
			list.add(point);
		}
		return list;
	}
	
	/**
	 * Return the string representation of the points in this leaf.
	 * @return This node as a string.
	 */
	public String toString()
	{
		String str = "";
		for (Triple<Integer, Integer, Handle> point : getPoints()) {
			str += point.toString();
		}
		str += "|";
		return str;
	}

	@Override
	public int radius_search(int x, int y, int radius, List<Handle> list, int ul_x, int ul_y, int size) {
		for (Triple<Integer, Integer, Handle> point : points) {
			if (CircleSquare.cir_cont(x, y, radius, point.getFirst(), point.getSecond())) {
				list.add(point.getThird());
			}
		}
		return 1;
	}

}
