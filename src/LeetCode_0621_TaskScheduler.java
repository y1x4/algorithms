import java.util.Arrays;

public class LeetCode_0621_TaskScheduler {

    /**
     * 1 <= task.length <= 10^4
     * tasks[i] is upper-case English letter.
     * The integer n is in the range [0, 100].
     *
     * Time:  O()
     * Space: O()
     */
    public int leastInterval(char[] tasks, int n) {
        int[] freqMap = new int[26];
        for (char c : tasks) {
            freqMap[c - 'A']++;
        }

        // max frequency of char
        int maxFreq = Arrays.stream(freqMap).max().getAsInt();

        // num of chars that freq equals maxFreq
        int num = (int) Arrays.stream(freqMap).filter(freq -> freq == maxFreq).count();

        return Math.max(tasks.length, (maxFreq - 1) * (n + 1) + num);
    }

    public static void main(String[] args) {
        LeetCode_0621_TaskScheduler obj = new LeetCode_0621_TaskScheduler();
    }
}
