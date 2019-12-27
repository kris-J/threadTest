package interrupt;

/**
 * @author fangjie
 * @Description: 业务执行时间较长，无法用打断标识来中断，可借用守护线程的打断机制进行中断
 * @date 2019/11/14 15:55
 */
public class Interrupt2 {

    public static void main(String[] args) {
        Thread monitorThread = new Thread() {
            @Override
            public void run() {
                Thread businessThread = new Thread(new BusinessService());
                //将业务处理服务设置为监控线程的守护线程
                businessThread.setDaemon(true);
                businessThread.start();
                try {
                    businessThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("捕捉到监控线程进行打断的通知");
                }
            }
        };

        monitorThread.start();
        //模拟监控5秒，进行打断
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //监控5秒，业务还未处理完成，则将监控线程打断，从而将监控线程的守护线程businessThread中断
        System.out.println("业务处理超时，进行打断处理");
        monitorThread.interrupt();
    }
}

/**
 * 业务处理,时间较长
 */
class BusinessService implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("业务正在处理:" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


