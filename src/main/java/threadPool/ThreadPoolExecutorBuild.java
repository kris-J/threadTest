package threadPool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/13 10:49
 */
public class ThreadPoolExecutorBuild {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = buildPool();
        //提交第一个线程
        pool.execute(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " running");
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //提交第二个线程，则active线程有一个，阻塞队列中有一个
        pool.execute(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " running");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //提交第三个线程，则active线程有一个，阻塞队列中有一个,此线程提交后，则会扩充运行线程到maximumPoolSize
        pool.execute(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " running");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        //提交第四个线程，当前active线程有两个，阻塞队列中有一个，已达到线程池的最大边界，则会执行策略，当前为拒绝
//        pool.execute(() -> {
//            try {
//                System.out.println(Thread.currentThread().getName() + " running");
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        //非阻塞式关闭，等所有线程工作完成后进行关闭
        pool.shutdown();
        //阻塞式关闭，最多等待时长后关闭
        try {
            pool.awaitTermination(1,TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //阻塞时关闭，将workQueue阻塞队列中的任务返回，运行中的线程会收到interrupt信号
        List<Runnable> runnableList = pool.shutdownNow();
        System.out.println(runnableList);

        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != pool.getActiveCount() || queueSize != pool.getQueue().size()) {
                System.out.println("active count:" + pool.getActiveCount());
                System.out.println("core pool size:" + pool.getCorePoolSize());
                System.out.println("blocking queue size:" + pool.getQueue().size());
                System.out.println("maximum pool size:" + pool.getMaximumPoolSize());
                activeCount = pool.getActiveCount();
                queueSize = pool.getQueue().size();
                System.out.println("==================================");
            }
        }

    }

    /**
     * corePoolSize:核心线程数大小
     * maximumPoolSize:最大拥有线程数大小
     * keepAliveTime:空闲线程存活的时间
     * unit:空闲线程存活时间单位
     * workQueue:缓存任务的阻塞队列
     * threadFactory:创建线程的工厂
     * handle:线程已达到maximumPoolSize并且workQueue队列已满，采取的策略
     */
    public static ThreadPoolExecutor buildPool() {
        //阻塞队列
        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1);
        //thread factory,自定义
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        //使用guava提供的创建factory构造方法
        ThreadFactory threadFactory1 = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

        //拒绝策略
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS, blockingQueue, threadFactory1, abortPolicy);

        System.out.println("the thread pool create done");

        return pool;
    }

}
