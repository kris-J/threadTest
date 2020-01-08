package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/7 10:51
 */
public class ReentrantLockExample {

    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {

    }

    public static void neekLock() {
        try {
            lock.lock();
            //lock.lockInterruptibly();//可被中断
            //lock.tryLock();//尝试获取锁，获取不到直接返回boolean
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void neekLockBySync() {
        synchronized (ReentrantLockExample.class) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
