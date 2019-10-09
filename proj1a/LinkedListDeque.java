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
        sentinal.next = new ItemNode(sentinal, i, sentinal);
        sentinal.prev = sentinal.next;
        size = 1;
    }

    public LinkedListDeque(){
        sentinal = new ItemNode(null, null, null);
        sentinal.prev = sentinal;
        sentinal.next = sentinal;
        size = 0;
    }

    public void addFirst(T i){
        sentinal.next = new ItemNode(sentinal,i,sentinal.next);
        sentinal.next.next.prev = sentinal.next;
        size += 1;
    }

    public void addLast(T i){
        sentinal.prev = new ItemNode(sentinal.prev, i, sentinal);
        sentinal.prev.prev.next = sentinal.prev;
        size += 1;
    }

    public boolean isEmpty(){
        return sentinal.next == sentinal;
    }

    public T removeFirst(){
        if (this.isEmpty()) {
            return null;
        }
        ItemNode i = sentinal.next;
        sentinal.next = sentinal.next.next;
        sentinal.next.prev = sentinal;
        return i.item;
    }

    public T removeLast(){
        if (this.isEmpty()) {
            return null;
        }
        ItemNode i = sentinal.prev;
        sentinal.prev = sentinal.prev.prev;
        sentinal.prev.next = sentinal;
        return i.item;
    }

    public int size(){
        return this.size;
    }

    public void printDeque(){
        if (this.isEmpty()) {
            System.out.printf("\n");
            return;
        }
        ItemNode i = sentinal.next;
        System.out.printf("%s", i.item);
        while (i.next!=sentinal){
            System.out.printf(" %s", i.item);
            i = i.next;
        }
        System.out.println();
        return;
    }

    public T get(int index) {
        if (index<0||index>this.size()) {
            System.out.printf("Index out of bounds;");
            return null;
        }

        ItemNode node = sentinal;
        for (int i = 0; i < index; i++){
            node = node.next;
        }
        return node.item;
    }

    private T getRecur(ItemNode i, int index){
        if (index == 0 ) return i.item;
        return getRecur(i.next,index-1);
    }

    public T getRecursive(int index) {
        if (index<0||index>this.size()) {
            System.out.printf("Index out of bounds;");
            return null;
        } else return getRecur(sentinal.next,index-1);
    }

    public LinkedListDeque(LinkedListDeque other){
        sentinal = new ItemNode(null, null, null);
        sentinal.prev = sentinal;
        sentinal.next = sentinal;
        size = 0;

        for (int i = 0; i < other.size(); i++){
            addLast((T) other.get(i));
        }
    }

}
