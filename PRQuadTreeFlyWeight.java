/**
 * The fly weight node for the PRQuadTree.
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.09
 * @param <T> The type of the object that can't be stored in here...
 */
public class PRQuadTreeFlyWeight<T> implements PRQuadTreeNode<T> {
	
	@SuppressWarnings("rawtypes")
	public static PRQuadTreeFlyWeight SINGLETON;
	
	/**
	 * Get a singleton instance of the fly weight.
	 */
	@SuppressWarnings("unchecked")
	public static <T> PRQuadTreeFlyWeight<T> getFlyweight()
	{
		return SINGLETON;
	}

	@Override
	public boolean insert(int x, int y, T val) {
		throw new UnsupportedOperationException(
				"Attempted to call function on Fly Weight"
				);
	}

	@Override
	public T get(int x, int y) {
		throw new UnsupportedOperationException(
				"Attempted to call function on Fly Weight"
				);
	}

	@Override
	public T remove(int x, int y) {
		throw new UnsupportedOperationException(
				"Attempted to call function on Fly Weight"
				);
	}

	@Override
	public boolean contains(int x, int y) {
		throw new UnsupportedOperationException(
				"Attempted to call function on Fly Weight"
				);
	}
	
	@Override
	public int size() {
		throw new UnsupportedOperationException(
				"Attempted to call function on Fly Weight"
				);
	}

	@Override
	public Triple<Integer, Integer, T>[] getPoints() {
		throw new UnsupportedOperationException(
				"Attempted to call function on Fly Weight"
				);
	}

}
