package synchronizedLock;

/**
 * @author fangjie
 * @Description: 同步代码块
 * @date 2019/11/15 10:16
 */
public class SynchroizedTest {

    private final static Object MONITOR = new Object();

    public static void main(String[] args) {
        new Thread("t1"){
            @Override
            public void run() {
                synchronized (MONITOR){
                    try {
                        Thread.sleep(300_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                synchronized (MONITOR){
                    try {
                        Thread.sleep(300_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        new Thread("t3"){
            @Override
            public void run() {
                synchronized (MONITOR){
                    try {
                        Thread.sleep(300_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
