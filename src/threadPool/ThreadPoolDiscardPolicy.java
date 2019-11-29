package threadPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/25 14:55
 */
public class ThreadPoolDiscardPolicy {

    private final int SIZE;

    private final static int DEFAULT_SIZE = 10;

    private static int SEQ = 0;

    private final static String THREAD_PREFIX = "SIMPLE_THREADPOOL_";

    private final static ThreadGroup GROUP = new ThreadGroup("pool_group");

    /**
     * 执行任务队列大小
     */
    private final static int DEFAULT_TASK_QUEUE_SIZE = 1000;

    private final int TASK_QUEUE_SIZE;

    /**
     * 执行任务队列
     */
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    /**
     * 线程队列,用于填充线程池，初始化后共size个WorkTask实例
     */
    private final static List<WorkTask> THREAD_QUEUE = new ArrayList<>();

    /**
     * 默认的决绝策略
     */
    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = new DiscardPolicy() {
        @Override
        public void discard() throws DiscardPolicyException {
            throw new DiscardPolicyException("拒绝策略：队列已满，决绝提交");
        }
    };

    /**
     * 拒绝策略
     */
    private final DiscardPolicy discardPolicy;

    /**
     * 当前线程池的状态
     */
    private volatile boolean destroy = false;

    public ThreadPoolDiscardPolicy() {

        this(DEFAULT_SIZE, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public ThreadPoolDiscardPolicy(int size, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.SIZE = size;
        this.TASK_QUEUE_SIZE = taskQueueSize;
        this.discardPolicy = discardPolicy;
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
        if (destroy) {
            throw new IllegalStateException("the thread pool is destroy and can not submit task.");
        }
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > TASK_QUEUE_SIZE) {
                this.discardPolicy.discard();
            }
            TASK_QUEUE.addFirst(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    public void shutdown() throws InterruptedException {
        //队列中是否还有任务待执行
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(500);
        }
        //任务队列中已没有任务，需要判断当前正在运行的任务是否完成
        int threadQueueSize = THREAD_QUEUE.size();
        while (threadQueueSize > 0) {
            for (WorkTask task : THREAD_QUEUE) {
                if (task.getTaskState() == TaskState.BLOCK) {
                    //打断当前任务，任务收到打断信号后会跳出while循环(详见：WorkTask类的run方法)
                    task.interrupt();
                    //强制任务为DEAD状态
                    task.close();
                    threadQueueSize--;
                } else {
                    Thread.sleep(500);
                }
            }
        }
        this.destroy = true;
        System.out.println("the thread pool have shutdown");
    }

    public int getSIZE() {
        return SIZE;
    }

    public int getTASK_QUEUE_SIZE() {
        return TASK_QUEUE_SIZE;
    }

    public boolean isDestroy() {
        return this.destroy;
    }


    private enum TaskState {
        FREE, RUNNING, BLOCK, DEAD
    }

    /**
     * 拒绝策略异常
     */
    public static class DiscardPolicyException extends RuntimeException {
        public DiscardPolicyException(String message) {
            super(message);
        }
    }

    /**
     * 拒绝策略接口
     */
    public interface DiscardPolicy {
        void discard() throws DiscardPolicyException;
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


    public static void main(String[] args) throws InterruptedException {
        //同时有10的线程运行，队列中最大有20个任务，其他采用拒绝策略
        ThreadPoolDiscardPolicy threadPoolDiscardPolicy = new ThreadPoolDiscardPolicy(10, 20, ThreadPoolDiscardPolicy.DEFAULT_DISCARD_POLICY);
        for (int i = 0; i < 20; i++) {
            threadPoolDiscardPolicy.submit(new Thread() {
                @Override
                public void run() {
                    System.out.println("the thread" + Thread.currentThread().getName() + " is start");
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("the thread" + Thread.currentThread().getName() + " finished");
                }
            });
        }

        Thread.sleep(20_000);
        threadPoolDiscardPolicy.shutdown();
        threadPoolDiscardPolicy.submit(new Thread(){
            @Override
            public void run() {
                System.out.println("sfsdfdsfdfdsfdsf");
            }
        });

    }
}
