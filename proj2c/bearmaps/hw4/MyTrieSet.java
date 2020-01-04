package bearmaps.hw4;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {

    Node root;

    public MyTrieSet() {
        root = new Node(' ', false);
    }

    @Override
    public void clear() {
        root = new Node(' ', false);
    }

    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return false;
        }
        char[] keyChar = key.toCharArray();
        Node curr = root;
        for (int i = 0; i < keyChar.length; i++) {
            if (!curr.next.containsKey(keyChar[i])) {
                return false;
            }
            curr = curr.next.get(keyChar[i]);
        }
        if (!curr.isKey) {
            return false;
        }
        return true;
    }

    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        char[] keyChar = key.toCharArray();
        Node curr = root;
        for (int i = 0; i < keyChar.length; i++) {
            if (!curr.next.containsKey(keyChar[i])) {
                curr.next.put(keyChar[i], new Node(keyChar[i], false));
            }
            curr = curr.next.get(keyChar[i]);
        }
        curr.isKey = true;
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> ret = new LinkedList<>();
        Node curr = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            if (!curr.next.containsKey(prefix.charAt(i))) {
                return ret;
            }
            curr = curr.next.get(prefix.charAt(i));
        }
        if (curr.isKey) {
            ret.add(prefix);
        }
        for (char x : curr.next.keySet()) {
            prefixHelper(curr.next.get(x), prefix, ret);
        }
        return ret;
    }

    private void prefixHelper(Node curr, String pre, List<String> ls) {
        pre = pre + curr.ch;
        if (curr.isKey) {
            ls.add(pre);
        }
        if (!curr.next.isEmpty()) {
            for (char x : curr.next.keySet()) {
                prefixHelper(curr.next.get(x), pre, ls);
            }
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

    private class Node {
        private boolean isKey;
        private char ch;
        private Hashtable<Character, Node> next;

        private Node(char c, boolean b) {
            ch = c;
            isKey = b;
            next = new Hashtable<>();
        }
    }
}
