/**
 * These are the leaves of the Quad Tree. Unlike internal nodes, Leaves don't
 * have children, but they do have data.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 *
 * @param <T> The type of the data stored in this leaf node.
 */
public class PRQuadTreeLeaf<T> extends PRQuadTreeNode<T> {
	public static final int MAX_ENTRIES = 3;
	private ArrayList<Triple<Integer, Integer, T>> points;
	
	/**
	 * Instantiate a new empty PRQuadTree Leaf.
	 */
	public PRQuadTreeLeaf()
	{
		points = new ArrayList<Triple<Integer, Integer, T>>();
	}
	
	public PRQuadTreeNode<T> insert(int x, int y, T data, int ul_x, int ul_y, int size)
	{
		// Make sure that we don't already have a point like that
		for (Triple<Integer, Integer, T> point : points) {
			if ((point.getFirst() == x) && (point.getSecond() == y)) {
				throw new DuplicateEntryException();
			}
		}
		
		if (points.size() == MAX_ENTRIES) {
			// Make a new Internal node.
			PRQuadTreeNode<T> internal = new PRQuadTreeInternal<T>();
			
			// Insert all the old stuff
			for (Triple<Integer, Integer, T> point : points) {
				internal = internal.insert(point.getFirst(), point.getSecond(), point.getThird(),
										   ul_x, ul_y, size);
			}
			
			// Insert the new point.
			internal = internal.insert(x, y, data, ul_x, ul_y, size);
			
			// Return the internal node.
			return internal;
		} else {
			points.add(new Triple<Integer, Integer, T>(x, y, data));
			return this;
		}
	}

}
