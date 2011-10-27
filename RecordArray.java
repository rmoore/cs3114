import java.io.File;
import java.io.IOException;

/**
 * Implement a record array front that all accesses to the underlying file will
 * be through. This record array is created by passing a file that represents 
 * the array database. The user will be able to query the array for information
 * about a record, and will be able to swap records within the array.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.10.24
 */
public class RecordArray {
	private BufferPool pool;
	
	// Some Constants
	private final static int BLOCK_SIZE = 4096; // bytes
	private final static int RECORD_SIZE = 4; // bytes
	
	/**
	 * Instantiate a new Record Array
	 * @param filename The name of the underlying record file.
	 * @param num_buffers The number of buffers that this RecordArray is
	 *                    allowed to have in memory.
	 * @throws IOException An exception that can arise from accessing the disk.
	 */
	public RecordArray(String filename, int num_buffers) throws IOException
	{
		File file = new File(filename);
		pool = new LRUBufferPool(file, num_buffers, BLOCK_SIZE);
	}
	
	/**
	 * Get the key for a record in the array.
	 * @param index The index of the record
	 * @return The key for the index-th record.
	 */
	public short getKey(int index)
	{
		int block = (index * RECORD_SIZE) / BLOCK_SIZE;
		int offset = (index * RECORD_SIZE) % BLOCK_SIZE;
		
		byte[] buffer = pool.acquireBuffer(block).read();
		
		short ret_val = 0x0000;
		ret_val |= buffer[offset] << 8;
		ret_val |= buffer[offset + 1];
		
		return ret_val;
	}
	
	/**
	 * Get the value for a record in the array.
	 * @param index The index of the record
	 * @return The value for the index-th record.
	 */
	public short getValue(int index)
	{
		int block = (index * RECORD_SIZE) / BLOCK_SIZE;
		int offset = (index * RECORD_SIZE) % BLOCK_SIZE;
		
		byte[] buffer = pool.acquireBuffer(block).read();
		
		short ret_val = 0x0000;
		ret_val |= buffer[offset + 2] << 8;
		ret_val |= buffer[offset + 3];
		
		return ret_val;
	}
	
	/**
	 * Swap two records in the array.
	 * @param i1 The index of the one record.
	 * @param i2 The index of the second record.
	 */
	public void swap(int i1, int i2)
	{
		int block1 = (i1 * RECORD_SIZE) / BLOCK_SIZE;
		int offset1 = (i1 * RECORD_SIZE) % BLOCK_SIZE;
		int block2 = (i2 * RECORD_SIZE) / BLOCK_SIZE;
		int offset2 = (i2 * RECORD_SIZE) % BLOCK_SIZE;
		
		byte[] rec1 = new byte[RECORD_SIZE];
		byte[] rec2 = new byte[RECORD_SIZE];
		
		Buffer buf1 = pool.acquireBuffer(block1);
		Buffer buf2 = pool.acquireBuffer(block2);
		
		byte[] buffer;
		
		// Get record 1
		buffer = buf1.read();
		System.arraycopy(buffer, offset1, rec1, 0, RECORD_SIZE);
		
		// Get record 2
		buffer = buf2.read();
		System.arraycopy(buffer, offset2, rec2, 0, RECORD_SIZE);
		
		// Put record 1 in buffer for old record 2
		System.arraycopy(rec1, 0, buffer, offset2, RECORD_SIZE);
		buf2.write(buffer);
		
		// Put record 2 in buffer for old record 1
		buffer = buf1.read();
		System.arraycopy(rec2, 0, buffer, offset1, RECORD_SIZE);
		buf1.write(buffer);
	}
	
	/**
	 * Get the number of records in this array.
	 * A tacit assumption here (which is allowed in the spec) is that the 
	 * number of records is a multiple of 1024, and therefore all blocks are
	 * full.
	 * @return The number of records in the array.
	 */
	public int size()
	{
		return (pool.size() * (BLOCK_SIZE / RECORD_SIZE));
	}
	
	/**
	 * Propagate a close message down the stack so that we can ensure that
	 * everything gets flushed out to disk.
	 */
	public void close()
	{
		pool.close();
	}
}
