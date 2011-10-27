/**
 * The array - tree methods as based off the heap implementation presented in 
 * section 5.5 of the textbook. 
 * 
 * I decided to refactor out these methods because they really don't have much
 * to do with a heap per-se, but instead are general methods for representing
 * a binary tree as an array.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.26
 */
public abstract class AbstractArrayTree {	
	/**
	 * How many items are visible in this tree?
	 */
	public abstract int size();

	/**
	 * Check if an index in the array refers to a leaf
	 * @param index The index to determine.
	 * @return If the index is a leaf
	 */
	public boolean isLeaf(int index) {
		return ((index >= size()/2) && (index < size()));
	}

	/**
	 * Get the index of the left child of the index'th node
	 * @param index The parent's index
	 * @return The index of the parent's left child
	 */
	public int leftChild(int index) {
		assert (index < size()/2) : "Position has no left child";
		return 2*index + 1;
	}

	/**
	 * Get the index of the right child of the index'th node
	 * @param index The parent's index
	 * @return The index of the parent's right child
	 */
	public int rightChild(int index) {
		assert (index < (size() - 1)/2) : "Position has no right child";
		return 2*index + 2;
	}

	/**
	 * Get the position for the parent of a given 'index'th node
	 * @param inde the child's index
	 * @return The index of the child's parent.
	 */
	public int parent(int index) {
		assert (index > 0) : "Position has no parent";
		return (index - 1)/2;
	}

}