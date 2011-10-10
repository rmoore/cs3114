public interface PRQuadTreeNode<T> {

	/**
	 * Insert a new object into this PR Quad Tree Node.
	 * 
	 * @param x The x coordinate of the value.
	 * @param y The y coordinate of the value.
	 * @param val The value to insert.
	 * @return if the insertion was successful.
	 */
	public abstract boolean insert(int x, int y, T val);

	/**
	 * Get an object from the node.
	 * No error checking is make to ensure that this is a valid value, if it
	 * is not, null will be returned.
	 * 
	 * @param x The x coordinate of the value to look for.
	 * @param y The y coordinate of the value to look for.
	 * @return The value stored at (x,y).
	 */
	public abstract T get(int x, int y);

	/**
	 * Remove an object from this node and return the removed object.
	 * Returns null if no object could be found.
	 * 
	 * @param x The x coordinate of the value to look for.
	 * @param y The y coordinate of the value to look for.
	 * @return The value removed.
	 */
	public abstract T remove(int x, int y);

	/**
	 * Does this QuadTreeNode have an object with coordinates (x,y)?
	 * 
	 * @param x The x coordinate to look for.
	 * @param y The y coordinate to look for.
	 * @return if this node contains the a value with those coordinates.
	 */
	public abstract boolean contains(int x, int y);
	
	/**
	 * Return a count of the number of object stored in the node.
	 * 
	 * @return The number of objects stored in the node.
	 */
	public abstract int size();
	
	/**
	 * Get an array of 3-tuples representing all points stored in this node.
	 * @return All points in this node as [(int, int, T)].
	 */
	public abstract Triple<Integer, Integer, T>[] getPoints() ;

}