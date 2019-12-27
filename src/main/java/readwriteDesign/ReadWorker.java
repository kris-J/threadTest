package readwriteDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/4 16:44
 */
public class ReadWorker extends Thread {

    private final SharedData sharedData;


    public ReadWorker(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char[] buffer = sharedData.read();
                System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(buffer));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
