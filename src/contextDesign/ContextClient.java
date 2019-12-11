package contextDesign;

/**
 * @author fangjie
 * @Description: 利用ThreadLocal，为每个线程单独封装Context上下文，省略参数传递
 * @date 2019/12/11 10:45
 */
public class ContextClient {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new ExecutionTask()).start();
        }
    }
}
