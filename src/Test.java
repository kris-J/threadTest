/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/6 17:34
 */
public class Test implements Runnable {

    @Override
    public void run() {
        synchronized (LOCK) {
            while (!condition){
                System.out.println(Thread.currentThread().getName() + " is wait");
                try {
                    LOCK.wait();
                    System.out.println(Thread.currentThread().getName() + "sdfsdfdsfd");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " notify to run");
            condition = true;
            LOCK.notifyAll();
        }
    }

    private static final Object LOCK = new Object();
    private static boolean condition = false;

    public static void main(String[] args) {
        Test t = new Test();
        Thread thread = new Thread(t, "t1");
        thread.start();
        new Thread("xxx"){
            @Override
            public void run() {
                synchronized (LOCK){
                    try {
                        Thread.sleep(5_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("go to run");
                    condition = true;
                    LOCK.notifyAll();
                }
            }
        }.start();
    }
}
