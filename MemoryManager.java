/**
 * The 'user facing' interface to the memory management core of the program
 * This interface deals exclusively with byte arrays and so should have an 
 * interface on top of it in order to serialize/deserialize objects.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @verson 2011.08.30
 */
public class MemoryManager {
	
	/**
	 * Instantiate a new Memory Manager
	 * @param size The byte size of the underlying memory pool
	 */
	public MemoryManager(int size)
	{
		// TODO
	}
	
	/**
	 * Insert data into memory
	 * @param data The data to insert
	 * @param size The length of data to insert from the buffer
	 * @return A handle to the inserted data (or an error handle)
	 */
	public Handle insert(byte[] data, int size)
	{
		// TODO
	}
	
	/**
	 * Remove the data from memory
	 * @param handle A handle for the memory to remove
	 */
	public void remove(Handle handle)
	{
		// TODO
	}
	
	/**
	 * Get the data from memory
	 * @param handle A handle for the memory to get
	 * @param data The data array to put the data into
	 * @param size The size to get from the memory manager
	 */
	public void get(Handle handle, byte[] data, int size)
	{
		// TODO
	}
	
	/**
	 * Dump the underlying Free Block List
	 */
	public void dump()
	{
		// TODO
	}
}
