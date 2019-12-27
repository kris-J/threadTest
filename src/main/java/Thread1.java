/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/9/11 14:57
 */
public class Thread1 {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread("ssss"){
            @Override
            public void run() {
                synchronized (LOCK){
                    System.out.println("dfsdfsdf"+Thread.currentThread().getName());
                    try {
//                        Thread.sleep(1000*5);
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread("aaa"){
            @Override
            public void run() {
                synchronized (LOCK){
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000*5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
