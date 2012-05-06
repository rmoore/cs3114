/**
 * Stats global variable namespace.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.11.1
 */
public class Stats {
	public static String dataFileName;
	public static int cacheHits = 0;
	public static int cacheMisses = 0;
	public static int diskReads = 0;
	public static int diskWrites = 0;
	public static long sortExecutionTime = 0;
	
	public static String formattedOutput() {
		String s = "Heap sort statistics";
		s += "\nData file name: " + dataFileName;
		s += "\nCache hits: " + cacheHits;
		s += "\nCache misses: " + cacheMisses;
		s += "\nDisk reads: " + diskReads;
		s += "\nDisk writes: " + diskWrites;
		s += "\nSort execution time: " + sortExecutionTime + "ms";
		s += "\n\n";
		
		return s;
	}
}