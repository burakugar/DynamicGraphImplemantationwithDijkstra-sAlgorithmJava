package graph.DoubleLinkedList;

public interface NodeIterator<E> {
    public E next();

    public boolean hasNext();

    public NodeIterator<E> concatenate(NodeIterator<E> secondIter);

    public int size();
}
