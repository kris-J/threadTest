package threadPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/25 14:55
 */
public class SimpleThreadPool {

    private final int SIZE;

    private final static int DEFAULT_SIZE = 10;

    private static int SEQ = 0;

    private final static String THREAD_PREFIX = "SIMPLE_THREADPOOL_";

    private final static ThreadGroup GROUP = new ThreadGroup("pool_group");

    /**
     * 执行任务队列
     */
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    /**
     * 线程队列,用于填充线程池，初始化后共size个WorkTask实例
     */
    private final static List<WorkTask> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPool() {
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPool(int size) {
        this.SIZE = size;
        init();
    }

    private void init() {
        for (int i = 0; i < this.SIZE; i++) {
            createWorkTask();
        }
    }

    private void createWorkTask() {
        WorkTask workTask = new WorkTask(GROUP, THREAD_PREFIX + (SEQ++));
        workTask.start();
        THREAD_QUEUE.add(workTask);
    }

    public void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
            TASK_QUEUE.addFirst(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private enum TaskState {
        FREE, RUNNING, BLOCK, DEAD
    }

    private static class WorkTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public TaskState getTaskState() {
            return this.taskState;
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }

        public WorkTask(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            this.taskState = TaskState.BLOCK;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break OUTER;
                        }
                    }

                    runnable = TASK_QUEUE.removeFirst();
                }
                if (runnable != null) {
                    this.taskState = TaskState.RUNNING;
                    runnable.run();
                    this.taskState = TaskState.FREE;
                }
            }
        }
    }


    public static void main(String[] args) {
        SimpleThreadPool simpleThreadPool = new SimpleThreadPool();
        for (int i = 0; i < 20; i++) {
            simpleThreadPool.submit(new Thread(){
                @Override
                public void run() {
                    System.out.println("the thread" + Thread.currentThread().getName() + " is start");
                    try {
                        Thread.sleep(30_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("the thread" + Thread.currentThread().getName() + " finished");
                }
            });
        }
    }
}
