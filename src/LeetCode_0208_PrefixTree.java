import java.util.Objects;

public class LeetCode_0208_PrefixTree {

    /**
     * 1 <= word.length, prefix.length <= 2000
     * word and prefix consist only of lowercase English letters.
     * At most 3 * 104 calls in total will be made to insert, search, and startsWith.
     */
    static class Trie {

        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode curr = root;
            char c;
            for (int i = 0; i < word.length(); i++) {
                c = word.charAt(i);
                if (curr.children[c - 'a'] == null) {
                    curr.children[c - 'a'] = new TrieNode();
                }
                curr = curr.children[c - 'a'];
            }
            curr.wordEnd = true;
        }

        public boolean search(String word) {
            TrieNode node = searchPrefixNode(word);
            return node != null && node.wordEnd;
        }

        public boolean startsWith(String prefix) {
            return searchPrefixNode(prefix) != null;
        }

        private TrieNode searchPrefixNode(String prefix) {
            TrieNode curr = root;
            char c;
            for (int i = 0; i < prefix.length(); i++) {
                c = prefix.charAt(i);
                if ((curr = curr.children[c - 'a']) == null) {
                    return null;
                }
            }
            return curr;
        }
    }

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean wordEnd = false;
    }


    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        assert Objects.equals(trie.search("apple"), true);
        assert Objects.equals(trie.search("app"), false);
        assert Objects.equals(trie.startsWith("app"), true);
        trie.insert("app");
        assert Objects.equals(trie.search("app"), true);
    }
}
