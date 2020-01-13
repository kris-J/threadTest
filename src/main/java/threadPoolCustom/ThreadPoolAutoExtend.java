package threadPoolCustom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/25 14:55
 */
public class ThreadPoolAutoExtend extends Thread {

    /**
     * 当前THREAD_QUEUE队列大小
     */
    private int SIZE;
    /**
     * 运行线程名称序列
     */
    private static int SEQ = 0;
    /**
     * 运行线程名称前缀
     */
    private final static String THREAD_PREFIX = "SIMPLE_THREADPOOL_";

    /**
     * 线程组
     */
    private final static ThreadGroup GROUP = new ThreadGroup("pool_group");

    /**
     * 执行任务队列大小
     */
    private final static int DEFAULT_TASK_QUEUE_SIZE = 1000;

    /**
     * 任务队列大小，用于拒绝策略
     */
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

    /**
     * 最小执行任务队列大小
     */
    private int min_size;
    /**
     * 中间执行任务队列大小
     */
    private int middle_size;
    /**
     * 最大执行任务队列大小
     */
    private int max_size;

    public ThreadPoolAutoExtend() {

        this(4, 8, 12, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public ThreadPoolAutoExtend(int minSize, int middleSize, int maxSize, int taskQueueSize, DiscardPolicy discardPolicy) {
        this.min_size = minSize;
        this.middle_size = middleSize;
        this.max_size = maxSize;
        this.TASK_QUEUE_SIZE = taskQueueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    /**
     * 初始化，创建min_size线程填充，并开始线程池自动填充
     */
    private void init() {
        for (int i = 0; i < this.min_size; i++) {
            createWorkTask();
        }
        this.SIZE = this.min_size;
        this.start();
    }

    /**
     * 线程池自动填充
     */
    @Override
    public void run() {
        while (!destroy) {
            System.out.printf("the thread pool#Min:%d,Middle:%d,Max:%d,Current:%d,QueueSize:%d\n",
                    this.min_size, this.middle_size, this.max_size, this.SIZE, TASK_QUEUE.size());
            try {
                Thread.sleep(5_000);
                //当前任务队列大小大于中间值，且当前运行的size小于中间值，进行扩充到middle_size
                if (TASK_QUEUE.size() > middle_size && SIZE < middle_size) {
                    for (int i = SIZE; i < middle_size; i++) {
                        createWorkTask();
                    }
                    System.out.println("the thread pool is incremented to middle size");
                    SIZE = middle_size;
                } else if (TASK_QUEUE.size() > max_size && SIZE < max_size) {
                    //当前任务队列大小大于最大值，且当前运行的size小于最大值，进行扩充到max_size
                    for (int i = SIZE; i < max_size; i++) {
                        createWorkTask();
                    }
                    System.out.println("the thread pool is incremented to max size");
                    SIZE = max_size;
                }
                //任务队列空，则进行THREAD_QUEUE的回收
                synchronized (THREAD_QUEUE) {
                    if (TASK_QUEUE.isEmpty() && SIZE > middle_size) {
                        System.out.println("the thread pool release");
                        int releaseSize = SIZE - middle_size;
                        for (Iterator<WorkTask> iterator = THREAD_QUEUE.iterator(); iterator.hasNext(); ) {
                            if (releaseSize <= 0) {
                                break;
                            }
                            WorkTask task = iterator.next();
                            task.close();
                            task.interrupt();
                            iterator.remove();
                            releaseSize--;
                        }
                        SIZE = middle_size;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建线程到THREAD_QUEUE,用于执行客户端提交的线程
     */
    private void createWorkTask() {
        WorkTask workTask = new WorkTask(GROUP, THREAD_PREFIX + (SEQ++));
        workTask.start();
        THREAD_QUEUE.add(workTask);
    }

    /**
     * 客户端提交任务，加入TASK_QUEUE，并唤醒进行线程运行
     * @param runnable
     */
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

    /**
     * 停止线程池
     * @throws InterruptedException
     */
    public void shutdown() throws InterruptedException {
        //队列中是否还有任务待执行
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(500);
        }
        //任务队列中已没有任务，需要判断当前正在运行的任务是否完成
        synchronized (THREAD_QUEUE) {
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
        }
        System.out.println("---------------" + GROUP.activeCount() + "-------------");
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

    /**
     * 任务task，用于执行客户端提交的线程
     */
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
//                            e.printStackTrace();
                            System.out.println("closed");
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
        ThreadPoolAutoExtend threadPoolAutoExtend = new ThreadPoolAutoExtend();
        for (int i = 0; i < 40; i++) {
            threadPoolAutoExtend.submit(new Thread() {
                @Override
                public void run() {
                    System.out.println("the thread" + Thread.currentThread().getName() + " is start");
                    try {
                        Thread.sleep(3_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("the thread" + Thread.currentThread().getName() + " finished");
                }
            });
        }

        Thread.sleep(5_000);
        threadPoolAutoExtend.shutdown();
//        threadPoolAutoExtend.submit(new Thread() {
//            @Override
//            public void run() {
//                System.out.println("sfsdfdsfdfdsfdsf");
//            }
//        });

    }
}
