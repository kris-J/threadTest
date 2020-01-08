package condition;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/8 13:48
 */
public class ConditionExample2 {

    private final static Lock LOCK = new ReentrantLock();
    /**
     * 多个condition控制不同的等待队列
     * 如果使用一个condition，则生产、消费者线程都存在于一个等待队列中
     * 如果一个生产者线程进行signal唤醒并且此时DATA共享数据已达到上限
     * 可能会有另外一个生产者线程被唤醒，发现共现数据达到上限，从而又一次进入等待队列，而浪费了一次资源
     */
    private final static Condition PRODUCE_CONDITION = LOCK.newCondition();
    private final static Condition CONSUME_CONDITION = LOCK.newCondition();
    private static LinkedList<Long> DATA = new LinkedList();
    private static final int MAX_SIZE = 10;

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new Thread("P" + i) {
                @Override
                public void run() {
                    while (true) {
                        produce();
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread("C" + i) {
                @Override
                public void run() {
                    while (true) {
                        consume();
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }


    }

    private static void produce() {
        try {
            LOCK.lock();
            while (DATA.size() >= MAX_SIZE) {
                PRODUCE_CONDITION.await();
            }
            long value = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " produce data:" + value);
            DATA.addLast(value);
            CONSUME_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }

    private static void consume() {
        try {
            LOCK.lock();
            while (DATA.isEmpty()) {
                CONSUME_CONDITION.await();
            }
            Long value = DATA.removeLast();
            System.out.println(Thread.currentThread().getName() + " consume data:" + value);
            PRODUCE_CONDITION.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }
}
