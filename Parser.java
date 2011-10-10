import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Parser for the command language relevant to the city records.
 * Creates a list of methods that are then executed on another object.
 * 
 * @author Tyler Kahn
 * @author Reese Moore
 * @version 12.09.2011
 * @param C The class of the object the methods will be executed on.
 */
public class Parser<C> implements Iterable<Pair<Method, Object[]>> {
	// Private variables.
	private ArrayList<String> lines;
	private ArrayList<Pair<Method, Object[]>> commands;
	private Class<C> methodSpace;
	
	/**
	 * Generate a new Parser that will read and parse a command file.
	 * @param fp The file pointer to the command file.
	 * @param clazz The class that we will be executing on.
	 * @throws IOException
	 */
	public Parser (File fp, Class<C> clazz) throws IOException {
		methodSpace = clazz;
		lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(fp));		

		// Split the file into an array of separate lines.
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			lines.add(line);
		}
		
		commands = new ArrayList<Pair<Method, Object[]>>();
		getCommands();
	}

	public Parser (String str, Class<C> clazz) {
		methodSpace = clazz;
		lines = new ArrayList<String>();
		
		// Split the string into an array of separate lines.
		for (String line : str.split("\n")) {
			lines.add(line);
		}
		
		commands = new ArrayList<Pair<Method, Object[]>>();
		getCommands();
	}
	
	/**
	 * Pass forward our iterator the ArrayList iterator.
	 * @return the commands ArrayList iterator.
	 */
	@Override
	public Iterator<Pair<Method, Object[]>> iterator(){		
		return commands.iterator();
	}
	
	/**
	 * Get all of the commands from the command language.
	 */
	private void getCommands() {
		for (String line : lines) {
			ArrayList<String> k = getCommandComponents(line);
			if (k != null)
				commands.add(getCommand(k));
		}
	}
	
	/**
	 * Generate a pair consisting of a Method call and that call's arguments
	 * based on an ArrayList of Strings that were parsed from the command 
	 * language.
	 * @param components The tokens retrieved from the command language.
	 * @return A pair of a method call and it's arguments.
	 */
	private Pair<Method, Object[]> getCommand(ArrayList<String> components) {
		
		// Build the arguments list for the method we are going to call.
		ArrayList<Object> argumentArray = new ArrayList<Object>();
		
		String commandName = components.get(0);
		if (commandName.equals("insert")) {
			argumentArray.add(Integer.parseInt(components.get(1)));
			argumentArray.add(Integer.parseInt(components.get(2)));
			argumentArray.add(components.get(3));
		} else if (commandName.equals("remove")) {
			if (components.size() == 3) {
				argumentArray.add(Integer.parseInt(components.get(1)));
				argumentArray.add(Integer.parseInt(components.get(2)));
			} else {
				argumentArray.add(components.get(1));
			}
		} else if (commandName.equals("find")) {
			argumentArray.add(components.get(1));	
		} else if (commandName.equals("search")) {
			argumentArray.add(Integer.parseInt(components.get(1)));
			argumentArray.add(Integer.parseInt(components.get(2)));
			argumentArray.add(Integer.parseInt(components.get(3)));
		}
		
		// We also need a list of the types of those arguments.
		@SuppressWarnings("rawtypes")
		ArrayList<Class> argumentTypes = new ArrayList<Class>();
		
		for (Object o : argumentArray) {
			argumentTypes.add(o.getClass());
		}
		
		// Get the method name that needs to be called.
		String methodName = commandName;
		
		// Use reflection to build the method call.
		Method m = null;
		try {
			m = methodSpace.getDeclaredMethod(methodName, argumentTypes.toArray(new Class[0]));
		} catch (SecurityException e) {
			// FAIL
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// I CAN HAZ FAIL!?
			e.printStackTrace();
		}

		// Give them back an pair where the method is the method to be called
		// And the right hand array is the arguments to be called with that method.
		return new Pair<Method, Object[]>(m, argumentArray.toArray());
	}
	
	/**
	 * Given a line of the command language, parse it into the relevant 
	 * portions.
	 * @param line The line from the command language.
	 * @return A list of the tokens from the command language.
	 */
	private ArrayList<String> getCommandComponents(String line) {
		// Prepare the regular expression.
		//String regex = "(\\w+)\\s?(-?\\d*)\\s?(-?\\d*)\\s?(-?\\d*)\\s?([A-Za-z_]*)";
		//Pattern pattern = Pattern.compile(regex);
		
		// Clean the line of whitespace.
		String strippedLine = line.trim().replaceAll("\\s+", " ");
		if (strippedLine.isEmpty()) {
			return null;
		}
		
		// Get the matches from the stripped line.
		//Matcher m = pattern.matcher(strippedLine);
        System.out.println(strippedLine);
		ArrayList<String> ret = new ArrayList<String>();
		for (String s : strippedLine.split("\\s+")) {
			ret.add(s);
		}
		return ret;
		//m.matches();
		//return getMatches(m);
		
	}
}
