import java.util.List;
import java.util.UnknownFormatFlagsException;

/**
 * This is the parent class of all QuadTree nodes.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.10
 */
public abstract class PRQuadTreeNode {
	public static byte INTERNAL = 0x1;
	public static byte LEAF	  = 0x2;
	
	public abstract Handle insert(int x, int y, Handle data, int ul_x, int ul_y, int size);
	public abstract Handle remove(int x, int y, Handle[] data, int ul_x, int ul_y, int size);
	public abstract int size();
	public abstract List<Triple<Integer, Integer, Handle>> getPoints();
	public abstract int radius_search(int x, int y, int radius, List<Handle> list, int ul_x, int ul_y, int size);
	public abstract Handle getHandle();
	
	public static PRQuadTreeNode deref(MemoryManager mem, Handle handle) {
		if (handle.getOffset() == -1) { return new PRQuadTreeFlyWeight(mem); }
		
		byte[] data = new byte[1];
		mem.get(handle, data, 1);
		
		if (data[0] == INTERNAL) { return new PRQuadTreeInternal(mem, handle); }
		if (data[0] == LEAF)     { return new PRQuadTreeLeaf(mem, handle);     }
		
		throw new UnknownFormatFlagsException("Flag Byte not Valid");
	}
}