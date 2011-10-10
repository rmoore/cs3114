import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
//- I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * Main class for the program.
 * 
 * @author Tyler Kahn
 * @author Reese Moore
 * @version 09.10.2011
 */
public class PRprog {
	
	/**
	 * Main entry point for the program.
	 * Reads the command line arguments
	 * Allocates the underlying memory manager
	 * Parses the command file and executes commands.
	 * 
	 * @param argv The command line arguments
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void main(String argv[]) 
		throws IOException, IllegalArgumentException, IllegalAccessException, 
		       InvocationTargetException 
	{
		// Make sure we get the proper number of command line arguments,
		// Otherwise print a usage statement.
		if (argv.length != 1) {
			System.exit(2);
		}
		
		// Parse the command line arguments
		File f = new File(argv[0]);
		
		Executor ex = new Executor();
		
		// Parse the command file
		Parser<Executor> parser = new Parser<Executor>(f, Executor.class);
		for (Pair<Method, Object[]> p : parser) {
			Method m = p.getLeft();
			Object[] args = p.getRight();
			
			// Build the command line output.
			String arg_str = "";
			for( Object o : args ) {
				arg_str += o.toString() + " ";
			}
			System.out.println("> " + m.getName() + " " + arg_str);
			
			m.invoke(ex, args);
			
			// Add a blank line to make it easier to read.
			System.out.println("");
		}
	}
}
