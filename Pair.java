/**
 * @author tkahn
 *
 */
public class Pair<L, R> {
	private final L left;
	private final R right;
	public Pair(L l, R r) {
		left = l;
		right = r;
	}
	
	public L getLeft() {
		return left;
	}
	
	public R getRight() {
		return right;
	}
	
	@Override
	public String toString() {
		return "Pair<" + left + ", " + right + ">";
	}
}
