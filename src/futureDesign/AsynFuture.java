package futureDesign;

/**
 * @author fangjie
 * @Description: 实现Future接口，并具体执行异步逻辑是否完成、等待逻辑
 * @date 2019/12/5 14:27
 */
public class AsynFuture<T> implements Future<T> {

    /**
     * 任务是否完成
     */
    private volatile boolean done = false;

    /**
     * 最终结果
     */
    private T result;

    /**
     * 任务已完成并赋值结果
     * @param result
     */
    public void done(T result){
        synchronized (this){
            this.result = result;
            this.done = true;
            this.notifyAll();
        }
    }

    @Override
    public T get() throws InterruptedException {
        synchronized (this){
            while (!done){
                this.wait();
            }
        }
        return result;
    }
}
