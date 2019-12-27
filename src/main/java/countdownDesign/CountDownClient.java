package countdownDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/11 15:14
 */
public class CountDownClient {

    public static void main(String[] args) {
        CountDownSelf countDown = new CountDownSelf(5);
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
                    countDown.down();
                }
            }.start();
        }
        //等待所有完成
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=======================");
        System.out.println("all thread has done");
    }
}
