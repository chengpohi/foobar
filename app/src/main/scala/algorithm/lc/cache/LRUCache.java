package algorithm.lc.cache;

import java.util.HashMap;

public class LRUCache {
    public static class LRUCache2 {
        // key -> Node(key, val)
        private HashMap<Integer, Node> map;
        // Node(k1, v1) <-> Node(k2, v2)...
        private DoubleList cache;
        // 最大容量
        private int cap;

        public LRUCache2(int capacity) {
            this.cap = capacity;
            map = new HashMap<>();
            cache = new DoubleList();
        }

        public int get(int key) {
            if (!map.containsKey(key)) { return -1; }
            int val = map.get(key).val;
            // 利用 put 方法把该数据提前
            put(key, val);
            return val;
        }

        public void put(int key, int val) {
            // 先把新节点 x 做出来
            Node x = new Node(key, val);

            if (map.containsKey(key)) {
                // 删除旧的节点，新的插到头部
                cache.remove(map.get(key));
                cache.addFirst(x);
                // 更新 map 中对应的数据
                map.put(key, x);
            } else {
                if (cap == cache.size()) {
                    // 删除链表最后一个数据
                    Node last = cache.removeLast();
                    map.remove(last.key);
                }
                // 直接添加到头部
                cache.addFirst(x);
                map.put(key, x);
            }
        }
    }

    public static class Node {
        public int key, val;
        public Node next, prev;

        public Node(int k, int v) {
            this.key = k;
            this.val = v;
        }
    }

    public static class DoubleList {
        // 在链表头部添加节点 x，时间 O(1)
        public void addFirst(Node x) {

        }

        // 删除链表中的 x 节点（x 一定存在）
        // 由于是双链表且给的是目标 Node 节点，时间 O(1)
        public void remove(Node x) {

        }

        // 删除链表中最后一个节点，并返回该节点，时间 O(1)
        public Node removeLast() {
            return null;
        }

        // 返回链表长度，时间 O(1)
        public int size() {
            return 0;
        }
    }
}
