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
	private FreeBlock startBlock;
	private FreeBlock endBlock;

	/**
	 * Generate a new FreeBlockList
	 * @param size The size of the entire memory pool to represent.
	 */
	public FreeBlockList(int size)
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

		// Set the length
		length = 1;
	}

	/**
	 * Allocate a buffer at the front of the 'index'th block of size 'size'.
	 * @param index The index to allocate on.
	 * @param size The size of the buffer we want to allocate.
	 * @return Success or failure
	 */
	public boolean allocate(int index, int size)
	{
		FreeBlock block = findNode(index);

		if (block.getSize() == size)
		{
			removeBlock(block);
		}
		else if (block.getSize() > size) 
		{
			block.setOffset(block.getOffset() + size);
			block.setSize(block.getSize() - size);
		}
		else
		{
			return false; // Error in allocation
		}

		return true;
	}

	/**
	 * Deallocate a buffer from 'offset' of size 'size'. 
	 * @param offset The offset to deallocate from
	 * @param size the size to deallocate
	 * @return Success or Failure
	 */
	public boolean deallocate(int offset, int size)
	{
		// Make a new Free Block
		FreeBlock block = new FreeBlock( size, offset );

		// Find the block we are going to insert before.
		FreeBlock found_block;
		for( int i = 0; i < getLength(); i++ )
		{
			FreeBlock search_block = findNode( i );
			if (search_block.offset < offset)
			{
				found_block = search_block;
				break;
			}
		}
		if (found_block == null)
		{
			found_block = endBlock;
		}

		// Insert our new block before the found block
		insertBefore(found_block, block);

		// Merge the blocks preceding block if it needs to be.
		FreeBlock prior = block.getPrev();
		if ((prior.getOffset() + prior.getSize()) == offset)
		{
			block.setOffset(block.getOffset() - prior.getSize());
			block.setSize(block.getSize() + prior.getSize());
			removeBlock(prior);
		}

		// Merge the following block if it needs to be.
		FreeBlock next = block.getNext();
		if ((block.getOffset() + block.getSize()) == next.getOffset())
		{
			block.setSize(block.getSize() + next.getSize());
			removeBlock(next);
		}
	}

	/**
	 * Get the size of the 'index'th free block in physical order.
	 * @param index The index of the free block
	 * @return The size of the 'index'th block.
	 */
	public int getSize(int index)
	{
		return findNode(index).getSize();
	}

	/**
	 * Get the offset of the 'index'th free block in physical order.
	 * @param index The index of the free block.
	 * @return The offset of the 'index'th block
	 */
	public int getOffset(int index)
	{
		return findNode(index).getOffset();
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
	 * Seek and return the 'index'th FreeNode in the list.
	 * @param index The node index to find in the list.
	 * @return The 'index'th node.
	 */
	private FreeBlock findNode(int index)
	{
		FreeBlock block = startNode;

		for ( int i = 0; i <= index; i++ )
		{
			block = block.getNext();
		}

		return block;
	}

	/**
	 * Remove a block from the list.
	 * @param block The block to be removed.
	 */
	private void removeBlock(FreeBlock block)
	{
		// Get the relevant blocks.
		FreeBlock prev_block = block.getPrev();
		FreeBlock next_block = block.getNext();

		// Relink.
		prev_block.setNext(next_block);
		next_block.setPrev(prev_block);

		// Update the length
		length--;
	}

	/**
	 * Insert the node 'block' before the node 'after_block'
	 * @param after_block The block in the list that we are inserting
	 * in front of.
	 * @param block The block that we are inserting before after_block.
	 */
	private void insertBefore(FreeBlock after_block, FreeBlock block)
	{
		// Get the relevant block.
		FreeBlock before_block = after_block.getPrev();

		// Insert it in the list
		block.setNext(after_block);
		block.setPrev(before_block);
		before_block.setNext(block);
		after_block.setPrev(block);

		// Update the length
		length++;
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

	}
}
