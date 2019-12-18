package activeObjectDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/18 16:36
 */
public class SchedulerThread extends Thread {

    private ActivationQueue activationQueue;

    public SchedulerThread(ActivationQueue activationQueue) {
        this.activationQueue = activationQueue;
    }

    /**
     * 向activequeue队列中放入请求
     * @param methodRequest
     */
    public void invoke(AbstractMethodRequest methodRequest) {
        this.activationQueue.put(methodRequest);
    }

    /**
     * 从activequeue队列中获取请求并执行
     */
    @Override
    public void run() {
        while (true) {
            this.activationQueue.take().execute();
        }
    }
}
