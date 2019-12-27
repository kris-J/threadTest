package workerDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/18 14:02
 */
public class WorkerClient {

    public static void main(String[] args) {
        //创建channel
        Channel channel = new Channel(5);
        channel.startWorker();
        //创建装配者进行request输送
        new TransportThread("alex", channel).start();
        new TransportThread("jack", channel).start();
        new TransportThread("william", channel).start();
    }
}
