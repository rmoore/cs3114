/**
 * The Records for the data that are going to be stored in memory are of this
 * type. This class will serialize and deserialize to byte arrays.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.08.30
 */
public class Record {

	/**
	 * Create a new Record
	 * @param name The name of the city
	 * @param x The x coordinate of the city
	 * @param y The y coordinate of the city
	 */
	public Record(String name, int x, int y)
	{
		// TODO
	}
	
	/**
	 * Serialize the record
	 * @return The record as a byte array
	 */
	public byte[] toBytes()
	{
		// TODO
	}
	
	/**
	 * Generate a record from a byte array
	 * @param data The a byte array to deserialize from
	 * @return A record
	 */
	public static Record fromBytes(byte[] data)
	{
		// TODO
	}
}
