import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class HorseRace {
    private static final int NUM_HORSES = 5;
    private static final CyclicBarrier barrier = new CyclicBarrier(NUM_HORSES + 1);
    private static final ConcurrentHashMap<String, Long> results = new ConcurrentHashMap<>(NUM_HORSES);

    public static void main(String[] args) {
        Horse[] horses = new Horse[NUM_HORSES];

        // 创建赛马线程
        IntStream.range(0, NUM_HORSES).forEach(i -> {
            horses[i] = new Horse("Horse " + (i + 1), barrier);
            new Thread(horses[i]).start();
        });

        // 主线程作为额外的参与者加入CyclicBarrier
        try {
            barrier.await(); // 等待所有马准备就绪
            System.out.println("All horses are ready. Race starts now!");

            barrier.await(); // 等待所有马完成比赛
            System.out.println("All horses have finished the race!");
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }

        // 公布成绩
        System.out.println("Race results:");
        IntStream.range(0, NUM_HORSES).forEach(i -> {
            String name = horses[i].getName();
            System.out.printf("  %s finished with %d ms.%n", name, results.get(name));
        });
    }

    static class Horse implements Runnable {
        private final String name;
        private final CyclicBarrier barrier;

        public Horse(String name, CyclicBarrier barrier) {
            this.name = name;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                // 马准备就绪
                System.out.println(name + " is ready.");
                barrier.await(); // 等待所有马准备就绪

                // 模拟赛马过程
                long ms = (long) (Math.random() * 3000);
                TimeUnit.MILLISECONDS.sleep(ms);

                // 到达终点
                System.out.println(name + " has reached the finish line.");
                results.put(name, ms);
                barrier.await(); // 等待所有马完成比赛
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }
    }
}
