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
}
