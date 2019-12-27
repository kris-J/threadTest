package TwoStepTerminateDesign.simple;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/12 15:17
 */
public class CounterClient {

    public static void main(String[] args) throws InterruptedException {
        CounterIncrement counterIncrement = new CounterIncrement();
        counterIncrement.start();
        Thread.sleep(3000);
        counterIncrement.close();
    }
}
