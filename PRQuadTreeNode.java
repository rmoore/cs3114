import java.util.List;

/**
 * This is the parent class of all QuadTree nodes.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 */
public abstract class PRQuadTreeNode {
	public abstract PRQuadTreeNode insert(int x, int y, Handle data, int ul_x, int ul_y, int size);
	public abstract PRQuadTreeNode remove(int x, int y, Handle[] data, int ul_x, int ul_y, int size);
	public abstract int size();
	public abstract List<Triple<Integer, Integer, Handle>> getPoints();
	public abstract int radius_search(int x, int y, int radius, List<Handle> list, int ul_x, int ul_y, int size);
}
