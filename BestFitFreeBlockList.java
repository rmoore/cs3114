/**
 * Keep track of the number of free blocks for the memory pool. Manage 
 * allocation and deallocation. Properly merge free blocks that are next to 
 * each other.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.08.30
 */
public class BestFitFreeBlockList implements FreeBlocks {

	// Private Variables
	private FreeBlock startBlock;
	private FreeBlock endBlock;

	/**
	 * Generate a new FreeBlockList
	 * @param size The size of the entire memory pool to represent.
	 */
	public BestFitFreeBlockList(int size)
	{
		FreeBlock largeBlock;

		// Create the sentinel nodes
		startBlock = new FreeBlock();
		endBlock = new FreeBlock();

		// Create the first block that represents all the free space
		largeBlock = new FreeBlock(size, 0);

		// Link it together
		startBlock.setNext(largeBlock);
		largeBlock.setPrev(startBlock);

		endBlock.setPrev(largeBlock);
		largeBlock.setNext(endBlock);
	}

	/**
	 * Allocate a buffer of size 'size'.
	 * @param size The size of the buffer we want to allocate.
	 * @return The offset of the allocated buffer (or -1 on failure).
	 */
	@Override
	public int allocate(int size)
	{
		// Find the block we're going to allocate
		FreeBlock block = bestFit(size);
		if (block == null) {
			return -1;
		}
		
		// Store the offset for later.
		int offset = block.getOffset();
		
		// Do the allocation
		performAllocate(block, size);
		
		return offset;
	}

	/**
	 * Deallocate a buffer from 'offset' of size 'size'. 
	 * @param offset The offset to deallocate from
	 * @param size the size to deallocate
	 */
	@Override
	public void deallocate(int offset, int size)
	{
		// Allocate the new FreeBlock
		FreeBlock newBlock = new FreeBlock(size, offset);
		
		// Find the block we are inserting before
		FreeBlock afterBlock = startBlock;
		while ((afterBlock = afterBlock.getNext()) != endBlock) {
			if (afterBlock.getOffset() > offset) {
				break;
			}
		}
		
		// Insert the new block in the list.
		newBlock.setNext(afterBlock);
		newBlock.setPrev(afterBlock.getPrev());
		newBlock.getNext().setPrev(newBlock);
		newBlock.getPrev().setNext(newBlock);
		
		// Perform merges if necessary
		mergeIfNeeded(newBlock);
		mergeIfNeeded(newBlock.getPrev());
	}

	/**
	 * Get a string representation of the Free Blocks.
	 * @return The Free Blocks in String format
	 */
	@Override
	public String toString()
	{
		String str = "FreeList:\n";
		
		FreeBlock block = startBlock;
		while( (block = block.getNext()) != endBlock ) {
			str += "\t[" + block.getOffset() + ", " 
					+ (block.getOffset() + block.getSize() - 1) + 
					"] (" + block.getSize() + " bytes)\n";
		}
		
		return str;
	}
	
	/**
	 * Perform the Best Fit algorithm to find the block that we are going to
	 * use to allocate 'size' bytes.
	 * @param size The number of bytes we want to allocate.
	 * @return a FreeBlock that can be used. If none are found, return null.
	 */
	private FreeBlock bestFit(int size)
	{
		FreeBlock found = null;
		int found_size = Integer.MAX_VALUE;
		
		FreeBlock block = startBlock;
		while( (block = block.getNext()) != endBlock )
		{
			if ((block.getSize() >= size) && (block.getSize() < found_size))
			{
				found = block;
				found_size = block.getSize();
			}
		}
		
		return found;
	}
	
	/**
	 * Do an allocation on a block.
	 * @param block The block that we are allocating on.
	 * @param size The number of bytes to allocate.
	 */
	private void performAllocate(FreeBlock block, int size)
	{
		// If it is a clean removal
		if (block.getSize() == size) {
			removeBlock(block);
			return;
		}
		
		// Otherwise update the block values.
		block.setSize(block.getSize() - size);
		block.setOffset(block.getOffset() + size);
	}

	/**
	 * Remove a block from the list.
	 * @param block The block to be removed.
	 */
	private void removeBlock(FreeBlock block)
	{
		// Remove our block from the list.
		block.getPrev().setNext(block.getNext());
		block.getNext().setPrev(block.getPrev());
	}
	
	/**
	 * Merge a block with the block following it, iff it is needed.
	 * @param block The block we are merging with its next.
	 */
	private void mergeIfNeeded(FreeBlock block)
	{
		// Check the merge conditions
		if (!isMergeNeeded(block)) {
			return;
		}
		
		// Get the next block.
		FreeBlock nextBlock = block.getNext();
		
		// Perform the merge.
		block.setSize(block.getSize() + nextBlock.getSize());
		removeBlock(nextBlock);
	}
	
	/**
	 * Make sure a block needs a forward merge.
	 * @param block The block to check if we need to merge.
	 * @return if we need to do a merge
	 */
	private boolean isMergeNeeded(FreeBlock block)
	{
		// Get the next block.
		FreeBlock nextBlock = block.getNext();
		
		// Check the merge conditions.
		if ((block == startBlock) || (nextBlock == endBlock)) {
			return false;
		}
		if ((block.getOffset() + block.getSize()) != nextBlock.getOffset()) {
			return false;
		}
		
		// If we've gotten here we're good to merge.
		return true;
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
		 * Constructor that sets the size and offset
		 * @param size The size of the block to create
		 * @param offset The offset of the new block
		 */
		public FreeBlock(int size, int offset)
		{
			this.size = size;
			this.offset = offset;
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
		
		/**
		 * Represent this Free Block as a String.
		 * @return A string representation of this FreeBlock.
		 */
		@Override
		public String toString()
		{
			String str = "<";
			str += "Begin: " + getOffset() + ", ";
			str += "Size: " + getSize();
			str += ">";
			
			return str;
		}

	}
}
