import java.util.List;

/**
 * An implementation of the Point Range Quad Tree Data Structure.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 */
public class PRQuadTree<T> implements QuadTree<T> {

	/**
	 * Insert data into the Quad Tree
	 * @param x The X coordinate of the data to insert.
	 * @param y The Y coordinate of the data to insert.
	 * @param data The data to insert.
	 * @return The success or failure of insertion.
	 */
	@Override
	public boolean insert(int x, int y, T data) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Remove some data from the Quad Tree
	 * @param x The X coordinate of the data to remove.
	 * @param y The Y coordinate of the data to remove.
	 * @return The removed data or null.
	 */
	@Override
	public T remove(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retrieve some data from the Quad Tree
	 * @param x The X coordinate of the data to retrieve.
	 * @param y The Y coordinate of the data to retrieve.
	 * @return The retrieved data or null.
	 */
	@Override
	public T get(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Perform a recursive radius search on the Quad Tree and return an 
	 * ArrayList of all points in the quad tree that are within the circle
	 * generated by point (x,y) and radius.
	 * @param x The X coordinate of the center of the circle.
	 * @param y The Y coordinate of the center of the circle.
	 * @param radius The radius of the circle centered at (x,y).
	 * @param list The list to insert the found points into.
	 * @return The number of nodes examined in the search.
	 */
	@Override
	public int radius_search(int x, int y, int radius, List<T> list) {
		// TODO Auto-generated method stub
		return 0;
	}

}
