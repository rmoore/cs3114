
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
	 * Allocate a new city record, store it in the memory pool, and return a 
	 * handle to the memory.
	 * @param mem The memory manager to use to store this city
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param name The name of the city
	 */
	public static Handle alloc(MemoryManager mem, int x, int y, String name)
	{
		byte[] city_record = new byte[12];

		// Store the name separately.
		Handle city_name = DiskString.alloc(mem, name);
		
		// Store the x
		byte[] x_bytes = IntegerBytes.bytesFromInt(x);
		city_record[0] = x_bytes[0];
		city_record[1] = x_bytes[1];
		city_record[2] = x_bytes[2];
		city_record[3] = x_bytes[3];
		
		// Store the y
		byte[] y_bytes = IntegerBytes.bytesFromInt(y);
		city_record[4] = y_bytes[0];
		city_record[5] = y_bytes[1];
		city_record[6] = y_bytes[2];
		city_record[7] = y_bytes[3];
		
		// Store the pointer
		byte[] n_bytes = IntegerBytes.bytesFromInt(city_name.getOffset());
		city_record[8] = n_bytes[0];
		city_record[9] = n_bytes[1];
		city_record[10] = n_bytes[2];
		city_record[11] = n_bytes[3];
		
		// Store in the memory pool and return a handle to it.
		return mem.insert(city_record, 12);
	}
	
	/**
	 * Dereference a 'pointer' to a city and return a reconstructed City object
	 * @param mem The memory manager to read from
	 * @param handle The Handle to dereference
	 */
	public static City deref(MemoryManager mem, Handle handle)
	{
		// Get the data from the memory manager
		byte[] city_record = new byte[12];
		mem.get(handle, city_record, 12);
		
		byte[] x_bytes = {city_record[0], city_record[1], city_record[2], city_record[3]};
		int x = IntegerBytes.intFromBytes(x_bytes);
		
		byte[] y_bytes = {city_record[4], city_record[5], city_record[6], city_record[7]};
		int y = IntegerBytes.intFromBytes(y_bytes);
		
		byte[] n_bytes = {city_record[8], city_record[9], city_record[10], city_record[11]};
		int n = IntegerBytes.intFromBytes(n_bytes);
		
		// Dereference the name
		String name = DiskString.deref(mem, new Handle(n));
		
		return new City(x, y, name);
	}
	
	/**
	 * Free a pointer to a City object
	 * @param mem The memory manager to work with.
	 * @param handle The handle to free
	 */
	public static void free(MemoryManager mem, Handle handle)
	{	
		// Get the data from the memory manager
		byte[] city_record = new byte[12];
		mem.get(handle, city_record, 12);
		
		// Get the pointer to the string.
		int n = 0x00000000;
		n |= (city_record[8] << 3);
		n |= (city_record[9] << 2);
		n |= (city_record[10] << 1);
		n |= (city_record[11] << 0);
		
		// Free the memory
		DiskString.free(mem, new Handle(n));
		mem.remove(handle);
	}
	
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
