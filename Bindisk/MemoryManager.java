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
	private FreeBlocks fbl;
	
	/**
	 * Instantiate a new Memory Manager
	 * @param bp_array The Buffer Pool array to use.
	 * @param block_sz The size of the blocks.
	 */
	public MemoryManager(BPByteArray bp_array, int block_sz)
	{
		pool = new MemoryPool(bp_array);
		fbl = new BestFitFreeBlockList(block_sz);
	}
	
	/**
	 * Insert data into memory
	 * @param data The data to insert
	 * @param size The length of data to insert from the buffer
	 * @return A handle to the inserted data (or an error handle)
	 */
	public Handle insert(byte[] data, int size)
	{
		int offset = fbl.allocate(size + 1);
		
		// Error Checking
		if (offset < 0) {
			return Handle.ERROR_HANDLE;
		}
		
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
	 * Update the memory at a specific handle
	 * @param handle A handle for the memory to update
	 * @param data The data to write into that location
	 * @param size The size of the data (can't exceed the size of the data
	 * at the handle already.
	 */
	public void update(Handle handle, byte[] data, int size)
	{
		// Determine which is smaller.
		int mem_size = size(handle);
		int write_size = Math.min(size, mem_size);
		
		pool.write(handle.getOffset() + 1, data, write_size);
	}
	
	/**
	 * Remove the data from memory
	 * @param handle A handle for the memory to remove
	 */
	public void remove(Handle handle)
	{
		// Read the size from the memory pool
		int size = size(handle);
		
		// Deallocate this block from the pool
		fbl.deallocate(handle.getOffset(), size + 1);
	}
	
	/**
	 * Get the data from memory
	 * @param handle A handle for the memory to get
	 * @param data The data array to put the data into
	 * @param size The size to get from the memory manager
	 * @return the number of bytes actually copied into the buffer.
	 */
	public int get(Handle handle, byte[] data, int size)
	{
		// Determine which is smaller.
		int mem_size = size(handle);
		int read_size = Math.min(size, mem_size);
		
		// Read the data into the data array ptr.
		pool.read(handle.getOffset() + 1, data, read_size);
		
		// How much was actually read.
		return read_size;
	}
	
	/**
	 * Get the size of the data represented by a handle
	 * @param handle The handle to get size
	 * @return The size of the data handle points to
	 */
	public int size(Handle handle)
	{
		return pool.read(handle.getOffset()) & 0xFF;
	}
	
	/**
	 * Dump the underlying Free Block List
	 */
	public String dump()
	{
		return fbl.toString();
	}
}
