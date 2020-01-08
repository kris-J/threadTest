package lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/7 16:41
 */
public class ReadWriteLockExample {

    private static final ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();

    private static final Lock readLock = reentrantLock.readLock();

    private static final Lock writeLock = reentrantLock.writeLock();

    private static final List<Long> data = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread() {
            @Override
            public void run() {
                write();
            }
        }.start();
        TimeUnit.SECONDS.sleep(1);
        new Thread() {
            @Override
            public void run() {
                read();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                read();
            }
        }.start();

    }

    public static void write() {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " write data");
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }


    public static void read() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " read data");
            data.forEach(System.out::println);
        } finally {
            readLock.unlock();
        }
    }
}
