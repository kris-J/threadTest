package workerDesign;

/**
 * @author fangjie
 * @Description: 执行者
 * @date 2019/12/18 11:48
 */
public class WorkerThread extends Thread {

    private Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            this.channel.take().execute();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
