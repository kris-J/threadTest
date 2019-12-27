package exception;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/11/22 14:17
 */
public class CatchException {

    public static void main(String[] args) {
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException();
            }
        };

        t.start();
        t.setUncaughtExceptionHandler(new ExceptionHandler());
        StackTraceElement[] stackTrace = t.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
        }
    }

}

class ExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t.getName() + " has exception");
        e.printStackTrace();
    }
}
