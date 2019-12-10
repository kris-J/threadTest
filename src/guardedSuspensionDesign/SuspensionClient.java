package guardedSuspensionDesign;

/**
 * @author fangjie
 * @Description: 请求与处理服务，Request并不适合马上进行处理，则加入队列，等上一个请求处理完成后再进行下一个处理
 * @date 2019/12/10 16:16
 */
public class SuspensionClient {

    public static void main(String[] args) throws InterruptedException {
        RequestQueue requestQueue = new RequestQueue();
        new ClientThread(requestQueue, "Alax").start();
        ServerThread serverThread = new ServerThread(requestQueue);
        serverThread.start();

        Thread.sleep(12_000);
        serverThread.close();
    }
}
