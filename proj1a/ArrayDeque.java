public class ArrayDeque<T> {

    private T[] aDeque;
    private int head, tail;
    private int size;

    public ArrayDeque(){
        aDeque = (T[]) new Object[8];
        head = 3;
        tail = 4;
        size = 0;
    }

    public boolean isEmpty(){
        return head==tail-1;
    }

    public void addFirst(T i){
        aDeque[head] = i;
        if (head > 0) {
            head -= 1;
        } else {
            head = aDeque.length-1;
        }
        size += 1;
    }

    public void addLast(T i){
        aDeque[tail] = i;
        if (tail < aDeque.length-1) {
            tail += 1;
        } else {
            tail = 0;
        }
        size += 1;
    }

    public int size(){
        return this.size;
    }

    public T removeFirst(){
        T tmp = aDeque[head];
        aDeque[head] = null;
        if (head == aDeque.length-1){
            head = 0;
        } else {
            head += 1;
        }
        size -= 1;
        return tmp;
    }

    public T removeLast(){
        T tmp = aDeque[tail];
        aDeque[tail] = null;
        if (tail == 0){
            tail = aDeque.length-1;
        } else {
            tail -= 1;
        }
        size -= 1;
        return tmp;
    }

    public T get(int index) {
        if (index > aDeque.length) return null;
        if (head+index > aDeque.length-1){
            return aDeque[head + index - aDeque.length];
        }
        return aDeque[head + index];
    }

    public ArrayDeque(ArrayDeque other){
        aDeque = (T[]) new Object[2*other.size()]; // To obtain a 50% usage ratio
        head = other.size()/2;
        tail = head + 1;
        for (int i = 1; i < other.size(); i++) {
            this.addLast((T)other.get(i));
        }
    }

    private void expand(){
        T[] tmp = (T[]) new Object[2*size];
        head = size/2; tail = head + 1;
        for (int i = 1; i < size; i++) {
            tmp[tail] = this.get(i);
            tail++;
        }
        aDeque = tmp;
    }

    private void shrink(){

    }
}
