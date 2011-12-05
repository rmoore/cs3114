import java.util.List;

/**
 * This is the parent class of all QuadTree nodes.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 *
 * @param <T> The type of the data stored in the node.
 */
public abstract class PRQuadTreeNode<T> {
	public abstract PRQuadTreeNode<T> insert(int x, int y, T data, int ul_x, int ul_y, int size);
	public abstract PRQuadTreeNode<T> remove(int x, int y, T[] data, int ul_x, int ul_y, int size);
	public abstract int size();
	public abstract List<Triple<Integer, Integer, T>> getPoints();
	public abstract int radius_search(int x, int y, int radius, List<T> list, int ul_x, int ul_y, int size);
}
