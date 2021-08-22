package GraphPackage;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
   A class that implements the ADT dictionary by using a chain of nodes.
   The dictionary is unsorted and has distinct search keys.
  
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public class LinkedDictionary<K, V> implements DictionaryInterface<K, V>, Serializable
{
	private Node firstNode;   // Reference to first node of chain
	private int  numberOfEntries; 
	
	public LinkedDictionary()
	{
      initializeDataFields();
	} // end default constructor
	
   public V add(K key, V value)
   {
      V result = null;

      // Search chain for a node containing key
      Node currentNode = firstNode;
      while ( (currentNode != null) && !key.equals(currentNode.getKey()) )
      {
         currentNode = currentNode.getNextNode();
      } // end while

      if (currentNode == null)
      {
         // Key not in dictionary; add new node at beginning of chain
         Node newNode = new Node(key, value);
         newNode.setNextNode(firstNode);
         firstNode = newNode;
         numberOfEntries++;
      }
      else
      {
         // Key in dictionary; replace corresponding value
         result = currentNode.getValue();
         currentNode.setValue(value); // Replace value
      } // end if

      return result;
   } // end add

   public V remove(K key)
	{
   	V result = null;  // Return value
   	
		if (!isEmpty())
		{	
         // Search chain for a node containing key;
         // save reference to preceding node
			Node currentNode = firstNode;
			Node nodeBefore = null;
         
         while ( (currentNode != null) && !key.equals(currentNode.getKey()) )
			{
				nodeBefore = currentNode;
				currentNode = currentNode.getNextNode();
			} // end while
			
			if (currentNode != null)
			{
				// node found; remove it
				Node nodeAfter = currentNode.getNextNode();  // Node after the one to be removed

				if (nodeBefore == null)
					firstNode = nodeAfter;
				else
					nodeBefore.setNextNode(nodeAfter);        // Disconnect the node to be removed

				result = currentNode.getValue();             // Get ready to return removed entry
			  numberOfEntries--;                            // Decrease length for both cases
			} // end if
		} // end if
			
      return result;  
   } // end remove

   public V getValue(K key)
   {
      V result = null;

      // Search for a node that contains key
      Node currentNode = firstNode;

      while ( (currentNode != null) && !key.equals(currentNode.getKey()) )
      {
         currentNode = currentNode.getNextNode();
      } // end while

      if (currentNode != null)
      {
         // Search key found
         result = currentNode.getValue();
      } // end if

      return result;
   } // end getValue

	public boolean contains(K key)
   {
   	return getValue(key) != null; 
   } // end contains

   public boolean isEmpty()
   {
      return numberOfEntries == 0;
   } // end isEmpty
	
   public int getSize()
   {
      return numberOfEntries;
   } // end getSize

	public final void clear()
	{ 
      initializeDataFields();
   } // end clear

	public Iterator<K> getKeyIterator()
	{
		return new KeyIterator();
	} // end getKeyIterator
	
	public Iterator<V> getValueIterator()
	{
		return new ValueIterator();
	} // end getValueIterator

   // Initializes the class's data fields to indicate an empty list.
   private void initializeDataFields()
   {
		firstNode = null;
		numberOfEntries = 0;
   } // end initializeDataFields
	
// Same as in SortedLinkedDictionary.
// Since iterators implement Iterator, methods must be public.
	private class KeyIterator implements Iterator<K>
	{
		private Node nextNode;
		
		private KeyIterator()
		{
			nextNode = firstNode;
		} // end default constructor
		
		public boolean hasNext() 
		{
			return nextNode != null;
		} // end hasNext
		
		public K next()
		{
			K result;
			
			if (hasNext())
			{
				result = nextNode.getKey();
				nextNode = nextNode.getNextNode();
			}
			else
			{
				throw new NoSuchElementException();
			} // end if
		
			return result;
		} // end next
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		} // end remove
	} // end KeyIterator 
	
	private class ValueIterator implements Iterator<V>
	{
		private Node nextNode;
		
		private ValueIterator()
		{
			nextNode = firstNode;
		} // end default constructor
		
		public boolean hasNext() 
		{
			return nextNode != null;
		} // end hasNext
		
		public V next()
		{
			V result;
			
			if (hasNext())
			{
				result = nextNode.getValue();
				nextNode = nextNode.getNextNode();
			}
			else
			{
				throw new NoSuchElementException();
			} // end if
		
			return result;
		} // end next
		
		public void remove()
		{
			throw new UnsupportedOperationException();
		} // end remove
	} // end getValueIterator

	private class Node implements Serializable
	{
		private K key;
		private V value;
		private Node next;

		private Node(K searchKey, V dataValue)
		{
			key = searchKey;
			value = dataValue;
			next = null;	
		} // end constructor
		
		private Node(K searchKey, V dataValue, Node nextNode)
		{
			key = searchKey;
			value = dataValue;
			next = nextNode;	
		} // end constructor
		
		private K getKey()
		{
			return key;
		} // end getKey
		
		private V getValue()
		{
			return value;
		} // end getValue

		private void setValue(V newValue)
		{
			value = newValue;
		} // end setValue

		private Node getNextNode()
		{
			return next;
		} // end getNextNode
		
		private void setNextNode(Node nextNode)
		{
			next = nextNode;
		} // end setNextNode
	} // end Node
} // end LinkedDictionary
		
