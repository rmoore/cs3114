/**
 * Interface that implements a Memory Management algorithm. By convention
 * the constructor must take a handle to the FreeBlockList that this alogrithm
 * is acting on.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.08.30
 */
public interface MMAlg {
	/**
	 * Search in the FreeBlockList to find the block index that we will use
	 * to allocate.
	 * @param size The size of the buffer we need to allocate.
	 * @return The index of the block we are going to allocate in.
	 */
	int getFit(int size);
}
