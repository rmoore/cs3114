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
	//private ArrayList<Triple<Integer, Integer, Handle>> points;
	private Handle self;
	private Handle[] points = new Handle[3];
	private int size;
	private MemoryManager mem;
	
	/**
	 * Instantiate a new empty PRQuadTree Leaf.
	 */
	public PRQuadTreeLeaf(MemoryManager mem)
	{
		this.mem = mem;
		this.size = 0;	
		save();
	}
	
	/**
	 * Constructor for the FlyWeight
	 */
	public PRQuadTreeLeaf() { /* DO NOTHING */ }
	
	/**
	 * Instantiate a PRQuadTree by loading from memory.
	 * @param mem The memory manager to use
	 * @param handle The handle to load.
	 */
	public PRQuadTreeLeaf(MemoryManager mem, Handle handle)
	{
		this.self = handle;
		this.mem = mem;
		
		// Retrieve the data
		byte[] data = new byte[14];
		mem.get(handle, data, 14);
		
		this.size = data[1];
		
		byte[] p0_b = {data[2], data[3], data[4], data[5]};
		int p0 = IntegerBytes.intFromBytes(p0_b);
		this.points[0] = (p0 >= 0) ? new Handle(p0) : null;
		
		byte[] p1_b = {data[6], data[7], data[8], data[9]};
		int p1 = IntegerBytes.intFromBytes(p1_b);
		this.points[1] = (p1 >= 0) ? new Handle(p1) : null;
		
		byte[] p2_b = {data[10], data[11], data[12], data[13]};
		int p2 = IntegerBytes.intFromBytes(p2_b);
		this.points[2] = (p2 >= 0) ? new Handle(p2) : null;
	}
	
	/**
	 * The size of a leaf node is the number of points it contains.
	 * @return the number of points in this leaf.
	 */
	public int size() {
		return size;
	}
	
	public Handle insert(int x, int y, Handle data, int ul_x, int ul_y, int size)
	{	
		// Make sure that we don't already have a point like that
		for (Handle h : points) {
			if (h == null) continue; 
			City c = City.deref(mem, h);
			if ((c.getX() == x) && (c.getY() == y)) {
				throw new DuplicateEntryException();
			}
		}
		
		if (size == MAX_ENTRIES) {
			// Make a new Internal node.
			PRQuadTreeNode internal = new PRQuadTreeInternal(mem);
			Handle i_han = internal.getHandle();
			
			// Insert all the old stuff
			for (Handle h : points) {
				City c = City.deref(mem, h);
				i_han = internal.insert(c.getX(), c.getY(), h, ul_x, ul_y, size);
				internal = PRQuadTreeNode.deref(mem, i_han);
			}
			
			// Insert the new point.
			i_han = internal.insert(x, y, data, ul_x, ul_y, size);
			
			// Delete ourself
			mem.remove(self);
			
			// Return the internal node.
			return i_han;
		} else {
			for (int i = 0; i < MAX_ENTRIES; i++) {
				if (points[i] == null) {
					points[i] = data;
					break;
				}
			}
				
			size++;
			save();
			return self;
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
	public Handle remove(int x, int y, Handle[] data, int ul_x, int ul_y, int size)
	{
		data[0] = null;		
		for (int i = 0; i < MAX_ENTRIES; i++) {
			Handle h = points[i];
			if (h == null) continue;
			City c = City.deref(mem, h);
			
			if ((c.getX() == x) && (c.getY() == y)) {
				points[i] = null;
				size--;
				data[0] = h;
				break;
			}
		}
		
		// return what the node looks like afterwards.
		if (size == 0) {
			// Delete ourself
			mem.remove(self);
			return new Handle(-1);
		} else {
			return self;
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
		for (Handle h : points) {
			if (h == null) continue;
			City c = City.deref(mem, h);
			list.add(new Triple<Integer, Integer, Handle>(c.getX(), c.getY(), h));
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
			str += point.getFirst() + ",";
			str += point.getSecond() + ",";
			str += City.deref(mem, point.getThird()).getName() + ":";
		}
		str += "|";
		return str;
	}

	@Override
	public int radius_search(int x, int y, int radius, List<Handle> list, int ul_x, int ul_y, int size) {
		for (Handle h : points) {
			if (h == null) continue;
			City c = City.deref(mem, h);
			if (CircleSquare.cir_cont(x, y, radius, c.getX(), c.getY())) {
				list.add(h);
			}
		}
		return 1;
	}
	
	private void save()
	{
		byte[] data = new byte[14];
		data[0] = PRQuadTreeNode.LEAF;
		data[1] = (byte) size;
		
		for (int i = 0; i < MAX_ENTRIES; i++) {
			int p = (points[i] == null) ? -1 : points[i].getOffset();
			byte[] p_b = IntegerBytes.bytesFromInt(p);
			for (int j = 0; j < 4; j++) {
				data[2 + (4*i) + j] = p_b[j];
			}
		}
		
		if (self == null) {
			self = mem.insert(data, 14);
		} else {
			mem.update(self, data, 14);
		}
		
	}

	@Override
	public Handle getHandle() {
		return self;
	}

}
