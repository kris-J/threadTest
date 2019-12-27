package observerDesign.thread;

import java.util.List;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/3 16:22
 */
public class ThreadLifeCycleObserver implements LifeCycleListener {


    private final Object LOCK = new Object();

    /**
     * 多线程并行查询线程状态
     *
     * @param threadIds
     */
    public void concurrentQuery(List<String> threadIds) {
        if (threadIds == null || threadIds.isEmpty()) {
            return;
        }
        for (String threadId : threadIds) {
            //创建线程
            new Thread(new AbstractObserverRunnable(this) {
                @Override
                public void run() {
                    try {
                        notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                        System.out.println("query for id " + threadId);
                        Thread.sleep(1_000);
                        notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                    } catch (InterruptedException e) {
                        notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                    }
                }
            }, threadId).start();
        }
    }

    @Override
    public void onEvent(AbstractObserverRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println("the runnable [" + event.getThread().getName() + "] data changed and state is [" + event.getState() + "]");
            if (event.getErrorMes() != null) {
                System.out.println("the runnable [" + event.getThread().getName() + "] process failed");
                event.getErrorMes().printStackTrace();
            }
        }
    }
}
