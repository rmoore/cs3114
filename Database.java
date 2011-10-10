/**
 * This is the primary database class for the PRprog project.
 * 
 * This class implements the interface that the user lays out in the control
 * file. This class then converts the input arguments into the proper internal
 * classes, and inserts them in to the internal data storage structures.
 * 
 * All of these classes are typed void. They 'return' their value to stdout.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.09
 */
public class Database {
	private PRQuadTree<City> quadtree;
	private BinarySearchTree<String, City> bst;
	
	/**
	 * Instantiate a new Database instance.
	 */
	public Database()
	{
		quadtree = new PRQuadTree<City>(1 << 14);
		bst = new BinarySearchTree<String, City>();
	}
	
	/**
	 * Insert a new record into the database
	 * @param x The x coordinate of the city to insert
	 * @param y the y coordinate of the city to insert
	 * @param name The name of the city to insert
	 */
	public void insert( Integer x, Integer y, String name )
	{
		// Make sure x and y are valid.
		if ((x < 0) || (x > (1<<14))) {
			System.out.println(OutputMessages.InsertBadX);
			return;
		}
		if ((y < 0) || (y > (1<<14))) {
			System.out.println(OutputMessages.InsertBadY);
			return;
		}
		
		// Create the city record.
		City city = new City(x, y, name);
		
		// Try inserting into the quad tree first.
		if (!quadtree.insert(x, y, city)) {
			System.out.println(OutputMessages.InsertDup);
			return;
		}
		
		// If that succeeded, insert into the BST
		bst.insert(name, city);
		
		// Output success
		System.out.println(OutputMessages.InsertSuccess);
	}
	
	/**
	 * Remove a value from the database.
	 * @param x The x coordinate of the city to remove
	 * @param y The y coordinate of the city to remove
	 */
	public void remove( Integer x, Integer y )
	{
		// TODO
	}
	
	/**
	 * Find data about cities based on a name search.
	 * @param name The name to search on.
	 */
	public void find( String name )
	{
		// TODO
	}
	
	/**
	 * Perform a radius search on the database.
	 * @param x The x coordinate of the center of the circle
	 * @param y The y coordinate of the center of the circle
	 * @param radius The radius to search in.
	 */
	public void search( Integer x, Integer y, Integer radius )
	{
		// TODO
	}
	
	/**
	 * Print a listing of the PRQuadTree nodes in preorder
	 */
	public void debug()
	{
		// TODO
	}
	
	/**
	 * Initialize the database to be empty
	 */
	public void makenull()
	{
		// Reinitialize the database.
		quadtree = new PRQuadTree<City>(1 << 14);
		bst = new BinarySearchTree<String, City>();
		
		// Output success
		System.out.println(OutputMessages.MakeNullSuccess);
	}
}
