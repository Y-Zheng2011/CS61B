package bearmaps.proj2ab;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private class Node {
        T item;
        double priority;

        public Node(T i, double p) {
            item = i;
            priority = p;
        }
    }

    private Map<T, Integer> itemMap;
    private Node[] minHeap;
    private int size;
    private int heapSize;

    @SuppressWarnings("unchecked")
    public ArrayHeapMinPQ(int size) {
        heapSize = size * 2;
        minHeap = new ArrayHeapMinPQ.Node[heapSize];
        itemMap = new HashMap<>();
        this.size = 0;
    }

    public ArrayHeapMinPQ() {
        this(16);
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        if (size + 1 == heapSize) {
            resize();
        }
        size++;
        minHeap[size] = new Node(item, priority);
        itemMap.put(item, size);
        swimUp(size);
    }

    @Override
    public boolean contains(T item) {
        return itemMap.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return minHeap[1].item;
    }

    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T ret = minHeap[1].item;
        minHeap[1] = minHeap[size];
        minHeap[size] = null;
        itemMap.remove(ret);
        size--;
        swimDown(1);
        if (size > 0 && size * 4 < heapSize) {
            shrink();
        }
        return ret;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = itemMap.get(item), p = parent(index);
        setPriority(index, priority);
        if (getPriority(index) > getPriority(p)) {
            swimDown(index);
        } else {
            swimUp(index);
        }
    }

    public void print() {
        int depth = ((int) (Math.log(minHeap.length) / Math.log(2)));
        int level = 0;
        int itemsUntilNext = (int) Math.pow(2, level);
        for (int j = 0; j < depth; j++) {
            System.out.print(" ");
        }

        for (int i = 1; i <= size; i++) {
            System.out.print(minHeap[i].item + " ");
            if (i == itemsUntilNext) {
                System.out.println();
                level++;
                itemsUntilNext += Math.pow(2, level);
                depth--;
                for (int j = 0; j < depth; j++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        heapSize = heapSize * 2;
        Node[] tmp = new ArrayHeapMinPQ.Node[heapSize];
        System.arraycopy(minHeap, 1, tmp, 1, size);
        minHeap = tmp;
    }

    @SuppressWarnings("unchecked")
    private void shrink() {
        heapSize = size * 2;
        Node[] tmp = new ArrayHeapMinPQ.Node[heapSize];
        System.arraycopy(minHeap, 1, tmp, 1, size);
        minHeap = tmp;
    }

    private double getPriority(int k) {
        return minHeap[k].priority;
    }

    private void setPriority(int k, double priority) {
        minHeap[k].priority = priority;
    }

    private void swimUp(int k) {
        int p = parent(k);
        if ((p > 0) && (getPriority(p) > getPriority(k))) {
                swap(k, p);
        }
    }

    private void swimDown(int k) {
        if (leftChild(k) > 0 && rightChild(k) > 0) {
            twoChildren(k);
        } else {
            oneChild(k);
        }
    }

    private void twoChildren(int k) {
        int left = leftChild(k), right = rightChild(k);
        if (getPriority(left) > getPriority(right)) {
            if (getPriority(right) > getPriority(k)) {
                swap(right, k);
                swimDown(right);
            }
        } else if (getPriority(left) > getPriority(k)) {
            swap(left, k);
            swimDown(left);
        }
    }

    private void oneChild(int k) {
        if (leftChild(k) > 0 && getPriority(leftChild(k)) > getPriority(k)) {
            swap(k, leftChild(k));
        }
    }

    private void swap(int i, int j) {
        Node tmp = minHeap[i];
        minHeap[i] = minHeap[j];
        minHeap[j] = tmp;
        itemMap.replace(minHeap[i].item,i);
        itemMap.replace(minHeap[j].item,j);
    }

    private int leftChild(int k) {
        if (k * 2 <= size) {
            return k * 2;
        } else {
            return -1;
        }
    }

    private int rightChild(int k) {
        if (k * 2 + 1 <= size) {
            return k * 2 + 1;
        } else {
            return -1;
        }
    }

    private int parent(int k) {
        return k / 2;
    }
}
