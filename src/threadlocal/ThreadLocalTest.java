package threadlocal;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/10 16:38
 */
public class ThreadLocalTest {

    private static ThreadLocal threadLocal = new ThreadLocal(){
        /**
         * 默认初始值，不set调用，get出来的结果
         * @return
         */
        @Override
        protected Object initialValue() {
            return "init";
        }
    };

    /**
     * JVM start a main thread
     **/
    public static void main(String[] args) throws InterruptedException {
        threadLocal.set("Alax");
        Thread.sleep(1_000);
        System.out.println(threadLocal.get());
    }
}
