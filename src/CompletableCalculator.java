import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class CompletableCalculator {
    // 使用 map 维护一个运算符优先级
    // 这里的优先级划分按照「数学」进行划分即可
    private static final Map<Character, Integer> OP_PRIORITY_MAP = new HashMap<>() {{
        put('-', 1);
        put('+', 1);
        put('*', 2);
        put('/', 2);
        put('%', 2);
        put('^', 3);
    }};

    public int calculate(String s) {
        // 将所有的空格去掉
        s = s.replaceAll(" ", "");

        char[] chars = s.toCharArray();
        int n = s.length();

        // 存放所有「非数字以外」的操作
        Deque<Character> opStack = new ArrayDeque<>();
        // 存放所有的数字
        Deque<Long> numStack = new ArrayDeque<>();
        // 为了防止第一个数为负数，先往 numStack 加个 0
        numStack.addLast(0L);

        for (int i = 0; i < n; i++) {
            char ch = chars[i];
            if (ch == '(') {
                opStack.addLast(ch);
            } else if (ch == ')') {
                // 计算到最近一个左括号为止
                while (!opStack.isEmpty()) {
                    if (opStack.peekLast() == '(') {
                        opStack.pollLast();
                        break;
                    }
                    this.calculate(numStack, opStack);
                }
            } else if (Character.isDigit(ch)) {
                long num = 0;
                int j = i;
                // 将从 i 位置开始后面的连续数字整体取出，加入 numStack
                while (j < n && Character.isDigit(chars[j])) {
                    num = num * 10 + (chars[j++] - '0');
                }
                numStack.addLast(num);
                i = j - 1;
            } else {
                if (i > 0 && (chars[i - 1] == '(' || chars[i - 1] == '+' || chars[i - 1] == '-')) {
                    numStack.addLast(0L);
                }
                // 有一个新操作要入栈时，先把栈内可以算的都算了
                // 只有满足「栈内运算符」比「当前运算符」优先级高/同等，才进行运算
                while (!opStack.isEmpty() && opStack.peekLast() != '(') {
                    char prev = opStack.peekLast();
                    if (OP_PRIORITY_MAP.get(prev) >= OP_PRIORITY_MAP.get(ch)) {
                        this.calculate(numStack, opStack);
                    } else {
                        break;
                    }
                }
                opStack.addLast(ch);
            }

        }
        // 将剩余的计算完
        while (!opStack.isEmpty()) {
            this.calculate(numStack, opStack);
        }
        return numStack.peekLast().intValue();
    }

    private void calculate(Deque<Long> nums, Deque<Character> ops) {
        if (nums.isEmpty() || nums.size() < 2 || ops.isEmpty()) {
            return;
        }
        long b = nums.pollLast(), a = nums.pollLast();
        char op = ops.pollLast();
        long ans = 0;
        if (op == '+') ans = a + b;
        else if (op == '-') ans = a - b;
        else if (op == '*') ans = a * b;
        else if (op == '/') ans = a / b;
        else if (op == '^') ans = (long) Math.pow(a, b);
        else if (op == '%') ans = a % b;
        nums.addLast(ans);
    }

    public static void main(String[] args) {
        CompletableCalculator calculator = new CompletableCalculator();
        System.out.println(calculator.calculate("(- 10+2*2^10+10)-96/2"));
    }
}
