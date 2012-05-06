/**
 * A buffer pool that has some number of buffers to keep in memory.
 * The buffer pool should ensure that only a certain number of buffers
 * are backed in memory.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.12
 */
public interface BufferPool {
	/**
	 * Get a handle to a Buffer that represents the block-th block of the file
	 * that is backing this BufferPool.
	 * @param block The index of the block we want to acquire.
	 * @return A buffer handle to that block.
	 */
	public abstract Buffer acquireBuffer(int block);
	
	/**
	 * Return the number of blocks that this Buffer Pool represents.
	 * @return The number of blocks that this Buffer Pool has.
	 */
	public abstract int size();
	
	/**
	 * Alert the buffer pool that its services are no longer needed and that
	 * it should write out all data to the disk.
	 */
	public abstract void flush();
}
