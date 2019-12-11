package threadlocal;

/**
 * @author fangjie
 * @Description: 当前线程作为key
 * @date 2019/12/10 16:52
 */
public class ThreadLocalTest2 {

    private static ThreadLocal threadLocal = new ThreadLocal();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                threadLocal.set("thread-t1");
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                threadLocal.set("thread-t2");
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
    }
}
