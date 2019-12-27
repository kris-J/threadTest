package synchronizedLock;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/15 15:40
 */
public class SynchroizedThis {

    public static void main(String[] args) {
        This t = new This();
        new Thread("t1") {
            @Override
            public void run() {
                t.m1();
            }
        }.start();

        new Thread("t2") {
            @Override
            public void run() {
                t.m2();
            }
        }.start();

        new Thread("t3") {
            @Override
            public void run() {
                t.m3();
            }
        }.start();
    }
}

class This {
    //方法锁标识，相当于给this加上锁，与其他相同this的方法有竞争锁的关系
    public synchronized void m1() {
        try {
            System.out.println("m1 " + Thread.currentThread().getName());
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //m2不加锁，所以可以直接调用
    public void m2() {
        try {
            System.out.println("m1 " + Thread.currentThread().getName());
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //方法锁标识，相当于给this加上锁，与其他相同this的方法有竞争锁的关系
    public synchronized void m3() {
        try {
            System.out.println("m1 " + Thread.currentThread().getName());
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
