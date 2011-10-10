/**
 * The Fly Weight is a leaf that doesn't have any data. All leaves of the tree
 * that are empty point to a singleton instance of this class.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 *
 * @param <T> The type of the data stored in the tree
 */
public class PRQuadTreeFlyWeight<T> extends PRQuadTreeLeaf<T> {
	@SuppressWarnings("rawtypes")
	private static PRQuadTreeFlyWeight instance = new PRQuadTreeFlyWeight();
	
	/**
	 * Get a reference to the flyweight.
	 */
	@SuppressWarnings("unchecked")
	public static <T> PRQuadTreeFlyWeight<T> getFlyWeight()
	{
		return (PRQuadTreeFlyWeight<T>)instance;
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
	public PRQuadTreeNode<T> insert(int x, int y, T data, int ul_x, int ul_y, int size)
	{
		PRQuadTreeNode<T> leaf = new PRQuadTreeLeaf<T>();
		return leaf.insert(x, y, data, ul_x, ul_y, size);
	}
}
