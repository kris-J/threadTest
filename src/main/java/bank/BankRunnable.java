package bank;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/9/12 15:03
 */
public class BankRunnable implements Runnable {

    private Integer numberIndex = 1;
    private static final Integer MAX_NUMBER = 50;
    private static final Object MONITOR = new Object();

    @Override
    public void run() {
        while (true) {
            synchronized (MONITOR) {
                if (numberIndex > MAX_NUMBER) {
                    break;
                }
                System.out.println("请" + (numberIndex++) + "号到" + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
