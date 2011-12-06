import java.util.List;

/**
 * This is the interface to the QuadTree class.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 */
public interface QuadTree {
	public abstract boolean insert(int x, int y, Handle data);
	public abstract Handle remove(int x, int y);
	public abstract int radius_search(int x, int y, int radius, List<Handle> list);
}
