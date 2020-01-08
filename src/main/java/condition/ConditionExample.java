package condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/8 10:08
 */
public class ConditionExample {

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition condition = lock.newCondition();

    private static int data = 0;

    private static volatile boolean hasProduct = false;

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                while (true){
                    productData();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                while (true){
                    consumerData();
                }
            }
        }.start();
    }

    public static void productData() {
        try {
            lock.lock();
            while (hasProduct) {
                condition.await();
            }
            data++;
            hasProduct = true;
            System.out.println("produce a data:" + data);
            TimeUnit.SECONDS.sleep(1);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void consumerData() {
        try {
            lock.lock();
            while (!hasProduct) {
                condition.await();
            }
            data--;
            hasProduct = false;
            System.out.println("consume a data:" + data);
            TimeUnit.SECONDS.sleep(1);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
