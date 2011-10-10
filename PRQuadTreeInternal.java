/**
 * These internal nodes to the Quad Tree don't have data, but they have
 * four children. 
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 *
 * @param <T> The type of the data stored in the children of this node.
 */
public class PRQuadTreeInternal<T> extends PRQuadTreeNode<T> {
	private PRQuadTreeNode<T>[] child;
	
	// Enumerate the Children
	public static final int NW = 0;
	public static final int NE = 1;
	public static final int SW = 2;
	public static final int SE = 3;
	
	/**
	 * Instantiate a new Internal node that only has Fly Weight children.
	 */
	@SuppressWarnings("unchecked")
	public PRQuadTreeInternal()
	{
		child = (PRQuadTreeNode<T>[])(new PRQuadTreeNode[4]);
		child[NW] = PRQuadTreeFlyWeight.getFlyWeight();
		child[NE] = PRQuadTreeFlyWeight.getFlyWeight();
		child[SW] = PRQuadTreeFlyWeight.getFlyWeight();
		child[SE] = PRQuadTreeFlyWeight.getFlyWeight();
	}
	
	public PRQuadTreeNode<T> insert(int x, int y, T data, int ul_x, int ul_y, int size)
	{
		int quad;
		int dx;
		int dy;
		
		// Determine the quadrant to insert into.
		if (x < (ul_x + (size/2))) {
			dx = 0;
			if (y < (ul_y + (size/2))) {
				dy = 0;
				quad = NW;
			} else {
				dy = (size/2);
				quad = SW;
			}
		} else {
			dx = (size/2);
			if (y < (ul_y + (size/2))) {
				dy = 0;
				quad = NE;
			} else {
				dy = (size/2);
				quad = SE;
			}
		}
		
		// Do the insert
		child[quad].insert(x, y, data, ul_x + dx, ul_y + dy, size/2);
		return this;
	}
}
