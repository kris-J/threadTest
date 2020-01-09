package forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @author fangjie
 * @Description: 分而治之,带有返回值
 * @date 2020/1/9 14:08
 */
public class ForkJoinRecursiveTask {

    private final static int MAX_THRESHOLD = 3;

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> task = forkJoinPool.submit(new CalculatedRecursiveTask(1, 1000));
        try {
            Integer result = task.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class CalculatedRecursiveTask extends RecursiveTask<Integer> {

        private int start;
        private int end;

        public CalculatedRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if ((end - start) <= MAX_THRESHOLD) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                int middle = (start + end) / 2;
                CalculatedRecursiveTask leftTask = new CalculatedRecursiveTask(start, middle);
                CalculatedRecursiveTask rightTask = new CalculatedRecursiveTask(middle + 1, end);
                leftTask.fork();
                rightTask.fork();
                return leftTask.join() + rightTask.join();
            }
        }
    }
}
