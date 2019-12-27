package futureDesign;

import java.util.function.Consumer;

/**
 * @author fangjie
 * @Description: 异步服务，负责提交client提交的请求
 * @date 2019/12/5 14:26
 */
public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> task) {
        AsynFuture<T> asynFuture = new AsynFuture<>();
        //创建线程进行具体异步逻辑的调用，并直接返回Future
        new Thread() {
            @Override
            public void run() {
                T result = task.call();
                asynFuture.done(result);
            }
        }.start();

        return asynFuture;
    }

    /**
     * jdk1.8，利用Consumer进行回调
     * @param task
     * @param consumer
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(final FutureTask<T> task, Consumer<T> consumer) {
        AsynFuture<T> asynFuture = new AsynFuture<>();
        //创建线程进行具体异步逻辑的调用，并直接返回Future
        new Thread() {
            @Override
            public void run() {
                T result = task.call();
                asynFuture.done(result);
                consumer.accept(result);
            }
        }.start();

        return asynFuture;
    }
}
