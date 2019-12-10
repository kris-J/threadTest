package guardedSuspensionDesign;

import java.util.LinkedList;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/10 15:52
 */
public class RequestQueue {

    private LinkedList<Request> queue = new LinkedList<>();

    public Request getRequest() {
        synchronized (queue) {
            while (queue.size() <= 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return queue.removeFirst();
        }
    }


    public void putRequest(Request request) {
        synchronized (queue) {
            queue.addLast(request);
            queue.notifyAll();
        }
    }
}
