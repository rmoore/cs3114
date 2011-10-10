/**
 * A generic binary search tree.
 * 
 * This binary search tree class is the outer class to the real BST which is
 * implemented internally with nodes. This is NOT a recursive definition of 
 * the BST (i.e. a BST does not have a BST as a child)
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * 
 * @param <K> The type of the key stored in the tree.
 * @param <V> The type of the values stored in the tree.
 */
public class BinarySearchTree<K extends Comparable<K>, V> {
	
	private BSTNode root;
	
	/**
	 * Insert a new KV pair into the BST.
	 * 
	 * @param key The key to insert.
	 * @param value The value to insert.
	 */
	public void insert(K key, V value)
	{
		// We may have to insert the first node into the tree.
		if (root == null) { 
			root = new BSTNode(key, value); 
			return;
		}
		
		// In the general case this is a lot easier.
		new BSTNode(key, value, root);
	}
	
	/**
	 * Find a value from the BST based on the key.
	 * @param key The key to search on.
	 * @return the value that was found, null if nothing was found.
	 */
	public V find(K key)
	{
		if (root == null) { return null; }
		return root.find(key);
	}
	
	/**
	 * The internal nodes of the Binary Search Tree.
	 * This is where all of the work is done.
	 * 
	 * @author Reese Moore
	 * @author Tyler Kahn
	 * @version 2011.10.09
	 */
	private class BSTNode {
		private final K key;
		private final V value;
		private BSTNode left;
		private BSTNode right;
		
		/**
		 * Create a new Node for the tree.
		 * 
		 * @param key The key to store in this node.
		 * @param value The value of this node.
		 */
		public BSTNode(K key, V value) 
		{
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Create a new Node for the tree and insert it in the proper place,
		 * starting from the root node passed in.
		 * 
		 * @param key The key to store in this node.
		 * @param value The value to store in this node.
		 * @param root The root node to start from for insertion.
		 */
		public BSTNode(K key, V value, BSTNode root)
		{
			this(key, value);
			root.insert(this);
		}
		
		/**
		 * Insert a node below this. 
		 * Either insert the new node as the proper child, or pass it on down
		 * the tree until it reaches the proper place.
		 * @param node The new node to insert.
		 */
		public void insert(BSTNode node)
		{
			if (node.getKey().compareTo(getKey()) < 0) {
				if (getLeft() == null) {
					setLeft(node);
				} else {
					getLeft().insert(node);
				}
			} else {
				if (getRight() == null) {
					setRight(node);
				} else {
					getRight().insert(node);
				}
			}
		}
		
		/**
		 * Retrieve a value from the BST.
		 * @param key The key to search on.
		 * @return The value that was found.
		 */
		public V find(K key)
		{
			if (key == getKey()) { return getValue(); }
			if (key.compareTo(getKey()) < 0) {
				return (left == null ? null : left.find(key));
			} else {
				return (right == null ? null : right.find(key));
			}
		}

		/**
		 * Get the key of a given node
		 * @return The key from this node.
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Get the value of a given node
		 * @return The value from this node.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Get the left child node of this node.
		 * @return the left child.
		 */
		public BSTNode getLeft() {
			return left;
		}

		/**
		 * Set the left child node of this node.
		 * @param left The new left child node of this one.
		 */
		public void setLeft(BSTNode left) {
			this.left = left;
		}

		/**
		 * Get the right child node of this node.
		 * @return the right child.
		 */
		public BSTNode getRight() {
			return right;
		}

		/**
		 * Set the right child node of this node.
		 * @param right The new right child node of this one.
		 */
		public void setRight(BSTNode right) {
			this.right = right;
		}
		
	}
}
