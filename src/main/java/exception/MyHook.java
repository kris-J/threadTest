package exception;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/22 10:28
 */
public class MyHook {

    public static void main(String[] args) {

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //添加钩子，在程序即将结束时，可进行相关操作
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("application will be done.");
                System.out.println("release resources");
            }
        });

    }
}
