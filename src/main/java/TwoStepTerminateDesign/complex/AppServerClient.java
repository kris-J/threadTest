package TwoStepTerminateDesign.complex;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/12 16:48
 */
public class AppServerClient {

    public static void main(String[] args) {
        AppServer appServer = new AppServer(13345);
        appServer.start();
        //server端主动关闭连接
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appServer.shutdown();
    }
}
