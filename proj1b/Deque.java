public interface Deque<Item> {

    void addFirst(Item i);

    void addLast(Item i);

    int size();

    default boolean isEmpty() {
        return size()==0;
    };

    Item removeFirst();

    Item removeLast();

    Item get(int i);

    void printDeque();


}
