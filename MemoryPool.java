/**
 * A pool of bytes to be used by the Memory Manager.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.08.30
 */
public class MemoryPool {
	
	// Private Buffer of Bytes
	private byte[] buffer;
	
	/**
	 * Allocate a new Memory Pool
	 * @param size The size (in bytes) of the memory pool to allocate
	 */
	public MemoryPool(int size)
	{
		buffer = new byte[size];
	}
	
	/**
	 * Write a byte to an offset in the Memory Pool
	 * @param offset An offset into the pool
	 * @param data The byte to write
	 */
	public void write(int offset, byte data)
	{
		buffer[offset] = data;
	}
	
	/**
	 * Write the bytes from a byte array to an offset in the Memory Pool
	 * @param offset An offset into the pool
	 * @param data The byte array to pull from
	 * @param size The number of bytes to write
	 */
	public void write(int offset, byte[] data, int size)
	{
		System.arraycopy(data, 0, buffer, offset, size);
	}
	
	/**
	 * Read a single byte from the Memory Pool
	 * @param offset The byte to read from
	 * @return The byte at that location in the Memory Pool
	 */
	public byte read(int offset)
	{
		return buffer[offset];
	}
	
	/**
	 * Read a byte array from the memory pool
	 * @param offset The offset to read from
	 * @param data A byte array to read into
	 * @param size The number of bytes to read
	 */
	public void read(int offset, byte[] data, int size)
	{
		System.arraycopy(buffer, offset, data, 0, size);
	}
}
