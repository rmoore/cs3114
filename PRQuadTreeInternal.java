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
	private Handle[] child;
	private MemoryManager mem;
	private Handle self;
	
	// Enumerate the Children
	public static final int NW = 0;
	public static final int NE = 1;
	public static final int SW = 2;
	public static final int SE = 3;
	
	/**
	 * Instantiate a new Internal node that only has Fly Weight children.
	 */
	public PRQuadTreeInternal(MemoryManager mem)
	{
		this.mem = mem;
		Handle fw = new Handle(-1);
		child[NW] = fw;
		child[NE] = fw;
		child[SW] = fw;
		child[SE] = fw;
		save();
	}
	
	/**
	 * Deserialize a PRQuadTreeInternal from a byte array.
	 * @param mem    The memory manager to use.
	 * @param handle The handle to read from.
	 */
	public PRQuadTreeInternal(MemoryManager mem, Handle handle)
	{
		this.self = handle;
		this.mem = mem;
		
		// Retrieve the data
		byte[] data = new byte[17];
		mem.get(handle, data, 17);
		
		byte[] nw = {data[1], data[2], data[3], data[4]};
		byte[] ne = {data[5], data[6], data[7], data[8]};
		byte[] sw = {data[9], data[10], data[11], data[12]};
		byte[] se = {data[13], data[14], data[15], data[16]};
		
		child = new Handle[4];
		child[NW] = new Handle(IntegerBytes.intFromBytes(nw));
		child[NE] = new Handle(IntegerBytes.intFromBytes(ne));
		child[SW] = new Handle(IntegerBytes.intFromBytes(sw));
		child[SE] = new Handle(IntegerBytes.intFromBytes(se));	
	}
	
	public Handle insert(int x, int y, Handle data, int ul_x, int ul_y, int size)
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
		child[quad] = getChild(quad).insert(x, y, data, ul_x + dx, ul_y + dy, size/2);
		save();
		return self;
	}
	
	public Handle remove(int x, int y, Handle[] data, int ul_x, int ul_y, int size)
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
		child[quad] = getChild(quad).remove(x, y, data, ul_x + dx, ul_y + dy, size/2);
		
		// If we have less than 4 items total, condense to a single Leaf node.
		if (size() < 4) {
			PRQuadTreeNode leaf = new PRQuadTreeLeaf();
			for( Triple<Integer, Integer, Handle> point : getPoints()) {
				leaf.insert(point.getFirst(), point.getSecond(), point.getThird(), ul_x, ul_y, size);
			}
			
			// Delete ourself
			mem.remove(self);
			
			return leaf.getHandle();
		} else {
			save();
			return self;
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
		for (int i = 0; i < 4; i++) { size += getChild(i).size(); }
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
			list.addAll(getChild(i).getPoints());
		}
		return list;
	}
	
	/**
	 * Return a string representation of this internal node.
	 * @return a string representation of the internal node.
	 */
	public String toString()
	{
		String str = "(";
		for (int i = 0; i < 4; i++) {
			str += child[i].toString();
		}
		str += ")";
		return str;
	}

	@Override
	public int radius_search(int x, int y, int radius, List<Handle> list, int ul_x, int ul_y, int size) {
		int sum = 1;
		if (CircleSquare.intersection(ul_x, ul_y, size/2, x, y, radius)) {
			sum += getChild(NW).radius_search(x, y, radius, list, ul_x, ul_y, size/2);
		}
		if (CircleSquare.intersection(ul_x + size/2, ul_y, size/2, x, y, radius)) {
			sum += getChild(NE).radius_search(x, y, radius, list, ul_x + size/2, ul_y, size/2);
		}
		if (CircleSquare.intersection(ul_x, ul_y + size/2, size/2, x, y, radius)) {
			sum += getChild(SW).radius_search(x, y, radius, list, ul_x, ul_y + size/2, size/2);
		}
		if (CircleSquare.intersection(ul_x + size/2, ul_y + size/2, size/2, x, y, radius)) {
			sum += getChild(SE).radius_search(x, y, radius, list, ul_x + size/2, ul_y + size/2, size/2);
		}
		return sum;
	}
	
	private PRQuadTreeNode getChild(int quad)
	{
		return PRQuadTreeNode.deref(mem, child[quad]);
	}
	
	private void save()
	{
		byte[] data = new byte[17];
		data[0] = PRQuadTreeNode.INTERNAL;

		for (int i = 0; i < 4; i++) {
			byte[] bytes = IntegerBytes.bytesFromInt(child[i].getOffset());
			for (int j = 0; j < 4; j++) {
				data[1 + i + j] = bytes[j];
			}
		}
		
		if (self == null) {
			self = mem.insert(data, 17);
		} else {
			mem.update(self, data, 17);
		}
	}

	@Override
	public Handle getHandle() {
		return self;
	}
}
