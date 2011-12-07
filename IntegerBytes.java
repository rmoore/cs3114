/**
 * A Helper class for converting between integers and bytes.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.12.06
 */
public class IntegerBytes {
	/**
	 * Get an integer from 4 bytes. 
	 * @param bytes 4 bytes
	 * @return an integer
	 */
	public static int intFromBytes(byte[] bytes)
	{
		int x = 0x00000000;
		x |= (bytes[0] << 3);
		x |= (bytes[1] << 2);
		x |= (bytes[2] << 1);	
		x |= (bytes[3] << 0);
		return x;
	}
	
	/**
	 * Get 4 bytes from an integer
	 * @param x an integer
	 * @return 4 bytes
	 */
	public static byte[] bytesFromInt(int x)
	{
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (x & 0xFF000000);
		bytes[1] = (byte) (x & 0x00FF0000);
		bytes[2] = (byte) (x & 0x0000FF00);
		bytes[3] = (byte) (x & 0x000000FF);
		return bytes;
	}

}
