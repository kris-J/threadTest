package threadCommunication;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/9/12 16:58
 */
public class ProduceConsumer {

    private Integer i = 0;
    //同步锁
    private final static Object LOCK = new Object();
    //生产消费标识
    private Boolean isProdueced = false;

    /**
     * 生产数据
     */
    public void produce() {
        synchronized (LOCK) {
            if (isProdueced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                System.out.println("produce->" + i);
                LOCK.notify();
                isProdueced = true;
            }
        }
    }

    /**
     * 消费数据
     */
    public void consumer() {
        synchronized (LOCK){
            if (isProdueced) {
                System.out.println("consumer->" + i);
                LOCK.notify();
                isProdueced = false;
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProduceConsumer pc = new ProduceConsumer();
        new Thread("p") {
            @Override
            public void run() {
                while(true){
                    pc.produce();
                }
            }
        }.start();

        new Thread("c") {
            @Override
            public void run() {
                while(true){
                    pc.consumer();
                }
            }
        }.start();

    }
}
