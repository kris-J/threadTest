/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/14 15:51
 */
public class DeamonThread {

    public static void main(String[] args) {
        Thread t = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        /**
         * t设置为main线程的守护线程，如果main线程执行完毕，则没有其他非守护线程，则t线程自动关闭
         *
         * **/
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");

    }
}
