import java.util.*;

public class LeetCode_0127_WordLadder {

    /**
     * 单向BFS
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        int length = 1;
        while (!queue.isEmpty()) {
            length++;
            for (int i = queue.size(); i > 0; i--) {
                if (check(wordSet, queue, queue.remove(), endWord)) {
                    return length;
                }
            }
        }
        return 0;
    }

    private boolean check(Set<String> wordSet, Queue<String> queue, String currWord, String endWord) {
        char[] chars = currWord.toCharArray();
        char c;
        String newWord;
        for (int i = 0; i < chars.length; i++) {
            c = chars[i];
            for (char j = 'a'; j <= 'z'; j++) {
                if (j == c) {
                    continue;
                }
                chars[i] = j;
                newWord = new String(chars);
                if (Objects.equals(newWord, endWord)) {
                    return true;
                } else if (wordSet.contains(newWord)) {
                    queue.add(newWord);
                    wordSet.remove(newWord);
                }
            }
            chars[i] = c;
        }
        return false;
    }


    /**
     * 双向BFS
     */
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            return 0;
        }

        Set<String> forward = new HashSet<>();
        forward.add(beginWord);
        Set<String> backward = new HashSet<>();
        backward.add(endWord);

        int length = 1;
        while (!forward.isEmpty() && !backward.isEmpty()) {
            length++;

            // 保持forward中单词更少
            Set<String> temp;
            if (backward.size() < forward.size()) {
                temp = backward;
                backward = forward;
                forward = temp;
            }

            temp = new HashSet<>();
            for (String currWord : forward) {
                if (check2(wordSet, currWord, temp, backward)) {
                    return length;
                }
            }
            forward = temp;
        }
        return 0;
    }
    private boolean check2(Set<String> wordSet, String currWord, Set<String> nextLevelForward, Set<String> backward) {
        char[] chars = currWord.toCharArray();
        char c;
        String newWord;
        for (int i = 0; i < chars.length; i++) {
            c = chars[i];
            for (char j = 'a'; j <= 'z'; j++) {
                if (j == c) {
                    continue;
                }
                chars[i] = j;
                newWord = new String(chars);
                if (backward.contains(newWord)) {
                    return true;
                } else if (wordSet.contains(newWord)) {
                    nextLevelForward.add(newWord);
                    wordSet.remove(newWord);
                }
            }
            chars[i] = c;
        }
        return false;
    }

    public static void main(String[] args) {
        LeetCode_0127_WordLadder obj = new LeetCode_0127_WordLadder();
        System.out.println(obj.ladderLength2("hit", "cog", List.of("hot", "dot", "dog", "lot", "log", "cog")));  // 5
    }
}
