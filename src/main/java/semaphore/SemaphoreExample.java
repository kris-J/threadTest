package semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/7 9:26
 */
public class SemaphoreExample {

    public static void main(String[] args) {
        //构造参数为同时获取锁的线程个数(许可证数)
        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " is running");
                    try {
                        semaphore.acquire();//传递int参数，可以一次性请求n个许可
                        System.out.println(Thread.currentThread().getName() + " get the lock");
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();//传递int参数，释放n个许可，与acquire相对应
                    }
                    System.out.println(Thread.currentThread().getName() + " release the lock");
                }
            }.start();
        }
        //可用许可数
        System.out.println(semaphore.availablePermits());
        //等待中的线程数
        System.out.println(semaphore.getQueueLength());
        //semaphore.acquireUninterruptibly();//等待的线程不允许被打断
        //semaphore.drainPermits();//获取所有剩余的许可数并返回，返回值用于release方法
        //semaphore.tryAcquire();//尝试获取许可，获取不到则直接放回
    }
}
