/**
 * A generic pair of objects.
 * 
 * @author Tyler Kahn
 * @author Reese Moore
 * @version 12.09.2011
 * @param L The left object type
 * @param R The right object type
 */
public class Pair<L, R> {
	private final L left;
	private final R right;
	
	/**
	 * Construct a new Pair.
	 * @param l The left object.
	 * @param r The right object.
	 */
	public Pair(L l, R r) {
		left = l;
		right = r;
	}
	
	/**
	 * Get the left object in the pair.
	 * @return The left object.
	 */
	public L getLeft() {
		return left;
	}
	
	/**
	 * Get the right object in the pair.
	 * @return The right object
	 */
	public R getRight() {
		return right;
	}
	
	/**
	 * Return a String representation of the pair.
	 * @return The pair as a string.
	 */
	@Override
	public String toString() {
		return "Pair<" + left + ", " + right + ">";
	}
}
