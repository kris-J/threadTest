package cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/6 14:02
 */
public class CyclicBarrierExample {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("all cyclicBarrier finished.");
            }
        });
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName() + " finished.");
                    cyclicBarrier.await();
                    System.out.println("cyclicBarrier wait finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    System.out.println(Thread.currentThread().getName() + " finished.");
                    cyclicBarrier.await();
                    System.out.println("cyclicBarrier wait finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //main线程也加入到cyclicBarrier
        cyclicBarrier.await();
        System.out.println("main wait finished");
    }
}
