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
	
	// Private Variables
	private int length;
	
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
		return length;
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
	
	/**
	 * Internal Representation of Free Blocks within the list.
	 */
	private class FreeBlock
	{
		// Private Variables
		private int offset;
		private int size;
		private FreeBlock next;
		private FreeBlock prev;
		
		/**
		 * Construct a new free block
		 */
		public FreeBlock()
		{
			// Empty Constructor
		}

		/**
		 * Get the offset of this block into memory.
		 * @return The offset of this block into memory.
		 */
		public int getOffset() {
			return offset;
		}

		/**
		 * Set the offset of this block in memory.
		 * @param offset the new offset of this block in memory.
		 */
		public void setOffset(int offset) {
			this.offset = offset;
		}

		/**
		 * Get the number of bytes in memory represented by this block.
		 * @return the number of bytes this block represents.
		 */
		public int getSize() {
			return size;
		}

		/**
		 * Set the number of bytes in memory that this block represents.
		 * @param size the new number of bytes that this block represents.
		 */
		public void setSize(int size) {
			this.size = size;
		}

		/**
		 * Get the next free block in physical order
		 * @return the next free block in the list.
		 */
		public FreeBlock getNext() {
			return next;
		}

		/**
		 * Set the next block in physical order
		 * @param next the new next block in the list
		 */
		public void setNext(FreeBlock next) {
			this.next = next;
		}

		/**
		 * Get the previous free block in physical order
		 * @return The previous free block in the list.
		 */
		public FreeBlock getPrev() {
			return prev;
		}

		/**
		 * Set the previous free block in physical order.
		 * @param prev The new previous free block in the list.
		 */
		public void setPrev(FreeBlock prev) {
			this.prev = prev;
		}
		
	}
}
