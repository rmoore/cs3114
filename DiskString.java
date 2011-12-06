import java.io.UnsupportedEncodingException;

/**
 * This class represents helper functions for storing a string on disk.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.12.05
 */
public class DiskString {
	/**
	 * Allocate a string in the memory manager
	 * @param mem the memory manager to use
	 * @param str The string to allocate
	 * @return A handle to that string.
	 */
	public static Handle alloc(MemoryManager mem, String str)
	{
		byte[] bytes;
		try {
			bytes = str.getBytes("US-ASCII");
		} catch (UnsupportedEncodingException e) {
			bytes = null;
			e.printStackTrace();
		}
		
		return mem.insert(bytes, str.length());
	}
	
	/**
	 * Dereference a string from the memory manager
	 * @param mem The memory manager to use
	 * @param handle the handle to use to dereference
	 * @return the reassembled string.
	 */
	public static String deref(MemoryManager mem, Handle handle)
	{
		byte[] bytes = new byte[1024];
		mem.get(handle, bytes, 1024);
		
		String str;
		try {
			str = new String(bytes, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			str = null;
			e.printStackTrace();
		}
		
		return str;
	}
	
	/**
	 * Free a string from the memory manager
	 * @param mem The memory manager to use
	 * @param handle the handle to free
	 */
	public static void free(MemoryManager mem, Handle handle)
	{
		mem.remove(handle);
	}
}
