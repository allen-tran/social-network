package GraphPackage;

public final class LinkedQueue<T> implements QueueInterface<T> {
    private Node firstNode;
    private Node lastNode;

    /**
     * Adds a new entry to the back of this queue.
     *
     * @param newEntry An object to be added.
     */
    public void enqueue(T newEntry) {
        Node newNode = new Node(newEntry, null);
        if (isEmpty())
            firstNode = newNode;
        else
            lastNode.setNextNode(newNode);
        lastNode = newNode;
    }

    /**
     * Removes and returns the entry at the front of this queue.
     *
     * @return The object at the front of the queue.
     * @throws EmptyQueueException if the queue is empty before the operation.
     */
    public T dequeue() {
        T front = getFront();

        assert firstNode != null;
        firstNode.setData();
        firstNode = firstNode.getNextNode();

        if (firstNode == null)
            lastNode = null;
        return front;
    }

    /**
     * Retrieves the entry at the front of this queue.
     *
     * @return The object at the front of the queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    public T getFront() {
        if (isEmpty())
            throw new EmptyQueueException();
        else {
            assert firstNode != null;
            return firstNode.getData();
        }
    }

    /**
     * Detects whether this queue is empty.
     *
     * @return True if the queue is empty, or false otherwise.
     */
    public boolean isEmpty() {
        return (firstNode == null) && (lastNode == null);
    }

    /**
     * Removes all entries from this queue.
     */
    public void clear() {
        firstNode = null;
        lastNode = null;
    }

    class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this(data, null);
        }

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        // set the next node for the current node
        private void setNextNode(Node nodeToSet) {
            this.next = nodeToSet;
        }

        // return the next node of the current node
        private Node getNextNode() {
            return this.next;
        }

        // set the data of the current node
        private void setData() {
            this.data = null;
        }

        // return the data of the current node
        private T getData() {
            return this.data;
        }
    } // end Node
} // end LinkedQueue
