package countdownDesign;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/3 17:25
 */
public class CountDownExample {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(2);

    private static final CountDownLatch countDownLatch = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < data.length; i++) {
            threadPool.execute(new SimpleRunnable(data, i, countDownLatch));
        }
        countDownLatch.await();
        System.out.println("all done");
        Arrays.stream(data).forEach(System.out::println);
        threadPool.shutdown();
    }

    static class SimpleRunnable implements Runnable {

        private final int[] data;
        private final int index;

        private final CountDownLatch latch;

        public SimpleRunnable(int[] data, int index, CountDownLatch latch) {
            this.data = data;
            this.index = index;
            this.latch = latch;
        }

        @Override
        public void run() {
            if (index % 2 == 0) {
                data[index] = data[index] * 2;
            } else {
                data[index] = data[index] * 3;
            }
            System.out.println(Thread.currentThread().getName() + " finished");
            latch.countDown();
        }
    }
}
