import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {


    // There is a broken calculator that has the integer startValue on its display initially. In one operation, you can:
    //
    //* multiply the number on display by 2, or
    //* subtract 1 from the number on display.
    //
    // Given two integers `startValue` and `target`, return the minimum number of operations needed to display `target` on the calculator.
    // 都是正数
    public static int minSteps(int startValue, int target) {
        if (startValue <= 0 || target <= 0) {
            throw new IllegalArgumentException("startValue and target must be positive");
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startValue);

        int step = 0;
        while (!queue.isEmpty()) {
            for (int i = queue.size(); i > 0; i--) {
                int value = queue.remove();
                if (value == target) {
                    return step;
                }
                if (value > 0) {
                    queue.add(value * 2);
                }
                if (value - 1 > 0) {
                    queue.add(value - 1);
                }
            }
            step++;
        }
        return -1;
    }


    public static void main(String[] args) {
        System.out.println(minSteps(-1, 1));
        System.out.println(minSteps(0, 1));
        System.out.println(minSteps(3, 2));
        System.out.println(minSteps(3, 4));
        System.out.println(minSteps(3, 5));
        System.out.println(minSteps(5, 8));
    }
}


/*
  2023-12-29 22:14:07
  自我介绍，介绍2个项目，账户和创意模块做了什么，设计模式怎么用的，策略模式几个类的名称，依赖注入模式，数据库范式，除MySQL外知道哪些数据库，
  Redis怎么用的，统一预算服务为什么用Redis，MQ知道哪些、有什么特点，和京东自研的比较，RPC概念和实现，系统可观测性
 */