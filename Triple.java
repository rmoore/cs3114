/**
 * A generic 3-tuple.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.09
 *
 * @param <J> The type of the 1st item in the tuple.
 * @param <K> The type of the 2nd item in the tuple.
 * @param <L> The type of the 3rd item in the tuple.
 */
public class Triple<J, K, L> {
	
	private final J first;
	private final K second;
	private final L third;
	
	public Triple(J first, K second, L third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public J getFirst() {
		return first;
	}

	public K getSecond() {
		return second;
	}

	public L getThird() {
		return third;
	}
	
	public String toString() {
		return getFirst() + "," + getSecond() + "," + getThird();
	}
}
