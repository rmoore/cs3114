import java.util.List;

/**
 * The Fly Weight is a leaf that doesn't have any data. All leaves of the tree
 * that are empty point to a singleton instance of this class.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 */
public class PRQuadTreeFlyWeight extends PRQuadTreeLeaf {
	private MemoryManager mem;
	
	/**
	 * Instantiate the flyweight and write it to disk
	 */
	public PRQuadTreeFlyWeight(MemoryManager mem)
	{
		this.mem = mem;
	}
	
	/**
	 * Insertions to a Fly Weight return a newly instantiated leaf node.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param data The data to insert.
	 * @param ul_x The x coordinate of the upper left point
	 * @param ul_y The y coordinate of the upper left point
	 * @param size The size of this node.
	 * @return a new leaf node with the data inserted.
	 */
	public Handle insert(int x, int y, Handle data, int ul_x, int ul_y, int size)
	{
		PRQuadTreeNode leaf = new PRQuadTreeLeaf(mem);
		return leaf.insert(x, y, data, ul_x, ul_y, size);
	}
	
	/**
	 * Trying to remove something from the fly weight always returns the fly
	 * weight and sets the data returned to null.
	 * @param x _
	 * @param y _
	 * @param data A pointer to a one sized array of T.
	 * @return the flyweight
	 */
	public Handle remove(int x, int y, Handle[] data)
	{
		data[0] = null;
		return getHandle();
	}
	
	/**
	 * There are no points in the flyweight to find...
	 */
	@Override
	public int radius_search(int x, int y, int radius, List<Handle> list, int ul_x, int ul_y, int size) {
		return 1;
	}
	
	/**
	 * Return the symbol for the flyweight
	 * @param <T> The type of the data stored in the tree
	 * @return "E"
	 */
	public String toString()
	{
		return "*";
	}
	
	public Handle getHandle()
	{
		return new Handle(-1);
	}
}
