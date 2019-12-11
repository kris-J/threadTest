package countdownDesign;

import java.util.concurrent.CountDownLatch;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/11 14:54
 */
public class JDKCountDown {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread("t" + i) {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " start work");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //count值减一
                    countDownLatch.countDown();
                }
            }.start();
        }
        //等待所有完成
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=======================");
        System.out.println("all thread has done");
    }
}
