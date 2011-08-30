/**
 * A pool of bytes to be used by the Memory Manager.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.08.30
 */
public class MemoryPool {
	
	/**
	 * Allocate a new Memory Pool
	 * @param size The size (in bytes) of the memory pool to allocate
	 */
	public MemoryPool(int size)
	{
		// TODO
	}
	
	/**
	 * Write a byte to an offset in the Memory Pool
	 * @param offset An offset into the pool
	 * @param data The byte to write
	 */
	public void write(int offset, byte data)
	{
		// TODO
	}
	
	/**
	 * Write the bytes from a byte array to an offset in the Memory Pool
	 * @param offset An offset into the pool
	 * @param data The byte array to pull from
	 * @param size The number of bytes to write
	 */
	public void write(int offset, byte[] data, int size)
	{
		// TODO
	}
	
	/**
	 * Read a single byte from the Memory Pool
	 * @param offset The byte to read from
	 * @return The byte at that location in the Memory Pool
	 */
	public byte read(int offset)
	{
		// TODO
	}
	
	/**
	 * Read a byte array from the memory pool
	 * @param offset The offset to read from
	 * @param data A byte array to read into
	 * @param size The number of bytes to read
	 */
	public void read(int offset, byte[] data, int size)
	{
		// TODO
	}
}
