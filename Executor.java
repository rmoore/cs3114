import java.util.ArrayList;


public class Executor {
	private MemoryManager mm;
	private ArrayList<Handle> handleArray;
	private int numRecs;
	private byte[] byteBuffer;
	public Executor(MemoryManager mm, int numRecs) {
		this.mm = mm;
		this.numRecs = numRecs;
		handleArray = new ArrayList<Handle>(numRecs);
		byteBuffer = new byte[256];
	}
	
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
		if (h == null) {
			return;
		}
		int bytesReturned = mm.get(h, byteBuffer, byteBuffer.length);
		Record r = Record.fromBytes(take(bytesReturned, byteBuffer));
		System.out.println(r);
	}
	
	public void print() {
		for (int i = 0; i < numRecs; i++) {
			print(i);
		}
		
		mm.dump();
	}
	
	private byte[] take(int n, byte[] a) {
		byte[] k = new byte[n];
		for (int i = 0; i < n; ++i) {
			k[i] = a[i];
		}
		return k;
	}
}
