package TwoStepTerminateDesign.complex;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fangjie
 * @Description: 监听客户端socket
 * @date 2019/12/12 16:29
 */
public class AppServer extends Thread {

    private int port;
    private final static int DEFAULT_PORT = 12345;

    private volatile boolean start = true;

    private List<ClientHandler> clientHandlers = new ArrayList<>();

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    private ServerSocket serverSocket;

    public AppServer() {
        this(DEFAULT_PORT);
    }

    public AppServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (start) {
                //socket等待客户端建立连接，建立连接后启动线程进行客户端的信息交互
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                executor.submit(clientHandler);
                this.clientHandlers.add(clientHandler);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            this.dispose();
        }

    }

    private void dispose() {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.stop();
        }
        executor.shutdown();
    }

    public void shutdown() {
        this.start = false;
        this.interrupt();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
