package synchronizedLock;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/15 15:47
 */
public class SynchroizedStatic {

    public static void main(String[] args) {
        new Thread("t1") {
            @Override
            public void run() {
                Static.m1();
            }
        }.start();

        new Thread("t2") {
            @Override
            public void run() {
                Static.m2();
            }
        }.start();

        new Thread("t3") {
            @Override
            public void run() {
                Static.m3();
            }
        }.start();
    }
}

class Static{
    //静态代码块，里面加类的锁，高于其锁
    static {
        synchronized (Static.class){
            try {
                System.out.println("static " + Thread.currentThread().getName());
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //静态方法锁标识，相当于给class加上锁，与其他相同class的方法有竞争锁的关系
    public synchronized static void m1(){
        try {
            System.out.println("m1 " + Thread.currentThread().getName());
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //m2不加锁，在static静态块之后，所以可以直接调用
    public static void m2(){
        try {
            System.out.println("m2 " + Thread.currentThread().getName());
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //静态方法锁标识，相当于给class加上锁，与其他相同class的方法有竞争锁的关系
    public synchronized static void m3(){
        try {
            System.out.println("m3 " + Thread.currentThread().getName());
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
