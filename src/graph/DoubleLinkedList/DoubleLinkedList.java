package graph.DoubleLinkedList;

public class DoubleLinkedList<E> {

    // Attributes
    private Node<E> head, tail;
    private int size;

    /**
     * Constructor
     */
    public DoubleLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }
    /**
     * Add to the tail
     *
     * @param data
     * @return added node
     */
    public Node<E> add(E data) {
        Node<E> node = new Node<E>(data);

        // If list is empty, add to the head
        if (size == 0) {
            head = node;

            // If list is not empty, add to the tail
        } else {
            tail.next = node;
            node.previous = tail;
        }

        // Adjust the tail and size
        tail = node;
        size++;
        return node;
    }

    /**
     * Remove a node
     *
     * @param node
     */
    public void remove(Node<E> node) {

        // If removing the head
        if (head == node) {

            // If only one node in the list, then make head points to null
            if (size == 1) {
                head = null;

                // If more then one node exits, the head points to the second node
            } else {
                node.next.previous = null;
                head = node.next;
            }

            // If removing the tail, make tail points to second to last
        } else if (tail == node) {
            node.previous.next = null;
            tail = node.previous;

            // If removing in the middle
        } else {
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }

        // Destroy the node configuration and adjust list size
        node.destroy();
        size--;
    }

    /**
     * Size of the DLL
     *
     * @return size
     */
    public int size() {
        return size;
    }

    /**
     * Get the head of the doubly linked list
     *
     * @return head
     */
    public Node<E> first() {
        return head;
    }

    /**
     * To String [1,2,3,4 ... ]
     */
    public String toString() {
        String output = "[";
        Node<E> tmp = head;

        while (tmp != null) {
            output += tmp.toString();
            if (tmp.next != null)
                output += ", ";
            tmp = tmp.next;
        }
        output += "]";
        return output;
    }

    /**
     * Iterator sits in between nodes or before head or after tail
     *
     * @return iterator
     */
    public NodeIterator<E> iterator() {

        // Create an anonymous class that implements NodeIterator
        return new NodeIterator<E>() {
            private Node<E> position = head;

            /**
             * Get next element in the list
             */
            public E next() {
                Node<E> node = position;
                position = position.next;
                return node.getData();
            }

            /**
             * Checks if there's a next node
             */
            public boolean hasNext() {
                return position != null;
            }

            /**
             * Concatenate two list
             * @param secondIter
             * @return new list
             */
            public NodeIterator<E> concatenate(NodeIterator<E> secondIter) {
                DoubleLinkedList<E> newList = new DoubleLinkedList<E>();
                while (this.hasNext())
                    newList.add(this.next());
                while (secondIter.hasNext())
                    newList.add(secondIter.next());
                return newList.iterator();
            }

            /**
             * Get size of iterator
             */
            public int size() {
                return DoubleLinkedList.this.size();
            }

            /**
             * to String inherits the outer class
             */
            public String toString() {
                return DoubleLinkedList.this.toString();
            }
        };
    }
}
