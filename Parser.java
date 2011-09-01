import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tyler Kahn
 * @author Reese Moore
 *
 */
public class Parser<C> implements Iterable<Pair<Method, Object[]>> {
	
	private ArrayList<String> lines;
	private ArrayList<Pair<Method, Object[]>> commands;
	private Class<C> methodSpace;
	
	public Parser (File f, Class<C> c) throws IOException {
		methodSpace = c;
		lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(f));		

		for (String line = br.readLine(); line != null; line = br.readLine()) {
			lines.add(line);
		}
		commands = new ArrayList<Pair<Method, Object[]>>();
		getCommands();
	}

	public Parser (String s, Class<C> c) {
		methodSpace = c;
		lines = new ArrayList<String>();
		for (String line : s.split("\n")) {
			lines.add(line);
		}
		commands = new ArrayList<Pair<Method, Object[]>>();
		getCommands();
	}
	
	@Override
	public Iterator<Pair<Method, Object[]>> iterator(){		
		return commands.iterator();
	}
	
	private void getCommands() {
		for (String line : lines) {
			ArrayList<String> k = getCommandComponents(line);
			if (k != null)
				commands.add(getCommand(k));
		}
		
	}
	
	private Pair<Method, Object[]> getCommand(ArrayList<String> components) {
		
		ArrayList<Object> argumentArray = new ArrayList<Object>();

		switch (components.size()) {
			case 5: argumentArray.add(components.get(4));
			case 4: argumentArray.add(0, (int)(Integer.parseInt(components.get(3), 10)));
			case 3: argumentArray.add(0, Integer.parseInt(components.get(2), 10));
			case 2: argumentArray.add(0, Integer.parseInt(components.get(1), 10));
		}
		
		@SuppressWarnings("rawtypes")
		ArrayList<Class> argumentTypes = new ArrayList<Class>();
		
		for (Object o : argumentArray) {
			argumentTypes.add(o.getClass());
		}
		String methodName = components.get(0);
		Method m = null;
		try {
			m = methodSpace.getDeclaredMethod(methodName, argumentTypes.toArray(new Class[0]));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return new Pair<Method, Object[]>(m, argumentArray.toArray());
	}
	
	private ArrayList<String> getCommandComponents(String line) {
		Pattern pattern = Pattern.compile("(print|insert|remove)\\s?(\\d*)\\s?(\\d*)\\s?(\\d*)\\s?([A-Za-z_]*)");
		String strippedLine = line.trim().replaceAll("\\s+", " ");
		if (strippedLine.isEmpty()) {
			return null;
		}
		Matcher m = pattern.matcher(strippedLine);
		m.matches();
		return getMatches(m);
		
	}
	
	private ArrayList<String> getMatches(Matcher m) {
		ArrayList<String> matches = new ArrayList<String>();
		for (int i = 1; i <= m.groupCount(); ++i) {
			String group = m.group(i);
			if (group.length() > 0) {
				matches.add(group);
			}
		}
		return matches;
	}
}
