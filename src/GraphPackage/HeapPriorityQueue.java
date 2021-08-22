package GraphPackage;

import java.io.Serializable;

/**
   A class that implements the ADT priority queue by using a maxheap.
   
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public final class HeapPriorityQueue<T extends Comparable<? super T>>
                   implements PriorityQueueInterface<T>, Serializable
{
	private MaxHeapInterface<T> pq;	
	
	public HeapPriorityQueue()
	{
		pq = new MaxHeap<>();
	} // end default constructor
	
	public void add(T newEntry)
	{ 
		pq.add(newEntry);
	} // end add

	public T remove()
	{
		return pq.removeMax();
	} // end remove
		
	public T peek()
	{
		return pq.getMax();
	} // end peek

	public boolean isEmpty()
	{
		return pq.isEmpty();
	} // end isEmpty
	
	public int getSize()
	{
		return pq.getSize();
	} // end getSize

	public void clear()
	{
		pq.clear();
	} // end clear
} // end HeapPriorityQueue