package threadGroup;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/25 11:33
 */
public class ThreadGroupTest {

    public static void main(String[] args) {
        //未定义，当前为main的线程组
//        System.out.println("当前线程名称：" + Thread.currentThread().getName());
//        System.out.println("当前线程组名称：" + Thread.currentThread().getThreadGroup().getName());
        ThreadGroup tg = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg, "t1") {
            @Override
            public void run() {
                try {
                    System.out.println("当前线程组名称：" + getThreadGroup().getName());
                    System.out.println("父线程名称：" +getThreadGroup().getParent().getName());
                    Thread.sleep(5_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        ThreadGroup tg2 = new ThreadGroup("TG2");
        Thread t2 = new Thread(tg2, "t2") {
            @Override
            public void run() {
                System.out.println(t1.getName());
            }
        };
        t2.start();
    }
}
