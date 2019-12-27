package interrupt;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/14 10:03
 */
public class Interrupt {

    public static void main(String[] args) throws InterruptedException {
//        interrupt1();
//        interrupt2();
//        interrupt3();
//        interrupt4();
        interrupt5();
    }

    /**
     * 根据打断标识
     */
    public static void interrupt1() {
        Thread t = new Thread("t-thread") {
            @Override
            public void run() {
                //正常活动状态下获取打断状态
                while (!Thread.interrupted()) {

                }
                System.out.println("收到打断信号");
            }
        };
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.isInterrupted());
        t.interrupt();
        System.out.println(t.isInterrupted());
    }

    /**
     * 捕捉sleep的打断异常
     */
    public static void interrupt2() {
        Thread t = new Thread("t-thread") {
            @Override
            public void run() {
                //处于阻塞状态下获取打断状态
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("收到打断信号");
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.isInterrupted());
        t.interrupt();
        System.out.println(t.isInterrupted());
    }

    private static final Object MONITOR = new Object();

    /**
     * 捕捉wait的打断异常
     */
    public static void interrupt3() {
        Thread t = new Thread("t-thread") {
            @Override
            public void run() {
                while (true){
                    synchronized (MONITOR){
                        try {
                            MONITOR.wait();//wait必须在synchronized中使用
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.isInterrupted());
        t.interrupt();
        System.out.println(t.isInterrupted());
    }

    /**
     * 捕捉join的打断异常
     */
    public static void interrupt4(){
        Thread t = new Thread(){
            @Override
            public void run() {
                while (true){

                }
            }
        };
        t.start();
        Thread mainThread = Thread.currentThread();
        Thread t2 = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //打断t线程
//                t.interrupt();
                //t线程调用join，是main线程进入挂起状态，要想t线程的join捕捉到打断异常，需要调用main线程的打断
                mainThread.interrupt();
                System.out.println("打断t");
            }
        };
        t2.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("asdfsadfasdfsdfsdf");

    }

    /**
     * isInterrupted 监测终端标识，但不进行重置
     */
    public static void interrupt5(){
        Thread t = new Thread(){
            @Override
            public void run() {
                while(true){
                }
            }
        };

        t.start();
        t.interrupt();
        System.out.println(t.isInterrupted());
        System.out.println(t.isInterrupted());
    }
}
