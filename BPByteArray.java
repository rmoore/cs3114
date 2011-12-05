/**
 * A Buffer Pool backed Byte Array.
 * This object sits on top of a Buffer Pool and mediates all access to it.
 * The user can then look at the Buffer Pool as though it was a simple byte
 * array.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.12.5
 */
public class BPByteArray {
	// Private Variables
	BufferPool pool;
	
	/**
	 * Allocate a new BPByteArray.
	 * @param bp The buffer pool this is abstracting.
	 */
	public BPByteArray(BufferPool bp)
	{
		pool = bp;
	}
	
	/**
	 * Write a byte at an offset
	 * @param b The byte to write
	 * @param offset The offset to write b at.
	 */
	public void write(byte b, int offset)
	{
		int bs = pool.bufSize();
		int block = offset / bs;
		int off = offset % bs;
		
		Buffer buf = pool.acquireBuffer(block);
		
		byte[] data = buf.read();
		data[off] = b;
		buf.write(data);
	}
	
	/**
	 * Read a byte from an offset.
	 * @param offset The offset to read from.
	 */
	public byte read(int offset)
	{
		int bs = pool.bufSize();
		int block = offset / bs;
		int off = offset % bs;
		
		Buffer buf = pool.acquireBuffer(block);
		byte[] data = buf.read();
		
		return data[off];
	}
	
	/**
	 * Write size bytes from a byte array into the buffer pool at offset offset
	 * @param data The byte array to write from.
	 * @param offset The offset into the pool to begin writing at.
	 * @param size The number of bytes to copy.
	 */
	public void write(byte[] data, int offset, int size)
	{
		for (int i = 0; i < size; i++) {
			write(data[i], offset + i);
		}
	}
	
	/**
	 * Read size bytes from the array at offset offset into a byte array.
	 * @param data The byte array to write into.
	 * @param offset The offset in the pool to begin reading at.
	 * @param size The number of bytes to copy.
	 */
	public void read(byte[] data, int offset, int size)
	{
		for (int i = 0; i < size; i++) {
			data[i] = read(offset + i);
		}
	}

	/**
	 * Flush the underlying pool
	 */
	public void flush()
	{
		pool.flush();
	}
}
