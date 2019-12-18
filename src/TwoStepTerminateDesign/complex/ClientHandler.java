package TwoStepTerminateDesign.complex;

import java.io.*;
import java.net.Socket;

/**
 * @author fangjie
 * @Description: 接收client端发送的消息
 * @date 2019/12/12 16:35
 */
public class ClientHandler implements Runnable {

    private final Socket socket;
    private volatile boolean running = true;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             PrintWriter printWriter = new PrintWriter(outputStream)) {
            while (running) {
                String message = reader.readLine();
                //client端可能主动关闭
                if (message == null) {
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " handler message come from client > " + message);
                printWriter.write("echo " + message + "\n");
                printWriter.flush();
            }
        } catch (IOException e) {
//            e.printStackTrace();
            this.running = false;
        }finally {
            this.stop();
        }
    }

    public void stop() {
        if (!running) {
            return;
        }
        this.running = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
