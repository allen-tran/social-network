// Hoang Phuc Luan Truong (Luan)
// AList.java
package ListPackage;

import java.util.Arrays;
import java.io.Serializable;

public class ArrayList<T> implements Serializable, ListInterface<T>

{
    private T[] list; // Array of list entries; ignore list[0]
    private int numberOfEntries;
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;


    // Default Constructor
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }



    // Overloaded Constructor
    public ArrayList(int initialCapacity) {
        if (initialCapacity < DEFAULT_CAPACITY)
            initialCapacity = DEFAULT_CAPACITY;
        else
            checkCapacity(initialCapacity);

        @SuppressWarnings("unchecked")
                T[] tempList = (T[])new Object[initialCapacity + 1];
        list = tempList;
        numberOfEntries = 0;
        initialized = true;
    }



    // add a new entry to the end of the list
    // (This method is implemented. For more information, view ListInterface.java)
    public void add(T newEntry) {
        checkInitialization();
        list[numberOfEntries + 1] = newEntry;
        numberOfEntries++;
        ensureCapacity();
    }



    // add a new entry at a specified position within the list
    // (This method is implemented. For more information, view ListInterface.java)
    public void add(int newPosition, T newEntry) {
        checkInitialization();
        if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1)) {
            if (newPosition <= numberOfEntries)
                makeRoom(newPosition);
            list[newPosition] = newEntry;
            numberOfEntries++;
            ensureCapacity();
        } else
            throw new IndexOutOfBoundsException("Given position of add's new entry is out of bounds.");
    }



    // remove the entry from the given position within the list
    // (This method is implemented. For more information, view ListInterface.java)
    public T remove(int givenPosition) {
        checkInitialization();
        if (givenPosition >= 1 && givenPosition <= numberOfEntries) {
            assert !isEmpty();
            T result = list[givenPosition];
            if (givenPosition < numberOfEntries)
                removeGap(givenPosition);
            numberOfEntries--;
            return result;
        } else
            throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
    }



    // remove all the entries from the list
    // (this method is implemented)
    public void clear() {
        for (int i = 0; i < getLength(); i++)
            list[i] = null;
        numberOfEntries = 0;
    }



    // replace the entry at a given position in this list
    // (This method is implemented. For more information, view ListInterface.java)
    public T replace(int givenPosition, T newEntry) {
        checkInitialization();
        if (givenPosition >= 1 && givenPosition <= numberOfEntries) {
            assert !isEmpty();
            T originalEntry = list[givenPosition];
            list[givenPosition] = newEntry;
            return originalEntry;
        } else
            throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
    }



    // retrieves the entry at a given position in this list
    // (This method is implemented. For more information, view ListInterface.java)
    public T getEntry(int givenPosition) {
        checkInitialization();
        if (givenPosition >= 1 && givenPosition <= numberOfEntries) {
            assert !isEmpty();
            return list[givenPosition];
        } else
            throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
    }



    // check if the list contains the given entry
    // (This method is implemented. For more information, view ListInterface.java)
    public boolean contains(T anEntry) {
        checkInitialization();
        boolean found = false;
        int index = 1;
        while (!found && index <= numberOfEntries) {
            if (anEntry.equals(list[index]))
                found = true;
            index++;
        }
        return found;
    }



    // get the length (number of entries) of the list
    // (This method is implemented. For more information, view ListInterface.java)
    public int getLength() {
        return numberOfEntries;
    }



    // check if the list is empty
    // (This method is implemented. For more information, view ListInterface.java)
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }



    // Retrieves all entries that are in this list in the order in which they occur in the list.
    // (This method is implemented. For more information, view ListInterface.java)
    public T[] toArray() {
        checkInitialization();
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];
        if (numberOfEntries >= 0) System.arraycopy(list, 1, result, 0, numberOfEntries);
        return result;
    }


    
    // Doubles the capacity of the array list if it is full.
    // Precondition: checkInitialization has been called.
    private void ensureCapacity()
    {
        int capacity = list.length - 1;
        if (numberOfEntries >= capacity)
        {
            int newCapacity = 2 * capacity;
            checkCapacity(newCapacity); // Is capacity too big?
            list = Arrays.copyOf(list, newCapacity + 1);
        } // end if
    } // end ensureCapacity





    // Makes room for a new entry at newPosition.
    // Precondition: 1 <= newPosition <= numberOfEntries + 1;
    // numberOfEntries is list's length before addition;
    // checkInitialization has been called.
    private void makeRoom(int newPosition)
    {
        assert (newPosition >= 1) && (newPosition <= numberOfEntries + 1);

        int newIndex = newPosition;
        int lastIndex = numberOfEntries;

    // Move each entry to next higher index, starting at end of
    // list and continuing until the entry at newIndex is moved
        if (lastIndex + 1 - newIndex >= 0)
            System.arraycopy(list, newIndex, list, newIndex + 1, lastIndex + 1 - newIndex);
    } // end makeRoom





    // Shifts entries that are beyond the entry to be removed to the
    // next lower position.
    // Precondition: 1 <= givenPosition < numberOfEntries;
    // numberOfEntries is list's length before removal;
    // checkInitialization has been called.
    private void removeGap(int givenPosition)
    {
        assert (givenPosition >= 1) && (givenPosition < numberOfEntries);

        int removedIndex = givenPosition;
        int lastIndex = numberOfEntries;

        System.arraycopy(list, removedIndex + 1, list, removedIndex, lastIndex - removedIndex);
    } // end removeGap





    // Throws an exception if this object is not initialized.
    private void checkInitialization()
    {
        if (!initialized)
            throw new SecurityException ("AList object is not initialized properly.");
    } // end checkInitialization



    // Throws an exception if the client requests a capacity that is too large.
    private void checkCapacity(int capacity)
    {
        if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a list " +
                    "whose capacity exceeds " +
                    "allowed maximum.");
    } // end checkCapacity
}