package lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * @author fangjie
 * @Description: jdk1.8提供，是ReadWriteLock的升级版本
 * 1.共10个线程，其中9个读、1个写，则写的线程获取到资源的机会较小而产生饥饿
 * 2.ReadWriteLock在获取线程时，采取的是悲观锁，StampedLock提供了乐观锁
 *
 * 效率上来说，一般用于读写不均衡的情况下，使用乐观锁tryOptimisticRead为最优
 *
 * @date 2020/1/8 15:03
 */
public class StampedLockExample {

    private final static StampedLock lock = new StampedLock();

    private final static List<Long> data = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (; ; ) {
                        read();
                    }
                }
            }.start();
        }

        new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    write();
                }
            }
        }.start();
    }

    /**
     * 悲观读锁
     */
    public static void read() {
        long stamp = -1;
        try {
            stamp = lock.readLock();
            System.out.println(data.stream().map(String::valueOf).collect(Collectors.joining("#")));
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockRead(stamp);
        }
    }

    /**
     * 乐观读锁
     */
    public static void read2() {
        long stamp = lock.tryOptimisticRead();
        if(!lock.validate(stamp)){
            try {
                stamp = lock.readLock();
                System.out.println(data.stream().map(String::valueOf).collect(Collectors.joining("#")));
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }

    /**
     * 悲观写锁
     */
    public static void write() {
        long stamp = -1;
        try {
            stamp = lock.writeLock();
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamp);
        }
    }

}
