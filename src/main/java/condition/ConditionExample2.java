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
