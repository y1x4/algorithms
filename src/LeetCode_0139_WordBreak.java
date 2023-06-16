import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LeetCode_0139_WordBreak {

    /**
     * 1 <= s.length <= 300
     * 1 <= wordDict.length <= 1000
     * 1 <= wordDict[i].length <= 20
     * s and wordDict[i] consist of only lowercase English letters.
     * All the strings of wordDict are unique.
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        TrieNode root = new TrieNode();
        TrieNode curr;
        int index;
        for (String word : wordDict) {
            curr = root;
            for (int i = 0; i < word.length(); i++) {
                index = word.charAt(i) - 'a';
                if (curr.children[index] == null) {
                    curr.children[index] = new TrieNode();
                }
                curr = curr.children[index];
            }
            curr.wordEnd = true;
        }

        // cache
        boolean[] visited = new boolean[s.length()];

        return wordBreak(s, 0, root, visited);
    }


    private boolean wordBreak(String s, int i, TrieNode root, boolean[] visited) {
        if (i == s.length()) {
            return true;
        }

        if (visited[i]) {
            return false;
        }

        visited[i] = true;

        TrieNode curr = root;
        int index;
        for (int j = i; j < s.length(); j++) {
            index = s.charAt(j) - 'a';
            if ((curr = curr.children[index]) == null) {
                return false;
            }
            if (curr.wordEnd && wordBreak(s, j + 1, root, visited)) {
                return true;
            }
        }
        return false;
    }


    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean wordEnd = false;
    }


    public static void main(String[] args) {
        LeetCode_0139_WordBreak obj = new LeetCode_0139_WordBreak();
        obj.wordBreak("leetcode", Arrays.asList(new String[]{"code", "leet"}));
        assert Objects.equals(obj.wordBreak("leetcode", Arrays.asList(new String[]{"code", "leet"})), true);
        assert Objects.equals(obj.wordBreak("catsandog", Arrays.asList(new String[]{"cats","dog","sand","and","cat"})), false);
    }
}
