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
}
