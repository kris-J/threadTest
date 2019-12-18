package activeObjectDesign;

/**
 * @author fangjie
 * @Description: ActiveObject代理
 * @date 2019/12/18 16:42
 */
class ActiveObjectProxy implements ActiveObject {

    private SchedulerThread schedulerThread;

    private Servant servant;

    public ActiveObjectProxy(SchedulerThread schedulerThread, Servant servant) {
        this.schedulerThread = schedulerThread;
        this.servant = servant;
    }

    /**
     * 使用future设计模式，发送带有返回值的makeString请求
     * @param fillChar
     * @param count
     * @return
     */
    @Override
    public Result makeString(char fillChar, int count) {
        FutureResult futureResult = new FutureResult();
        schedulerThread.invoke(new MakeStringRequest(servant, futureResult, fillChar, count));
        return futureResult;
    }

    /**
     * 发送displayString请求
     * @param text
     */
    @Override
    public void displayString(String text) {
        schedulerThread.invoke(new DisplayStringRequest(servant, null, text));
    }
}
