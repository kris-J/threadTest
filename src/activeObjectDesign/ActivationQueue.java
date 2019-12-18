package activeObjectDesign;

import java.util.LinkedList;

/**
 * @author fangjie
 * @Description: 请求队列
 * @date 2019/12/18 16:17
 */
public class ActivationQueue {

    private static int MAX_REQUEST_QUEUE_SIZE = 100;

    private LinkedList<AbstractMethodRequest> methodRequestQueue;

    public ActivationQueue() {
        this.methodRequestQueue = new LinkedList<>();
    }

    public synchronized void put(AbstractMethodRequest methodRequest) {
        while (methodRequestQueue.size() >= MAX_REQUEST_QUEUE_SIZE) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        methodRequestQueue.addLast(methodRequest);
        this.notifyAll();
    }

    public synchronized AbstractMethodRequest take() {
        while (methodRequestQueue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        AbstractMethodRequest first = methodRequestQueue.removeFirst();
        this.notifyAll();
        return first;
    }
}
