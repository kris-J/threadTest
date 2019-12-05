package readwriteDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/4 16:47
 */
public class ReadWriteLockClient {

    public static void main(String[] args) {
        SharedData sharedData = new SharedData(10);
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new ReadWorker(sharedData).start();
        new WriteWorker(sharedData,"abcdefg").start();
        new WriteWorker(sharedData,"ABCDEFG").start();
    }
}
