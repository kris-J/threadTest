package synchronizedLock;

/**
 * @author fangjie
 * @Description: 同步方法
 * @date 2019/11/15 10:16
 */
public class SynchroizedTest1 {

    public static void main(String[] args) {
        SynchroizedRunnable synchroizedRunnable = new SynchroizedRunnable();
        Thread t1 = new Thread(synchroizedRunnable, "t1");
        Thread t2 = new Thread(synchroizedRunnable, "t2");
        Thread t3 = new Thread(synchroizedRunnable, "t3");
        t1.start();
        t2.start();
        t3.start();

    }

}

class SynchroizedRunnable implements Runnable {

    public static Integer totalWorkNum = 1;

    @Override
    public void run() {
        execute();
    }

    private synchronized void execute() {
        try {
            String name = Thread.currentThread().getName();
            System.out.println(name);
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前任务数量："+(totalWorkNum++));
    }
}
