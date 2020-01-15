package threadPool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fangjie
 * @Description: 拒绝策略
 * @date 2020/1/13 15:43
 */
public class ThreadPoolPolicy {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1),
                new ThreadFactoryBuilder().setNameFormat("pool-thread-%d").build(),
                discardPolicy());
        for (int i = 0; i < 5; i++) {
            pool.execute(new Task(i));
        }
        pool.shutdown();
    }

    /**
     * 抛出异常RejectedExecutionException
     *
     * @return
     */
    private static ThreadPoolExecutor.AbortPolicy abordPolicy() {
        return new ThreadPoolExecutor.AbortPolicy();
    }

    /**
     * 由当前运行线程池的线程运行
     *      main task-2 is running
     *      pool-thread-0 task-0 is running
     *      main task-3 is running
     *      pool-thread-0 task-1 is running
     *      pool-thread-0 task-4 is running
     *
     * @return
     */
    private static ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy() {
        return new ThreadPoolExecutor.CallerRunsPolicy();
    }

    /**
     * 抛弃阻塞队列中最前面的任务
     *      pool-thread-0 task-0 is running
     *      pool-thread-0 task-4 is running
     * @return
     */
    private static ThreadPoolExecutor.DiscardOldestPolicy discardOldestPolicy() {
        return new ThreadPoolExecutor.DiscardOldestPolicy();
    }

    /**
     * 直接抛弃
     *      pool-thread-0 task-0 is running
     *      pool-thread-0 task-1 is running
     * @return
     */
    private static ThreadPoolExecutor.DiscardPolicy discardPolicy() {
        return new ThreadPoolExecutor.DiscardPolicy();
    }

    static class Task implements Runnable {

        private final int no;

        Task(int no) {
            this.no = no;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " task-" + no + " is running");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
