package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/13 15:30
 */
public class ExecutorsBuild {

    public static void main(String[] args) {

    }

    /**
     * new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()))
     *
     * 一个单线程的线程池，相当于串行执行任务，如果异常则会创建新的线程替代
     */
    private static void singleThreadExecutor(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>())
     *
     * 固定coreSize和maxmimSize，多余的加入LinkedBlockingQueue边界为Integer.MAX_VALUE大小的队列中
     */
    private static void fixedThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
    }

    /**
     * new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>())
     *
     * 适用于小任务，因为使用SynchronousQueue，任务队列只能暂存一个
     * 所以大量任务提交后会不断扩大线程到Integer.MAX_VALUE
     *
     * 如果空闲线程到60秒进行回收，直到线程池自动关闭
     *
     */
    private static void cacheThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
    }

    /**
     * JDK1.8新增
     * new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true)
     *
     * fork join的模式
     *
     * 创建最大CPU个数的线程池
     */
    private static void workStealing(){
        ExecutorService executorService = Executors.newWorkStealingPool();
    }
}
