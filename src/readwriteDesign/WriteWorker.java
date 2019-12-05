package readwriteDesign;

import java.util.Random;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/4 16:39
 */
public class WriteWorker extends Thread {

    private static final Random random = new Random(System.currentTimeMillis());

    private final SharedData sharedData;

    private final String filler;

    private int index = 0;


    public WriteWorker(SharedData sharedData, String filler) {
        this.sharedData = sharedData;
        this.filler = filler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char c = nextChar();
                sharedData.write(c);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private char nextChar() {
        char c = filler.charAt(index);
        index++;
        if (index >= filler.length()) {
            index = 0;
        }
        return c;
    }
}
