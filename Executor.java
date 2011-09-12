import java.util.ArrayList;

/**
 * Make calls on a Memory Manager. This is the high level interface to the
 * Memory Manager that is closer to the client-side aspect of the project.
 * 
 * @author Tyler Kahn
 * @author Reese Moore
 * @version 12.09.2011
 */
public class Executor {
	// Private Variables
	private MemoryManager mm;
	private ArrayList<Handle> handleArray;
	private int numRecs;
	private byte[] byteBuffer;
	
	/**
	 * Allocate a new Executor.
	 * @param mm The Memory Manager to make calls to.
	 * @param numRecs The number of records we have.
	 */
	public Executor(MemoryManager mm, int numRecs) {
		this.mm = mm;
		this.numRecs = numRecs;
		handleArray = new ArrayList<Handle>(numRecs);
		byteBuffer = new byte[256];
	}
	
	/**
	 * Insert a new Record into Memory.
	 * @param recnum The number of the record.
	 * @param x The X coordinate of the city.
	 * @param y The Y coordinate of the city.
	 * @param name The Name of the city.
	 */
	public void insert(Integer recnum, Integer x, Integer y, String name) {
		Record r = new Record(name, x, y);
		byte[] recordBytes = r.toBytes();
		Handle e = handleArray.get(recnum);
		if (e != null) {
			mm.remove(e);
		}
		Handle h = mm.insert(recordBytes, recordBytes.length);
		handleArray.set(recnum, h);
	}
	
	/**
	 * Remove a record from Memory.
	 * @param recnum The record number to remove.
	 */
	public void remove(Integer recnum) {
		if (recnum < 0 || recnum >= numRecs) {
			System.out.println(Errors.RecnumOutOfBounds);
		}
		else if (handleArray.get(recnum) == null) {
			System.out.println(Errors.HandleNotInArray);
		} else {
			Handle h = handleArray.get(recnum);
			mm.remove(h);
			handleArray.set(recnum, null);
		}
	}
	
	/**
	 * Print the information about the Record specified by recnum.
	 * @param recnum The record number to retrieve information about.
	 */
	public void print(Integer recnum) {
		Handle h = handleArray.get(recnum);
		if (h == null) {
			return;
		}
		int bytesReturned = mm.get(h, byteBuffer, byteBuffer.length);
		Record r = Record.fromBytes(take(bytesReturned, byteBuffer));
		System.out.println(r);
	}
	
	/**
	 * Print information about all records, as well as the underlying 
	 * FreeBlockList.
	 */
	public void print() {
		for (int i = 0; i < numRecs; i++) {
			print(i);
		}
		
		mm.dump();
	}
	
	/**
	 * Get the first n bytes from the byte array "array" and return it.
	 * @param n The number of bytes to retrieve.
	 * @param array The array to retrieve it from.
	 * @return The byte array containing the first n bytes of the array 
	 * 		   "array".
	 */
	private byte[] take(int n, byte[] array) {
		byte[] ret = new byte[n];
		for (int i = 0; i < n; ++i) {
			ret[i] = array[i];
		}
		return ret;
	}
}
