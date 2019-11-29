package bank;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/9/12 15:04
 */
public class BankCallNumber {

    public static void main(String[] args) {
        final BankRunnable bankRunnable = new BankRunnable();
        Thread t1 = new Thread(bankRunnable, "window1");
        Thread t2 = new Thread(bankRunnable, "window2");
        Thread t3 = new Thread(bankRunnable, "window3");
        t1.start();
        t2.start();
        t3.start();
    }
}
