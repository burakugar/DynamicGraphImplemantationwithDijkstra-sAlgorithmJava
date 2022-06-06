package graph.DoubleLinkedList;

public class Node<E> {
    protected Node<E> next, previous;
    private E data;

    /**
     * Constructor
     *
     * @param data
     * @param previous
     * @param next
     */
    protected Node(E data, Node<E> previous, Node<E> next) {
        this.data = data;
        this.previous = previous;
        this.next = next;
    }

    /**
     * Overloaded constructor
     *
     * @param data
     */
    protected Node(E data) {
        this(data, null, null);
    }

    /**
     * Checks if there is a next node
     *
     * @return boolean
     */
    public boolean hasNext() {
        return next != null;
    }

    /**
     * Checks if there is a previous node
     *
     * @return boolean
     */
    public boolean hasPrevious() {
        return previous != null;
    }

    /**
     * Get the data stored in the node
     *
     * @return data in the node
     */
    public E getData() {
        return data;
    }

    /**
     * Set the data stored in the node
     *
     * @param data
     */
    public void setData(E data) {
        this.data = data;
    }

    /**
     * Get next
     *
     * @return next
     */
    public Node<E> next() {
        return next;
    }

    /**
     * Get previous
     *
     * @return previous
     */
    public Node<E> previous() {
        return previous;
    }

    /**
     * Destroy a node
     */
    protected void destroy() {
        data = null;
        next = null;
        previous = null;
    }

    /**
     * To string <Object>
     */
    public String toString() {
        return String.format("%s", data.toString());
    }
}