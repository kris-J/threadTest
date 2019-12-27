package observerDesign.thread;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/3 16:17
 */
public abstract class AbstractObserverRunnable implements Runnable {


    protected LifeCycleListener listener;

    public AbstractObserverRunnable(LifeCycleListener listener) {
        this.listener = listener;
    }

    /**
     * 通知改变
     * @param event
     */
    protected void notifyChange(RunnableEvent event) {
        //回调listener的方法
        this.listener.onEvent(event);
    }

    /**
     * 状态
     */
    public enum RunnableState {
        RUNNING, ERROR, DONE
    }

    /**
     * 事件
     */
    public static class RunnableEvent {

        private final RunnableState state;
        private final Thread thread;
        private final Throwable errorMes;

        public RunnableEvent(RunnableState state, Thread thread, Throwable errorMes) {
            this.state = state;
            this.thread = thread;
            this.errorMes = errorMes;
        }

        public RunnableState getState() {
            return state;
        }

        public Thread getThread() {
            return thread;
        }

        public Throwable getErrorMes() {
            return errorMes;
        }
    }


}
