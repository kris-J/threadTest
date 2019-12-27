package workerDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/18 11:44
 */
public class Channel {
    /**
     * 最大请求数
     **/
    private static int MAX_REQUEST = 100;
    /**
     * 请求队列,存放需要处理的请求
     **/
    private Request[] requestQueue;
    /**
     * 队列头位置
     **/
    private int head;
    /**
     * 队列尾位置
     **/
    private int tail;
    /**
     * 工作者池,用于异步执行请求
     **/
    private WorkerThread[] workerPool;
    /**
     * 当前请求数量
     **/
    private int count;

    public Channel(int poolSize) {
        this.requestQueue = new Request[MAX_REQUEST];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        this.workerPool = new WorkerThread[poolSize];
        this.init();
    }

    private void init() {
        for (int i = 0; i < workerPool.length; i++) {
            workerPool[i] = new WorkerThread("worker-" + i, this);
        }
    }


    public void startWorker() {
        for (WorkerThread workerThread : workerPool) {
            workerThread.start();
        }
    }

    public synchronized void put(Request request) {
        while (this.count >= requestQueue.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.requestQueue[this.tail] = request;
        this.tail = (this.tail + 1) % this.requestQueue.length;
        this.count++;
        this.notifyAll();
    }

    public synchronized Request take() {
        while (this.count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Request request = this.requestQueue[this.head];
        this.head = (this.head + 1) % this.requestQueue.length;
        this.count--;
        this.notifyAll();
        return request;
    }


}
