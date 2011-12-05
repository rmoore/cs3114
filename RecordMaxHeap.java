/**
 * A Max Heap that operates on and is backed by a RecordArray.
 * 
 * This heap implementation is *heavily* based on the implementation presented 
 * in section 5.5 of the textbook "Data Structures".
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.26
 */
public class RecordMaxHeap extends AbstractArrayTree {
	private RecordArray array;
	private int size;
	
	/**
	 * Construct a new MaxHeap.
	 * Will automatically 'heapify' the entire array in place.
	 * @param record_array The array that we are going to heapify.
	 */
	public RecordMaxHeap( RecordArray record_array )
	{
		array = record_array;
		size = array.size();
		
		// Build the heap.
		heapify();
	}
	
	/**
	 * Get the number of records currently in the heap.
	 * @return the number of records remaining in the heap.
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * Build the heap using siftdowns.
	 */
	public void heapify()
	{
		for (int i = (size/2) - 1; i >= 0; i--) {
			siftdown(i);
		}
	}
	
	/**
	 * Sift down the heap to move an object into the proper position in order 
	 * to satisfy the heap condition.
	 * @param index The index of the node to sift down into position.
	 */
	private void siftdown(int index)
	{
		assert ((index >= 0) && (index < size)) : "Illegal heap index";
		while (!isLeaf(index)) {
			// Get the left child
			int child = leftChild(index);
			
			// Compare the left child to the right child, choose the bigger.
			if ((child < (size - 1)) && 
				(array.getKey(child) < array.getKey(child + 1))) {
				child++;
			}
			
			// Compare the selected child to the parent, return early if the 
			// comparison satisfies the heap condition.
			if (array.getKey(index) >= array.getKey(child)) { return; }
			
			// If the heap condition isn't satisfied, swap and check the next
			// level for the heap condition
			array.swap(index, child);
			index = child;
		}
	}
	
	/**
	 * Remove the max record of the Heap.
	 * Decrement the apparent size of the heap by one, Move the top item of
	 * the heap to the end by swapping with the 'last' item in physical order,
	 * decrement the apparent size of the heap by one, and reheapify by 
	 * sifting down the new root of the heap.
	 */
	public void removeMax()
	{
		assert (size > 0) : "Removing from empty heap.";
		
		// Decrement the size of the heap
		size--;
		
		// Swap the root with the last item in the array
		array.swap(0, size);
		
		// As long as we aren't the last element, we need to siftdown
		if (size != 0) {
			siftdown(0);
		}
	}
	
}
