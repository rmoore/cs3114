import java.util.List;

/**
 * This is the interface to the QuadTree class.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 *
 * @param <T> The type of data held in the QuadTree.
 */
public interface QuadTree<T> {
	public abstract boolean insert(int x, int y, T data);
	public abstract T remove(int x, int y);
	public abstract T get(int x, int y);
	public abstract int radius_search(int x, int y, int radius, List<T> list);
}
