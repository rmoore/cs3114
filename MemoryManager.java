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
	
	// Private Variables
	private MemoryPool pool;
	private FreeBlockList fbl;
	private MMAlg mmalg;
	
	/**
	 * Instantiate a new Memory Manager
	 * @param size The byte size of the underlying memory pool
	 */
	public MemoryManager(int size)
	{
		pool = new MemoryPool(size);
		fbl = new FreeBlockList(size);
		mmalg = new BestFitAlg(fbl);
	}
	
	/**
	 * Insert data into memory
	 * @param data The data to insert
	 * @param size The length of data to insert from the buffer
	 * @return A handle to the inserted data (or an error handle)
	 */
	public Handle insert(byte[] data, int size)
	{
		int offset = mmalg.getFit(size + 1);
		
		// Error Checking
		if (offset < 0) {
			System.err.println("Cannot allocate buffer");
			return new Handle(-1);
		}
		
		// Allocate the memory from the Free Block List
		fbl.allocate(offset, size + 1);
		
		// Create our write buffer
		byte[] write_data = new byte[size + 1];
		write_data[0] = (byte) size;
		System.arraycopy(data, 0, write_data, 1, size);
		
		// Write to memory
		pool.write(offset, write_data, size + 1);
		
		// Return a handle to this data
		return new Handle(offset);
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
