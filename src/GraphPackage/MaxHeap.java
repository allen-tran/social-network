package GraphPackage;

import java.io.Serializable;
import java.util.Arrays;
/**
   A class that implements the ADT maxheap by using an array.
   
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public class MaxHeap<T extends Comparable<? super T>>
             implements MaxHeapInterface<T>, Serializable
{
   private T[] heap;      // Array of heap entries
   private int lastIndex; // Index of last entry and number of entries
   private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;

   public MaxHeap()
   {
      this(DEFAULT_CAPACITY); // Call next constructor
   } // end default constructor

   public MaxHeap(int initialCapacity)
   {
      checkCapacity(initialCapacity);
      
      // The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      T[] tempHeap = (T[])new Comparable[initialCapacity + 1];
      heap = tempHeap;
      lastIndex = 0;
      initialized = true;
   } // end constructor

   public MaxHeap(T[] entries)
   {
      checkCapacity(entries.length);
      
      // The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      T[] tempHeap = (T[]) new Comparable[entries.length + 1];
      heap = tempHeap;
      lastIndex = entries.length;

      // Copy given array to data field
      for (int index = 0; index < entries.length; index++)
         heap[index + 1] = entries[index];
       
      // Create heap
      for (int rootIndex = lastIndex/2; rootIndex > 0; rootIndex--)
         reheap(rootIndex);
      initialized = true;
   } // end constructor

   public void add(T newEntry)
   {
		checkInitialization();
      int newIndex = lastIndex + 1;
      int parentIndex = newIndex / 2;
      while ( (parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0)
      {
         heap[newIndex] = heap[parentIndex];
         newIndex = parentIndex;
         parentIndex = newIndex / 2; 
      } // end while

      heap[newIndex] = newEntry;
      lastIndex++;
      ensureCapacity();
   } // end add
  
	public T removeMax()
	{
		checkInitialization();
      T root = null;

      if (!isEmpty())
      {
         root = heap[1];            // Return value
         heap[1] = heap[lastIndex]; // Form a semiheap
         lastIndex--;               // Decrease size
         reheap(1);                 // Transform to a heap
      } // end if

      return root;
	} // end removeMax
  
   public T getMax()
   {
		checkInitialization();
      T root = null;

      if (!isEmpty())
         root = heap[1];

      return root;
   } // end getMax

   public boolean isEmpty()
   {
      return lastIndex < 1;
   } // end isEmpty

   public int getSize()
   {
      return lastIndex;
   } // end getSize

   public void clear()
   {
		checkInitialization();
      while (lastIndex > -1)
      {
         heap[lastIndex] = null;
         lastIndex--;
      } // end while
      
      lastIndex = 0;
   } // end clear

   // Precondition: checkInitialization has been called.
   private void reheap(int rootIndex)
   {
      boolean done = false;
      T orphan = heap[rootIndex];
      int leftChildIndex = 2 * rootIndex;

      while (!done && (leftChildIndex <= lastIndex) )
      {
         int largerChildIndex = leftChildIndex; // assume larger
         int rightChildIndex = leftChildIndex + 1;

         if ( (rightChildIndex <= lastIndex) && 
               heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0)
         {
            largerChildIndex = rightChildIndex;
         } // end if

         if (orphan.compareTo(heap[largerChildIndex]) < 0)
         {
            heap[rootIndex] = heap[largerChildIndex];
            rootIndex = largerChildIndex;
            leftChildIndex = 2 * rootIndex;
         } 
         else
            done = true;
      } // end while

      heap[rootIndex] = orphan;
   } // end reheap

   // Doubles the capacity of the array heap if it is full.
   // Precondition: checkInitialization has been called.
	private void ensureCapacity()
	{
		if (lastIndex >= heap.length)
      {
         int newCapacity = 2 * heap.length;
         checkCapacity(newCapacity);
         heap = Arrays.copyOf(heap, newCapacity);
      } // end if
	} // end ensureCapacity
   
   // Throws an exception if this object is not initialized.
   private void checkInitialization()
   {
      if (!initialized)
         throw new SecurityException ("MaxHeap object is not initialized properly.");
   } // end checkInitialization
   
   // Ensures that the client requests a capacity
   // that is not too small or too large.
   private void checkCapacity(int capacity)
   {
      if (capacity < DEFAULT_CAPACITY)
         capacity = DEFAULT_CAPACITY;
      else if (capacity > MAX_CAPACITY)
         throw new IllegalStateException("Attempt to create a heap " +
                                         "whose capacity is larger than " +
                                         MAX_CAPACITY);
   } // end checkCapacity
} // end MaxHeap

