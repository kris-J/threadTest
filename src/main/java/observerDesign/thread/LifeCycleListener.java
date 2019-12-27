package observerDesign.thread;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/3 16:18
 */
public interface LifeCycleListener {

    /**
     * 事件回调
     * @param event
     */
    void onEvent(AbstractObserverRunnable.RunnableEvent event);
}
