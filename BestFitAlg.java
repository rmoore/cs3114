/**
 * Implement the Best Fit Memory Management algorithm. This algorithm searches
 * to find the smallest block that will fit the size we seek to allocate. 
 * @author ram
 *
 */
public class BestFitAlg implements MMAlg {

	// Private Variables.
	private FreeBlockList fbl;
	
	/**
	 * Allocate a new BestFit Object
	 * @param fbl The FreeBlockList that we are acting on.
	 */
	public BestFitAlg(FreeBlockList fbl)
	{
		this.fbl = fbl;
	}
	
	/**
	 * Find the smallest block that will fit the size of the buffer that we seek
	 * to allocate.
	 * @param size The size of the buffer to allocate.
	 * @return the index of the block to allocate on.
	 */
	@Override
	public int getFit(int size) {
		int smallest_index = -1;
		int smallest_size = Integer.MAX_VALUE;
		int current_size;
		int length = fbl.getLength();
		
		for( int i = 0; i < length; i++ )
		{
			current_size = fbl.getSize(i);
			if ((current_size >= size) && (current_size < smallest_size))
			{
				smallest_index = i;
				smallest_size = current_size;
				
				// Optimize later access to this block.
				fbl.setInteresting(i);
			}
		}
		
		return smallest_index;	
	}

}
