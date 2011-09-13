import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Implement an Array backed list for our usage.
 * 
 * @author Reese Moore
 * @author Tyler Kahn
 * @version 2011.09.12
 * @param E The type of value stored in the list.
 */
public class ArrayList<E> implements List<E> {

	// Private variables
	private E array[];
	private int length;
	
	/**
	 * Generate an ArrayList of default size 10.
	 */
	public ArrayList()
	{
		this(10);
	}
	
	/**
	 * Generate a new ArrayList
	 * @param size The default starting size of the array backing.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList(int size)
	{
		array = (E[]) new Object[size];
		length = 0;
	}
	
	/**
	 * Add a value to the end of the list.
	 * @param obj The object to add to the list.
	 */
	@Override
	public boolean add(E obj) {
		add(size(), obj);
		return true;
	}

	/**
	 * Insert the data 'obj' at position 'index' shifting everything else to 
	 * the right.
	 * @param index The index to insert at
	 * @param obj The data to insert.
	 */
	@Override
	public void add(int index, E obj) {
		ensureSpace();
		shiftRight(index);
		
		// Add the object to the list
		array[index] = obj;
		length++;
	}

	/**
	 * Add all objects from col into this list.
	 * @param col The collection to add from
	 * @return true if all adds went successfully.
	 */
	@Override
	public boolean addAll(Collection<? extends E> col) {
		boolean ret = true;
		for (E obj : col) {
			if (!add(obj)) {
				ret = false;
			}
		}
		return ret;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Clear the list, start anew.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		array = (E[]) new Object[length];
		length = 0;
	}

	/**
	 * Does this list contain obj.
	 * @param obj The object to look for.
	 * @return if the list contains obj.
	 */
	@Override
	public boolean contains(Object obj) {
		return (indexOf(obj) >= 0);
	}

	/**
	 * Ensure that this ArrayList contains all of the items in col.
	 * @param col The collection to check against.
	 * @return true iff all elements of col are contained in this list.
	 */
	@Override
	public boolean containsAll(Collection<?> col) {
		for (Object obj : col) {
			if (!contains(obj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get a value from the ArrayList
	 * @param index The index to retrieve from.
	 * @return The value at that position in the list.
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		return array[index];
	}

	/**
	 * Get the first index of an object in the list equal to obj. Return
	 * -1 if none can be found.
	 * @param obj The object to search for.
	 * @return The first index of obj or -1.
	 */
	@Override
	public int indexOf(Object obj) {
		for (int i = 0; i < length; i++) {
			if (obj == null ? get(i) == null : obj.equals(get(i))) {
				return i;
			}
		}
		
		return -1;
	}

	/**
	 * Does the list have at least 1 element in it?
	 * @return true if the size is nonzero.
	 */
	@Override
	public boolean isEmpty() {
		return (size() == 0);
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			int current_index;

			@Override
			public boolean hasNext() {
				return (current_index < size());
			}

			@Override
			public E next() {
				return get(current_index++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Find the last instance in the list where an object equaling obj is
	 * found.
	 * @param obj The object to search for.
	 * @return The last index where this can be found.
	 */
	@Override
	public int lastIndexOf(Object obj) {
		for (int i = length; i >= 0; i--) {
			if  (obj == null ? get(i) == null : obj.equals(get(i))) {
				return i;
			}
		}
		
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator(int arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Remove the first occurrence of obj from the list.
	 * @param obj The object to remove.
	 * @return true if something was removed.
	 */
	@Override
	public boolean remove(Object obj) {
		int index = indexOf(obj);
		
		// Make sure we found something.
		if (index < 0) 
			return false;
		
		remove(index);
		return true;
	}

	/**
	 * Remove the object at the index-th position from the list.
	 * @param index The index to remove from.
	 * @return The object removed.
	 */
	@Override
	public E remove(int index) {
		E val = get(index);
		shiftLeft(index);
		
		length--;
		
		return val;
	}

	/**
	 * Remove all elements from this list if they are in col.
	 * @param col The collection to use as a basis for removal.
	 * @return false if any of the removals returns false
	 */
	@Override
	public boolean removeAll(Collection<?> col) {
		boolean ret = true;
		for (Object obj : col) {
			if (!remove(obj)) {
				ret = false;
			}
		}
		return ret;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Replace an element at a position in the list.
	 * @param index The position to replace with.
	 * @param val the new data to store there.
	 * @return The old data at that position in the list.
	 */
	@Override
	public E set(int index, E val) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E old = array[index];
		array[index] = val;
		
		return old;
	}

	/**
	 * Get the number of items in the list.
	 * @return the number of items in the list.
	 */
	@Override
	public int size() {
		return length;
	}

	@Override
	public List<E> subList(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return a safe copy of the backing array.
	 * @return The list as an array.
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(array, length);
	}

	/**
	 * Return a safe copy of the backing array, the type of which is 
	 * determined by the array passed in. 
	 * @param a The array passed in.
	 * @return a safe copy of this array.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size()) {
			return (T[]) Arrays.copyOf(array, size(), a.getClass());
		} else {
			System.arraycopy(array, 0, a, 0, size());
			if (a.length > size()) {
				a[size()] = null;
			}
			return a;
		}
	}
	
	/**
	 * Make sure the array is large enough to handle the addition of a new 
	 * element.
	 */
	private void ensureSpace()
	{
		if (size() == array.length - 1) {
			@SuppressWarnings("unchecked")
			E[] new_array = (E[]) new Object[size() * 2];
			System.arraycopy(array, 0, new_array, 0, size());
			array = new_array;
		}
	}
	
	/**
	 * Shift everything to the right starting at position index.
	 * After this is called, array[index] = null.
	 * 
	 * NOTE: DOES NOT AFFECT SIZE;
	 * 
	 * @param index the left most item to shift to the right. 
	 */
	private void shiftRight(int index) 
	{
		ensureSpace();
		for (int i = (length - 1); i >= index; i--) {
			array[i + 1] = array[i];
		}
		array[index] = null;
	}
	
	/**
	 * Shift everything to the left starting with overwriting position
	 * index. After this is called, array[length - 1] = null.
	 * 
	 * NOTE: DOES NOT AFFECT SIZE;
	 * 
	 * @param index The index in the array that gets overwritten.
	 */
	private void shiftLeft(int index)
	{
		for (int i = index; i < (length - 1); i++) {
			array[i] = array[i + 1];
		}
		array[length - 1] = null;
	}

}
