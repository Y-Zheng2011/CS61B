public class LinkedListDeque<T> {

    private class ItemNode{
        public ItemNode prev;
        public T item;
        public ItemNode next;

        public ItemNode(ItemNode p, T i, ItemNode n){
            prev = p;
            item = i;
            next = n;
        }
    }

    private ItemNode sentinal;
    private int size;

    public LinkedListDeque(T i){
        sentinal = new ItemNode(null, i, null);
        sentinal.prev = new ItemNode(sentinal, i, sentinal);
        sentinal.next = new ItemNode(sentinal, i, sentinal);
        size = 1;
    }

    public void addFirst(T i){
        sentinal.next = new ItemNode(i,sentinal.next);
        size += 1;
    }

    public void addLast(T i){
        sentinal
    }

    public int size(){
        return this.size;
    }
}
