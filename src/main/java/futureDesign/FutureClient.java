package futureDesign;

import java.util.function.Consumer;

/**
 * @author fangjie
 * @Description: 请求后，处理其他逻辑，在适当的地方再去获取数据，如果有结果则直接得到，如果还未有结果，则等待
 * <p>
 * Future           -> 代表未来的凭据
 * FutureTask       -> 调用逻辑进行隔离
 * FutureService    -> 桥接Future和FutureTask
 * @date 2019/12/5 14:33
 */
public class FutureClient {

    public static void main(String[] args) throws InterruptedException {
        FutureService futureService = new FutureService();
        Future<String> future = futureService.submit(new FutrueTaskImpl());

        /** 利用jdk1.8的Consumer，进行函数式回调，无需调用get **/
        Future<String> future2 = futureService.submit(new FutrueTaskImpl(),new Consumer<String>(){
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        System.out.println("====================");
        System.out.println("do other thing");
        Thread.sleep(1_000);
        System.out.println("====================");

        System.out.println(future.get());

    }
}
