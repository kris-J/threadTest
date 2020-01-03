package atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/3 11:12
 */
public class AtomicStampedReferenceTest {

    //两个参数，第一个为初始值，第二个为stamped值(相当于version)
    private static AtomicStampedReference atomicStm = new AtomicStampedReference(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    //模拟ABA问题
                    //四个参数，1.期望值，2.修改的值，3.期望的stamp，4.修改的stamp值
                    boolean b = atomicStm.compareAndSet(100, 101, atomicStm.getStamp(), atomicStm.getStamp() + 1);
                    System.out.println(b);
                    //修改为原值
                    atomicStm.compareAndSet(101, 100, atomicStm.getStamp(), atomicStm.getStamp() + 1);
                    System.out.println(b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    int stamp = atomicStm.getStamp();
                    System.out.println("before sleep:stamp=" + stamp);
                    Thread.sleep(3000);
                    boolean b = atomicStm.compareAndSet(100, 101, stamp, stamp + 1);
                    System.out.println(b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }
}
