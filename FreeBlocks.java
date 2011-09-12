public interface FreeBlocks {

	/**
	 * Allocate a buffer of size 'size'.
	 * @param size The size of the buffer we want to allocate.
	 * @return The offset of the allocated buffer (or -1 on failure).
	 */
	public abstract int allocate(int size);

	/**
	 * Deallocate a buffer from 'offset' of size 'size'. 
	 * @param offset The offset to deallocate from
	 * @param size the size to deallocate
	 */
	public abstract void deallocate(int offset, int size);

	/**
	 * Get a string representation of the Free Blocks.
	 * @return The Free Blocks in String format
	 */
	public abstract String toString();

}