package volatileVariable;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/2 14:15
 */
public class VolatileTest {

    private volatile static int INIT_VALUE = 0;

    private final static int MAX_VALUE = 5;

    public static void main(String[] args) {

        new Thread("READER") {
            @Override
            public void run() {
                int localValue = INIT_VALUE;
                while (localValue < MAX_VALUE) {
                    if (localValue != INIT_VALUE) {
                        System.out.println("The value updated to " + INIT_VALUE);
                        localValue = INIT_VALUE;
                    }
                }
            }
        }.start();


        new Thread("UPDATER") {
            @Override
            public void run() {
                int localValue = INIT_VALUE;
                while (localValue < MAX_VALUE) {
                    System.out.println("update value to " + (++localValue));
                    INIT_VALUE = localValue;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
