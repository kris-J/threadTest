package TwoStepTerminateDesign.simple;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/12 15:14
 */
public class CounterIncrement extends Thread {

    private volatile boolean terminated = false;

    private int counter = 0;

    @Override
    public void run() {
        try {
            while (!terminated) {
                System.out.println(Thread.currentThread().getName() + " " + (counter++));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //最终进行资源回收
            this.clean();
        }
    }

    private void clean() {
        System.out.println("do some clean job, current counter is " + counter);
    }

    public void close() {
        terminated = true;
        this.interrupt();
    }
}
