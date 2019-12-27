package guardedSuspensionDesign;

import java.util.Random;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/10 16:08
 */
public class ServerThread extends Thread {

    private RequestQueue queue;

    private Random random;

    private volatile boolean flag = false;

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (!flag) {
            Request request = queue.getRequest();
            if(request == null){
                System.out.println("the empty request");
                continue;
            }
            System.out.println("server -> " + request.getValue());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void close() {
        flag = true;
        this.interrupt();
    }
}
