/**
 * A buffer representing a block from a file.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.12
 */
public interface Buffer {
	/**
	 * Read the contents of the block represented by this buffer. This will
	 * return a new byte array of size() size.
	 * @return A byte array with the contents of this Buffer.
	 */
	public abstract byte[] read();
	
	/**
	 * 'Write' the contents of the argument to this block. It is not guaranteed
	 * that this write will happen when this is called. This marks the Buffer
	 * as dirty to tell it that a write needs to happen when it gets flushed 
	 * from memory. 
	 * @param data The data to write to the block represented by the buffer.
	 */
	public abstract void write(byte[] data);
	
	/**
	 * What is the size of the block represented by this Buffer?
	 * @return The size of the block.
	 */
	public abstract int size();
	
	/**
	 * Write the data to storage and free the memory that this buffer
	 * represents. Any later reads or writes will require touching the disk.
	 */
	public abstract void flush();
}
