package countdownDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/11 15:11
 */
public class CountDownSelf {

    private final int total;
    private int counter = 0;

    public CountDownSelf(int total) {
        this.total = total;
    }

    public void down() {
        synchronized (this) {
            this.counter++;
            this.notifyAll();
        }
    }

    public void await() throws InterruptedException {
        synchronized (this) {
            while (this.counter != total) {
                this.wait();
            }
        }
    }
}
