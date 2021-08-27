package QueueAndStackPackage;

/**
 An interface for the ADT stack.
 @author Frank M. Carrano
 @author Timothy M. Henry
 @version 4.0
 */
public interface StackInterface<T>
{
    /** Adds a new entry to the top of this stack.
     @param newEntry  An object to be added to the stack. */
    void push(T newEntry);

    /** Removes and returns this stack's top entry.
     @return  The object at the top of the stack.
     // @throws  EmptyStackException if the stack is empty before the operation. */
    T pop();

    /** Retrieves this stack's top entry.
     @return  The object at the top of the stack.
     // @throws  EmptyStackException if the stack is empty. */
    T peek();

    /** Detects whether this stack is empty.
     @return  True if the stack is empty. */
    boolean isEmpty();

    /** Removes all entries from this stack. */
    void clear();
} // end StackInterface
