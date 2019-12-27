package threadCommunication;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/9/12 16:58
 */
public class ProduceConsumerMultiThread {

    private Integer i = 0;
    //同步锁
    private final static Object LOCK = new Object();
    //生产消费标识
    private volatile Boolean isProdueced = false;

    /**
     * 生产数据
     */
    public void produce() {
        synchronized (LOCK) {
            //此处不能使用if，notifyAll会唤醒所有在等待的线程，当第二个在唤醒后可能会继续向下执行i++，从而导致生产多个而无消费
            while (isProdueced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println(Thread.currentThread().getName() + "->" + i);
            //释放锁队列中的所有等待线程
            LOCK.notifyAll();
            isProdueced = true;

        }
    }

    /**
     * 消费数据
     */
    public void consumer() {
        synchronized (LOCK) {
            while (!isProdueced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "->" + i);
            LOCK.notifyAll();
            isProdueced = false;
        }
    }

    public static void main(String[] args) {
        ProduceConsumerMultiThread pc = new ProduceConsumerMultiThread();
        new Thread("p1") {
            @Override
            public void run() {
                while (true) {
                    pc.produce();
                }
            }
        }.start();

        new Thread("p2") {
            @Override
            public void run() {
                while (true) {
                    pc.produce();
                }
            }
        }.start();

        new Thread("p3") {
            @Override
            public void run() {
                while (true) {
                    pc.produce();
                }
            }
        }.start();

        new Thread("c1") {
            @Override
            public void run() {
                while (true) {
                    pc.consumer();
                }
            }
        }.start();
        new Thread("c2") {
            @Override
            public void run() {
                while (true) {
                    pc.consumer();
                }
            }
        }.start();
        new Thread("c3") {
            @Override
            public void run() {
                while (true) {
                    pc.consumer();
                }
            }
        }.start();

    }
}
