package futureDesign;

/**
 * @author fangjie
 * @Description: 实现FutureTask接口，实现具体异步的处理逻辑
 * @date 2019/12/5 14:36
 */
public class FutrueTaskImpl implements FutureTask<String> {


    @Override
    public String call() {
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "finish";
    }
}
