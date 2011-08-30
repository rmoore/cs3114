/**
 * Keep track of the number of free blocks for the memory pool. Manage 
 * allocation and deallocation. Properly merge free blocks that are next to 
 * each other.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.08.30
 */
public class FreeBlockList {
	
	/**
	 * Generate a new FreeBlockList
	 * @param size The size of the entire memory pool to represent.
	 */
	public FreeBlockList(int size)
	{
		// TODO
	}
	
	/**
	 * Try to allocate a buffer at 'offset' of size 'size'.
	 * @param offset The offset to allocate at.
	 * @param size The size of the buffer to allocate
	 * @return Success or failure
	 */
	public boolean allocate(int offset, int size)
	{
		// TODO
	}
	
	/**
	 * Deallocate a buffer from 'offset' of size 'size'. 
	 * @param offset The offset to deallocate from
	 * @param size the size to deallocate
	 * @return Success or Failure
	 */
	public boolean deallocate(int offset, int size)
	{
		// TODO
	}
	
	/**
	 * Get the size of the 'index'th free block in physical order.
	 * @param index The index of the free block
	 * @return The size of the 'index'th block.
	 */
	public int getSize(int index)
	{
		// TODO
	}
	
	/**
	 * Get the offset of the 'index'th free block in physical order.
	 * @param index The index of the free block.
	 * @return The offset of the 'index'th block
	 */
	public int getOffset(int index)
	{
		// TODO
	}
	
	/**
	 * Get how many blocks are in the list currently.
	 * @return the number of blocks in the list.
	 */
	public int getLength()
	{
		// TODO
	}
	
	/**
	 * Get a string representation of the Free Block List
	 * @return The Free Block List in String format
	 */
	@Override
	public String toString()
	{
		// TODO
	}
}
