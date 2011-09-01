import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class memman {
	public static void main(String argv[]) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		int poolSize = Integer.parseInt(argv[0], 10);
		int numRecs = Integer.parseInt(argv[1], 10);
		File f = new File(argv[2]);
		
		MemoryManager mm = new MemoryManager(poolSize);
		Executor ex = new Executor(mm, numRecs);
		
		Parser<Executor> parser = new Parser<Executor>(f, Executor.class);
		for (Pair<Method, Object[]> p : parser) {
			Method m = p.getLeft();
			Object[] args = p.getRight();
			
			m.invoke(ex, args);
		}
	}
}
