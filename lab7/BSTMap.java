import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V> {

    //region innerClass
    private class BST{
        BST left;
        BST right;
        K key;
        V val;
        int size;

        BST(K k, V v) {
            key = k;
            val = v;
            left = null;
            right = null;
            size = 1;
        }

        V get(BST b, K k) {
            if (b == null || k == null) {
                return null;
            }
            if (k.equals(b.key)) {
                return b.val;
            } else if (k.compareTo(b.key) < 0) {
                return get(b.left, k);
            }
            return get(b.right, k);
        }

        BST insert(BST b, K k, V v) {
            if (b == null) {
                return new BST(k,v);
            }
            if (k.compareTo(b.key) < 0) {
                b.left = insert(b.left, k, v);
            } else if (k.compareTo(b.key) > 0) {
                b.right = insert(b.right, k, v);
            } else {
                b.val = v;
            }
            b.size = size(b.left) + size(b.right) + 1;
            return b;
        }

        int size() {
            return size(this);
        }

        private int size(BST b) {
            if (b == null) {
                return 0;
            }
            return b.size;
        }

        BST remove(K k) {
            return remove(this, k);
        }

        private BST remove(BST b, K k) {
            if (b == null) {
                return null;
            }
            int r = k.compareTo(b.key);
            if (r < 0) {
                b.left =  remove(b.left, k);
            } else if (r > 0) {
                b.right = remove(b.right, k);
            } else {
                if (b.left == null) {
                    return b.right;
                }
                if (b.right == null) {
                    return b.left;
                }
                BST min = findMin(b.right);
                b.key = min.key;
                b.val = min.val;
                b.right = remove(b.right, min.key);
            }
            b.size = size(b.left) + size(b.right) + 1;
            return b;
        }
    }
    //endregion

    //region Body
    private int size;
    private BST bst;
    Set<K> keySet;

    public BSTMap() {
        size = 0;
        keySet = new HashSet<>();
    }

    @Override
    public void clear() {
        size = 0;
        bst = null;
    }

    @Override
    public boolean containsKey(K k) {
        return find(bst, k);
    }

    private boolean find(BST b, K k) {
        if (b == null) {
            return false;
        } else if (b.key.compareTo(k) > 0) {
            return find(b.left, k);
        } else if (b.key.compareTo(k) < 0) {
            return find(b.right, k);
        } else {
            return true;
        }
    }

    @Override
    public V get(K k) {
        if (bst == null) {
            return null;
        }
        return bst.get(bst, k);
    }

    @Override
    public void put(K k, V v) {
        if (bst == null) {
            bst = new BST(k, v);
        } else {
            bst.insert(bst, k, v);
        }
        keySet.add(k);
    }

    @Override
    public int size() {
        if (bst == null) {
            return 0;
        }
        return bst.size();
    }

    @Override
    public Set<K> keySet() {
        return  keySet;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V ret = get(key);
        bst = bst.remove(key);
        return ret;
    }

    private BST findMin(BST b) {
        if (b.left == null) {
            return b;
        }
        return findMin(b.left);
    }

    @Override
    public V remove(K key, V valu) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    private class BSTMapIter implements Iterator<K> {

        private BST curr;

        public BSTMapIter() {
            curr = bst;
        }

        @Override
        public boolean hasNext() {
            return (curr.right != null || curr.left != null);
        }

        @Override
        public K next() {
            return null;
        }
    }
    //endregion
}
