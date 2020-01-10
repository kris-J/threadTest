package phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/9 16:22
 */
public class PhaserExample2 {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        //构造5个，无需register
        Phaser phaser = new Phaser(5);
        for (int i = 0; i < 5; i++) {
            new Task(phaser).start();
        }
    }

    static class Task extends Thread {

        private final Phaser phaser;

        public Task(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(Thread.currentThread().getName() + " is done step 1");
                phaser.arriveAndAwaitAdvance();

                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(Thread.currentThread().getName() + " is done step 2");
                phaser.arriveAndAwaitAdvance();


                TimeUnit.SECONDS.sleep(random.nextInt(5));
                System.out.println(Thread.currentThread().getName() + " is done step 3");
                phaser.arriveAndAwaitAdvance();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
