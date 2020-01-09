package forkJoin;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author fangjie
 * @Description: 分而治之, 无返回值
 * @date 2020/1/9 14:08
 */
public class ForkJoinRecursiveAction {

    private final static int MAX_THRESHOLD = 3;

    private final static AtomicInteger SUM = new AtomicInteger();

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new CalculatedRecursiveAction(1, 1000));
        try {
            forkJoinPool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(SUM.get());
    }

    static class CalculatedRecursiveAction extends RecursiveAction {

        private int start;
        private int end;

        public CalculatedRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if ((end - start) <= MAX_THRESHOLD) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (start + end) / 2;
                CalculatedRecursiveAction leftTask = new CalculatedRecursiveAction(start, middle);
                CalculatedRecursiveAction rightTask = new CalculatedRecursiveAction(middle + 1, end);
                leftTask.fork();
                rightTask.fork();
            }
        }
    }
}
