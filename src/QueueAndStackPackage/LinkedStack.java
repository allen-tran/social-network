package QueueAndStackPackage;

import java.io.Serializable;

import GraphPackage.EmptyStackException;

public final class LinkedStack<T> implements StackInterface<T>, Serializable
{

    private Node topNode; // References the first node in the chain

    //default constructor
    public LinkedStack() {
        topNode = null;
    }


    ///////////////////// Implement the unimplemented methods ////////////////////////

    /** Adds a new entry to the top of this stack.
     @param newEntry  An object to be added to the stack. */
    public void push(T newEntry) {
        topNode = new Node(newEntry, topNode);
    }


    /** Removes and returns this stack's top entry.
     @return  The object at the top of the stack.
     // @throws  EmptyStackException if the stack is empty before the operation. */
    public T pop() {
        T top = peek();
        assert topNode != null;
        topNode = topNode.getNextNode();
        return top;
    }


    /** Retrieves this stack's top entry.
     @return  The object at the top of the stack.
     // @throws  EmptyStackException if the stack is empty. */
    public T peek() {
        if (isEmpty())
            throw new EmptyStackException();
        else
            return topNode.getData();
    }

    /** Detects whether this stack is empty.
     @return  True if the stack is empty. */
    public boolean isEmpty() {
        return topNode == null;
    }


    /** Removes all entries from this stack. */
    public void clear() {
        topNode = null;
    }


    //////// Node class ////////
    private class Node implements Serializable
    {
        private T data;
        private Node next;

        private Node(T dataPortion) {
            this(dataPortion, null);
        }

        private Node(T dataPortion, Node link) {
            data = dataPortion;
            next = link;
        }

        private T getData() {
            return data;
        }

        private Node getNextNode() {
            return next;
        }
    }// end Node
}// end LinkedStack
