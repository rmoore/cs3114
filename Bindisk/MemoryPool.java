/**
 * A pool of bytes to be used by the Memory Manager.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.08.30
 */
public class MemoryPool {
	
	// Private Buffer of Bytes
	private BPByteArray pool;
	
	/**
	 * Allocate a new Memory Pool
	 * @param array The BPByteArray to use to manage the data.
	 */
	public MemoryPool(BPByteArray array)
	{
		pool = array;
	}
	
	/**
	 * Write a byte to an offset in the Memory Pool
	 * @param offset An offset into the pool
	 * @param data The byte to write
	 */
	public void write(int offset, byte data)
	{
		pool.write(data, offset);
	}
	
	/**
	 * Write the bytes from a byte array to an offset in the Memory Pool
	 * @param offset An offset into the pool
	 * @param data The byte array to pull from
	 * @param size The number of bytes to write
	 */
	public void write(int offset, byte[] data, int size)
	{
		pool.write(data, offset, size);
	}
	
	/**
	 * Read a single byte from the Memory Pool
	 * @param offset The byte to read from
	 * @return The byte at that location in the Memory Pool
	 */
	public byte read(int offset)
	{
		return pool.read(offset);
	}
	
	/**
	 * Read a byte array from the memory pool
	 * @param offset The offset to read from
	 * @param data A byte array to read into
	 * @param size The number of bytes to read
	 */
	public void read(int offset, byte[] data, int size)
	{
		pool.read(data, offset, size);
	}
}
