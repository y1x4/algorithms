import java.util.*;

public class Main2 {


    public static int[] nextGt(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int n = nums.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            boolean found = false;
            for (int j = 1; j < n; j++) {
                if (nums[(i + j) % n] > nums[i]) {
                    res[i] = nums[(i + j) % n];
                    found = true;
                    break;
                }
            }
            if (!found) {
                res[i] = -1;
            }
        }
        return res;
    }


    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int n = s.length(), num = 0;
        char preSign = '+', currChar;
        for (int i = 0; i < n; i++) {
            currChar = s.charAt(i);
            if (Character.isDigit(currChar)) {
                num = num * 10 + (currChar - '0');
            }
            if ((!Character.isDigit(currChar) && currChar != ' ') || i == n - 1) {
                switch (preSign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    case '/':
                        stack.push(stack.pop() / num);
                        break;
                    default:
                        break;
                }
                num = 0;
                preSign = currChar;
            }
        }

        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }

    public int[] dailyTemperatures(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();
        int n = temperatures.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                res[stack.peek()] = i - stack.peek();
                stack.pop();
            }
            stack.push(i);
        }
        return res;
    }

    static class StockSpanner {

        private Stack<int[]> stack;
        private int idx;

        public StockSpanner() {
            stack = new Stack<>();
            idx = 0;
        }

        public int next(int price) {
            while (!stack.isEmpty() && stack.peek()[1] <= price) {
                stack.pop();
            }
            int res = stack.isEmpty() ? idx + 1 : idx - stack.peek()[0];
            stack.push(new int[]{idx++, price});
            return res;
        }
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(k, n, 1, res, new ArrayList<>());
        return res;
    }

    private void backtrack(int k, int n, int start, List<List<Integer>> res, List<Integer> curr) {
        if (k == 0 && n == 0) {
            res.add(new ArrayList<>(curr));
        } else if (k > 0 && n > 0) {
            for (int i = start; i <= 9; i++) {
                if (n - i >= 0) {
                    curr.add(i);
                    backtrack(k - 1,n - i, i + 1, res, curr);
                    curr.remove(curr.size() - 1);
                }
            }
        }
    }


    public int[] canSeePersonsCount(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int n = heights.length;
        int[] res = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() < heights[i]) {
                res[i]++;
                stack.pop();
            }
            if (!stack.isEmpty()) {
                res[i]++;
            }
            stack.push(heights[i]);
        }
        return res;
    }


    public String predictPartyVictory(String senate) {
        int n = senate.length();
        boolean[] baned = new boolean[n];
        int sizeR = 0, sizeD = 0;
        for (int i = 0; i < n; i++) {
            if (senate.charAt(i) == 'R') {
                sizeR++;
            } else {
                sizeD++;
            }
        }

        while (true) {
            for (int i = 0; i < n; i++) {
                if (!baned[i]) {
                    if (sizeR == 0) {
                        return "Dire";
                    } else if (sizeD == 0) {
                        return "Radiant";
                    } else {
                        int k;
                        for (int j = 1; j < n; j++) {
                            k = (i + j) % n;
                            if (!baned[k] && senate.charAt(k) != senate.charAt(i)) {
                                baned[k] = true;
                                if (senate.charAt(i) == 'R') {
                                    sizeD--;
                                } else {
                                    sizeR--;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        if (n == 0) {
            return new int[][]{newInterval};
        }

        // leftIdx 左侧的区间都是小于newInterval[0]的
        // rightIdx 右侧的区间都是大于newInterval[0]的
        // [leftIdx, rightIdx]
        int leftIdx, rightIdx;

        int lo = 0, hi = n, mid;
        while (lo < hi) {
            mid = lo + ((hi - lo) >> 1);
            if (intervals[mid][1] >= newInterval[0]) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        leftIdx = lo;

        lo = 0;
        hi = n;
        while (lo < hi) {
            mid = lo + ((hi - lo) >> 1);
            if (intervals[mid][0] <= newInterval[1]) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        rightIdx = hi - 1;

        System.out.println(leftIdx + " " + rightIdx);

        if (leftIdx > rightIdx) {
            // leftIdx = rightIdx - 1，插入一个
            int[][] res = new int[n + 1][2];
            System.arraycopy(intervals, 0, res, 0, leftIdx);
            res[leftIdx] = newInterval;
            System.arraycopy(intervals, leftIdx, res, leftIdx + 1, n - leftIdx);
            return res;
        } else {
            // leftIdx <= rightIdx，合并期间
            int[][] res = new int[n - rightIdx + leftIdx][2];
            System.arraycopy(intervals, 0, res, 0, leftIdx);
            res[leftIdx] = new int[]{Math.min(newInterval[0], intervals[leftIdx][0]), Math.max(newInterval[1], intervals[rightIdx][1])};
            System.arraycopy(intervals, rightIdx + 1, res, leftIdx + 1, n - rightIdx - 1);
            return res;
        }
    }

    public String getHint(String secret, String guess) {
        int x = 0, all = 0;
        int[] cnt1 = new int[10], cnt2 = new int[10];
        int n = secret.length();
        for (int i = 0; i < n; i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                x++;
            }
            cnt1[secret.charAt(i) - '0']++;
            cnt2[guess.charAt(i) - '0']++;
        }
        for (int i = 0; i < 10; i++) {
            all += Math.min(cnt1[i], cnt2[i]);
        }
        return String.format("%dA%dB", x, all - x);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextGt(new int[]{1, 2, 1})));
        System.out.println(Arrays.toString(nextGt(new int[]{1, 2, 3, 4, 3})));
        System.out.println(Arrays.toString(nextGt(new int[]{7, 2, 1, 3})));
        System.out.println(Arrays.toString(nextGt(new int[]{4, 3, 2, 1})));
        System.out.println(Arrays.toString(nextGt(new int[]{1, 1, 1, 1})));

        Main2 test = new Main2();
        System.out.println(Arrays.toString(test.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73})));
        System.out.println(Arrays.toString(test.dailyTemperatures(new int[]{30,40,50,60})));
        System.out.println(Arrays.toString(test.dailyTemperatures(new int[]{30,60,90})));

        System.out.println(test.predictPartyVictory("RD"));
        System.out.println(test.predictPartyVictory("RDD"));
        System.out.println(test.predictPartyVictory("DDRRR"));  // Dire

        System.out.println(Arrays.deepToString(test.insert(new int[][]{{1, 3}, {5, 6}, {8, 9}}, new int[]{-1, 0})));
        System.out.println(Arrays.deepToString(test.insert(new int[][]{{1, 3}, {5, 6}, {8, 9}}, new int[]{10, 17})));
        System.out.println(Arrays.deepToString(test.insert(new int[][]{{1, 3}, {5, 6}, {8, 9}}, new int[]{4, 4})));
        System.out.println(Arrays.deepToString(test.insert(new int[][]{{1, 3}, {5, 6}, {8, 9}}, new int[]{4, 5})));
        System.out.println(Arrays.deepToString(test.insert(new int[][]{{1, 3}, {5, 6}, {8, 9}}, new int[]{4, 10})));
    }
}

