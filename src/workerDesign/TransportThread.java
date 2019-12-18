package workerDesign;

/**
 * @author fangjie
 * @Description: 装配者
 * @date 2019/12/18 13:55
 */
public class TransportThread extends Thread {

    private Channel channel;

    public TransportThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }


    @Override
    public void run() {
        for (int i = 0; true; i++) {
            Request request = new Request(getName(), i);
            this.channel.put(request);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
