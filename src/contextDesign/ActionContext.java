package contextDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/11 10:50
 */
public class ActionContext {

    private static ThreadLocal<Context> threadLocal = new ThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    /**
     * 内部类实现单例模式
     */
    private static class ContextHolder {
        private final static ActionContext actionContext = new ActionContext();
    }

    public static ActionContext getInstance() {
        return ContextHolder.actionContext;
    }

    public Context getContext() {
        return threadLocal.get();
    }
}
