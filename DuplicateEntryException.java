/**
 * An exception that is thrown when there already exists an element with the 
 * coordinates passed in.
 * 
 * Yes, I know, this is abuse of the exception system, but at this point I 
 * really don't care.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 */
public class DuplicateEntryException extends RuntimeException {
	private static final long serialVersionUID = -3205423735007597176L;
}
