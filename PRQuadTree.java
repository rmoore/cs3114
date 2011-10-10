/**
 * Implement a generic Quad Tree that stores some value and uses two integers
 * as the keys. 
 * 
 * A PRQuadTree either has a internal node (which may be a fly weight) or it
 * may have 4 internal PRQuadTrees.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.09
 * 
 * @param <T> the type that is stored in the Quad Tree.
 */
public class PRQuadTree<T> {
	// Private variables
	private int dimension;
	private PRQuadTreeNode<T> internalNode;
	private PRQuadTree<T>[] internalQuadTree;
	private boolean hasSubTrees;
	
	// Enumerate the quadrants of a QuadTree
	private final static int NW = 0;
	private final static int NE = 1;
	private final static int SW = 2;
	private final static int SE = 3;
	
	public PRQuadTree(int dim)
	{
		this.dimension = dim;
		this.internalNode = PRQuadTreeFlyWeight.getFlyweight();
	}
	
	/**
	 * Insert a value into the PRQuadTree
	 * 
	 * @param x The x coordinate of the value to insert.
	 * @param y The y coordinate of the value to insert.
	 * @param val The value to insert.
	 * @return if the insertion was successful.
	 */
	public boolean insert(int x, int y, T val)
	{
		if (hasSubTrees) {
			return insertSubTree(x, y, val);
		} else {
			if (internalNode instanceof PRQuadTreeFlyWeight) {
				// Make it a concrete Leaf Node and perform the insert.
				internalNode = new PRQuadTreeLeafNode<T>();
				return internalNode.insert(x, y, val);
			} else {
				if (internalNode.size() == PRQuadTreeLeafNode.MAX_OBJECTS) {
					makeSubTrees();
					return insertSubTree(x, y, val);
				} else {
					return internalNode.insert(x, y, val);
				}
			}
		}
	}

	/**
	 * Helper method used for insertion into subtrees of this tree.
	 * This takes care of determining which tree to insert into and then
	 * changing the dimensions accordingly.
	 * 
	 * @param x The x coordinate to insert into.
	 * @param y The y coordinate to insert into.
	 * @param val The value to insert.
	 * @return if the insertion was successful.
	 */
	private boolean insertSubTree(int x, int y, T val) {
		assert(hasSubTrees);
		
		int quad;
		int dx;
		int dy;
		
		if (x < dimension/2) {
			dx = 0;
			if (y < dimension/2) {
				quad = NW;
				dy = 0;
			} else {
				quad = SW;
				dy = (dimension/2);
			}
		} else {
			dx = (dimension/2);
			if (y < dimension/2) {
				quad = NE;
				dy = 0;
			} else {
				quad = SE;
				dy = (dimension/2);
			}
		}
		return internalQuadTree[quad].insert(x - dx, y - dy, val);
	}
	
	/**
	 * Convert an existing internalNode into SubTrees.
	 */
	private void makeSubTrees()
	{
		assert(internalNode.size() == PRQuadTreeLeafNode.MAX_OBJECTS);
		
		// Instantiate the SubQuadTrees
		internalQuadTree[NW] = new PRQuadTree<T>(dimension/2);
		internalQuadTree[NE] = new PRQuadTree<T>(dimension/2);
		internalQuadTree[SW] = new PRQuadTree<T>(dimension/2);
		internalQuadTree[SE] = new PRQuadTree<T>(dimension/2);
		hasSubTrees = true;
		
		// Add all values from the existing internal node to the new trees.
		Triple<Integer, Integer, T>[] points = internalNode.getPoints();
		for( Triple<Integer, Integer, T> point : points ) {
			insertSubTree(point.getFirst().intValue(), 
						  point.getSecond().intValue(), 
						  point.getThird());
		}
		
		// Replace the internal node with a flyweight
		internalNode = PRQuadTreeFlyWeight.getFlyweight();
	}
}
