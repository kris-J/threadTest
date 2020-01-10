package phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/9 16:10
 */
public class PhaserExample {

    public static void main(String[] args) {
        Phaser phaser = new Phaser();
        for (int i = 0; i < 5; i++) {
            new Task(phaser).start();
        }

        phaser.register();
        phaser.arriveAndAwaitAdvance();
        System.out.println("all work has done");
    }

    static class Task extends Thread {

        private final Phaser phaser;

        public Task(Phaser phaser) {
            this.phaser = phaser;
            //注册
            this.phaser.register();
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is working");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + " is done");
                phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
