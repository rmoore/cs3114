/**
 * The internal nodes of a PR QuadTree
 * Normally I would create this as a private inner class, but this class does
 * implement enough functionality that it can stand on its own. Furthermore,
 * it does not need access to anything else in the PRQuadTree class, so it 
 * does not have to have access there.
 * 
 * The PRQuadTreeNode implemented here has a bag that it can store up to 3 
 * objects in. Trying to add more than 3 objects to this node causes an 
 * exception to be thrown.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.09
 *
 * @param <T> The type of the thing stored in the Node.
 */
public class PRQuadTreeLeafNode<T> implements PRQuadTreeNode<T> {
	private int num_stored;
	private int[] x_ary;
	private int[] y_ary;
	private T[] val_ary;
	private boolean[] used;
	
	// How many objects are we allowing each node to store?
	public static final int MAX_OBJECTS = 3;
	
	/**
	 * Instantiate a new PRQuadTree
	 */
	@SuppressWarnings("unchecked")
	public PRQuadTreeLeafNode()
	{
		num_stored = 0;
		x_ary = new int[MAX_OBJECTS];
		y_ary = new int[MAX_OBJECTS];
		val_ary = (T[])(new Object[MAX_OBJECTS]);
		used = new boolean[MAX_OBJECTS]; // Initialized to all false.
	}
	
	/**
	 * Insert a new object into this PR Quad Tree Node.
	 * 
	 * @param x The x coordinate of the value.
	 * @param y The y coordinate of the value.
	 * @param val The value to insert.
	 * @return if the insertion was successful.
	 */
	@Override
	public boolean insert(int x, int y, T val)
	{
		// Make sure there is free space
		if (num_stored == MAX_OBJECTS) {
			throw new ArrayStoreException("QuadTree Node out of Space.");
		}
		
		// Check if we already have an object with this (x,y)
		if (contains(x, y)) {
			return false;
		}
		
		// Find the first free index
		int index = -1;
		for (int i = 0; i < MAX_OBJECTS; i++) {
			if (!used[i]) {
				index = i;
				break;
			}
		}
		
		// Insert the data and mark as used;
		x_ary[index] = x;
		y_ary[index] = y;
		val_ary[index] = val;
		used[index] = true;
		num_stored++;
		
		// success
		return true;
	}
	
	/**
	 * Get an object from the node.
	 * No error checking is make to ensure that this is a valid value, if it
	 * is not, null will be returned.
	 * 
	 * @param x The x coordinate of the value to look for.
	 * @param y The y coordinate of the value to look for.
	 * @return The value stored at (x,y).
	 */
	@Override
	public T get(int x, int y)
	{
		return val_ary[find(x, y)];
	}
	
	/**
	 * Remove an object from this node and return the removed object.
	 * Returns null if no object could be found.
	 * 
	 * @param x The x coordinate of the value to look for.
	 * @param y The y coordinate of the value to look for.
	 * @return The value removed.
	 */
	@Override
	public T remove(int x, int y)
	{
		int index = find(x, y);
		if (index < 0) { return null; }
		
		// Retrieve the value
		T val = val_ary[index];
		
		// Mark that index unused and update the counter
		used[index] = false;
		num_stored--;
		
		// Return the value.
		return val;
	}
	
	/**
	 * Does this QuadTreeNode have an object with coordinates (x,y)?
	 * 
	 * @param x The x coordinate to look for.
	 * @param y The y coordinate to look for.
	 * @return if this node contains the a value with those coordinates.
	 */
	@Override
	public boolean contains(int x, int y)
	{
		return (find(x, y) >= 0);
	}
	
	/**
	 * Return a count of the number of object stored in the node.
	 * 
	 * @return The number of objects stored in the node.
	 */
	@Override
	public int size()
	{
		return num_stored;
	}
	
	/**
	 * Get an array of 3-tuples representing all points stored in this node.
	 * @return All points in this node as [(int, int, T)].
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Triple<Integer, Integer, T>[] getPoints() 
	{
		// Allocate an array of the appropriate size.
		Triple<Integer, Integer, T>[] points;
		points = (Triple<Integer, Integer, T>[])(new Object[num_stored]);
		
		int index = 0;
		for( int i = 0; i < MAX_OBJECTS; i++ ) {
			if (used[i]) {
				points[index] = new Triple<Integer, Integer, T>(x_ary[i], y_ary[i], val_ary[i]);
				index++;
			}
		}
		
		return points;
	}	
	
	/**
	 * Find the index of the item in the node that has coordinates (x,y). If 
	 * no such index exists, return -1.
	 * 
	 * @param x The x coordinate to look for.
	 * @param y The y coordinate to look for.
	 * @return The index that was found, or -1.
	 */
	private int find(int x, int y)
	{
		for (int i = 0; i < MAX_OBJECTS; i++) {
			if ((!used[i]) && (x_ary[i] == x) && (y_ary[i] == y)) {
				return i;
			}
		}
		return -1;
	}
	
}
