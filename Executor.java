import java.util.ArrayList;


public class Executor {
	private MemoryManager mm;
	private ArrayList<Handle> handleArray;
	private int numRecs;
	public Executor(MemoryManager mm, int numRecs) {
		this.mm = mm;
		this.numRecs = numRecs;
		handleArray = new ArrayList<Handle>(numRecs);
	}
	
	public void insert(Integer recnum, Integer x, Integer y, String name) {
		Record r = new Record(name, x, y);
		byte[] recordBytes = r.toBytes();
		Handle h = mm.insert(recordBytes, recordBytes.length); //TODO: some error handling logic goes here
		handleArray.set(recnum, h);
	}
	
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
	
	public void print(Integer recnum) {
		Handle h = handleArray.get(recnum);
		byte[] recordBytes = mm.get(h); //shouldn't have to know size of record
		Record r = Record.fromBytes(recordBytes);
		System.out.println(r);
	}
	
	//TODO: implement print(void)
}
