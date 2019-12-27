package guardedSuspensionDesign;

import java.util.Random;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/10 15:59
 */
public class ClientThread extends Thread {

    private RequestQueue queue;

    private Random random;

    private String sendValue;

    public ClientThread(RequestQueue queue, String sendValue) {
        this.queue = queue;
        this.sendValue = sendValue;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("client -> request " + sendValue);
            queue.putRequest(new Request(sendValue));
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
