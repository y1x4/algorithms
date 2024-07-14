import java.util.*;

public class LeetCode_0146_LRU {

    class LRUCache {
        // 额外的头尾节点，双向链表方便从任意处增删
        Node head, tail;
        int maxCapacity;
        // key-node map
        Map<Integer, Node> map;

        public LRUCache(int capacity) {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.prev = tail;
            head.next = tail;
            tail.prev = head;
            tail.next = head;
            maxCapacity = capacity;
            map = new HashMap<>(capacity);
        }

        public int get(int key) {
            Node node = map.get(key);
            if (node != null) {
                removeNode(node);
                addToHead(node);
                return node.val;
            }
            return -1;
        }

        public void put(int key, int value) {
            Node node = map.get(key);
            if (node != null) {
                // 如果存在，更新值，将节点从链表中移除
                node.val = value;
                removeNode(node);
            } else {
                // 如果达到空间上限，移除末尾的节点
                if (map.size() == maxCapacity) {
                    map.remove(tail.prev.key);
                    removeNode(tail.prev);
                }
                // 创建新节点
                node = new Node(key, value);
                map.put(key, node);
            }
            // 添加到头部
            addToHead(node);
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addToHead(Node node) {
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            node.prev = head;
        }

        class Node {
            int key, val;
            Node prev, next;

            public Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }
    }


    public int longestSubarray(int[] nums) {
        int n = nums.length, ans = 0;
        int withZero = 0, withOutZero = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                withZero++;
                withOutZero++;
            } else {
                withZero = withOutZero;
                withOutZero = 0;
            }
            ans = Math.max(ans, withZero);
        }
        return ans == n ? n - 1 : ans;
    }


    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        int i = 0, n = words.length;
        while (i < n) {
            int endIdx = findEnd(words, maxWidth, i);
            if (endIdx == n -1) {
                StringBuilder sb = new StringBuilder();
                for (int j = i; j < endIdx; j++) {
                    sb.append(words[j]).append(' ');
                }
                sb.append(words[endIdx]);
                sb.append(" ".repeat(Math.max(0, maxWidth - sb.length())));
                res.add(sb.toString());
                break;
            } else {
                int space = maxWidth;
                for (int j = i; j <= endIdx; j++) {
                    space -= words[j].length();
                }
                StringBuilder sb = new StringBuilder();
                for (int j = i; j < endIdx; j++) {
                    int extra = (j - i + 1) <= (space % (endIdx - i)) ? 1 : 0;
                    sb.append(words[j]).append(" ".repeat((space / (endIdx - i)) + extra));
                }
                sb.append(words[endIdx]);
                sb.append(" ".repeat(Math.max(0, maxWidth - sb.length())));
                res.add(sb.toString());
                i = endIdx + 1;
            }
        }
        return res;
    }
    public int findEnd(String[] words, int maxWidth, int i) {
        int len = words[i].length();
        while (++i < words.length) {
            if (len + words[i].length() + 1 > maxWidth) {
                break;
            }
            len += words[i].length() + 1;
        }
        return i - 1;
    }

    public String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        int i = 0, n = path.length();
        while (i < n) {
            while (i < n && path.charAt(i) == '/') {
                i++;
            }
            if (i < n) {
                int j = i;
                while (j < n && path.charAt(j) != '/') {
                    j++;
                }
                String curr = path.substring(i, j);
                if (Objects.equals(curr, "..")) {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                } else if (!Objects.equals(curr, ".")) {
                    stack.push(curr);
                }
                i = j;
            }
        }
        return "/" + String.join("/",stack);
    }


    public static void main(String[] args) {
        LeetCode_0146_LRU test = new LeetCode_0146_LRU();
        System.out.println(test.fullJustify(new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16));
    }
}
