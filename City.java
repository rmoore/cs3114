/**
 * City Records.
 * This is where we will store data about cities. 
 * Store their names and coordinates.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.09
 */
public class City {
	private final int x, y;
	private final String name;
	
	/**
	 * Create a new city record.
	 * @param x The x coordinate of the city.
	 * @param y The y coordinate of the city.
	 * @param name The name of the city.
	 */
	public City(int x, int y, String name)
	{
		this.x = x;
		this.y = y;
		this.name = name;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the name of the city
	 */
	public String toString() {
		return getName();
	}
}
