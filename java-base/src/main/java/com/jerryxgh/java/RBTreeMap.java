package com.jerryxgh.java;

public class RBTreeMap<K, V> {
    private enum ColorEnum {
        RED, BLACK
    }

    private static class TreeNode<K, V> {
        K key;
        V value;

        TreeNode left;
        TreeNode right;

        TreeNode parent;

        ColorEnum color;
    }

    private TreeNode<K, V> root;

    public V get(K key) {
        TreeNode<K, V> t = getNode(key);
        if (null == t) {
            return null;
        }
        return t.value;
    }

    /**
     * 类型检查，key必须实现comparable接口
     * @param k1
     * @param k2
     * @return
     */
    final int compare(Object k1, Object k2) {
        return ((Comparable<? super K>)k1).compareTo((K)k2);
    }

    private TreeNode<K, V> newNode(K key, V value, TreeNode<K, V> parent) {
        TreeNode<K, V> newNode = new TreeNode<K, V>();
        newNode.key = key;
        newNode.value = value;
        newNode.parent = parent;

        return  newNode;
    }

    public V put(K key, V value) {
        if (null == root) {
            root = newNode(key, value, null);
            return null;
        }

        Comparable<? super K> k = (Comparable<? super K>) key;
        TreeNode<K, V> t = root;
        TreeNode<K, V> parent = null;
        int cmp;
        do {
            cmp = k.compareTo(t.key);
            if (cmp == 0) {
                V oldV = t.value;
                t.value = value;
                return oldV;
            } else if (cmp < 0) {
                parent = t;
                t = t.left;
            } else if (cmp > 0) {
                parent = t;
                t = t.right;
            }
        } while (null != t);

        TreeNode<K, V> newNode = newNode(key, value, parent);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        return null;
    }

    public V remove(Object key) {
        TreeNode<K, V> t = getNode(key);
        if (null == t) {
            return null;
        }

        return null;

    }

    private TreeNode<K, V> getNode(Object key) {
        Comparable<? super K> k = (Comparable<? super K>) key;
        TreeNode<K, V> t = root;
        while (null != t) {
            int cmp = k.compareTo(t.key);
            if (cmp == 0) {
                return t;
            } else if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            }
        }

        return null;
    }
}
