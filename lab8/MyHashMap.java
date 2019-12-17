import edu.princeton.cs.algs4.StdRandom;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyHashMap<K,V> implements Map61B<K,V> {

    int bucketSize, mapSize;
    double loadFactor;
    Node<K, V>[] bucket;
    Set<K> keySet = new HashSet<>();

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        this.mapSize = 0;
        this.bucketSize = 16;
        this.loadFactor = 0.75;
        bucket = new Node[bucketSize];
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialSize) {
        this.mapSize = 0;
        this.bucketSize = initialSize;
        this.loadFactor = 0.75;
        bucket = new Node[bucketSize];
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.mapSize = 0;
        this.loadFactor = loadFactor;
        this.bucketSize = initialSize;
    }

    @Override
    public void clear() {
        for (int i = 0; i < bucketSize; i++) {
            bucket[i] = new Node<>();
        }
        keySet = new HashSet<>();
        mapSize = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return keySet.contains(key);
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int i = hash(key);
        if (bucket[i] == null) {
            return null;
        }
        return bucket[i].get(key);
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7FFFFFFF) % bucketSize;
    }

    @Override
    public int size() {
        return mapSize;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int i = hash(key);
        if (bucket[i] == null) {
            bucket[i] = new Node<>(key, value);
            keySet.add(key);
            mapSize++;
        } else {
            this.bucket[i] = this.bucket[i].put(key, value);
            if (!keySet.contains(key)) {
                mapSize++;
            }
            keySet.add(key);
        }
        if (mapSize > loadFactor * bucketSize) {
            resize();
        }
    }

    public void resize() {

        Node<K, V>[] tmp = new Node[bucketSize*2];
        for (K k : keySet) {
            int i = hash(k);
            V val = get(k);
            tmp[i] = new Node<>();
            tmp[i] = tmp[i].put(k, val);
        }
        bucket = tmp;
        bucketSize = bucketSize * 2;
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    private static class Node<K, V> {

        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        Node() {
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }

        V get(K key) {
            if (key == null) {
                throw new IllegalArgumentException();
            }
            Node<K,V> t = this;
            while (t != null) {
                if (key.equals(t.key)) {
                    return t.value;
                }
                t = t.next;
            }
            return null;
        }

        Node<K,V> put(K k, V v) {
            if (k == null) {
                throw new IllegalArgumentException();
            }
            Node<K,V> t = this;
            while (t != null) {
                if (k.equals(t.key)) {
                    t.value = v;
                    return t;
                }
                t = t.next;
            }
            t = new Node<>(k, v);
            return t;
        }
    }

}
