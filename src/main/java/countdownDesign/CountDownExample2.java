package countdownDesign;

import java.util.concurrent.CountDownLatch;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/6 10:34
 */
public class CountDownExample2 {

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " step one");
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " step two");
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " step one");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }
        }.start();

    }
}
