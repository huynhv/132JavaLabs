package lab09.list;

/**
 * List interface that defines the operations supported by all of the
 * implementations of the List ADT.
 * 
 * @author Grant Braught
 * @author Dickinson College
 * @version Jul 29, 2009
 */
public interface CS132List {

    /**
     * Get the current size of the list.
     * 
     * @return the size.
     */
    public int size();

    /**
     * Append the specified Object to the end of the list. The size of the list
     * is increased by 1.
     * 
     * @param element the data element to append to the list.
     */
    public void add(Object element);

    /**
     * Get the data element stored at the specified index of the list.
     * 
     * @param index the index from which to get the element.
     * @return the data element at index.
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public Object get(int index) throws IndexOutOfBoundsException;

    /**
     * Replace the data element at the specified index with the specified Object.
     * 
     * @param index the index of the element to be replaced
     * @param element the new data element to store at index.
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public void set(int index, Object element) throws IndexOutOfBoundsException;

    /**
     * Insert the specified data element before the one found at index.  The newly inserted
     * data element will now be found at the specified index. All elements at index and higher
     * have their index increased by one.  Note: Inserting at index 0 of an empty list will
     * place the element at index 0.  Inserting at index size will place the element at the
     * end of the list.
     * 
     * @param index the index at which to insert the new element.
     * @param element the new data element to be inserted.
     * @throws IndexOutOfBoundsException if index < 0 or index > size()
     */
    public void insert(int index, Object element) throws IndexOutOfBoundsException;
    
    /**
     * Remove and return the data element at the specified index.  All elements at index and
     * higher have their index decreased by one.
     * 
     * @param index the index from which to remove the element.
     * @return the element found at index.
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    public Object remove(int index) throws IndexOutOfBoundsException;
}
