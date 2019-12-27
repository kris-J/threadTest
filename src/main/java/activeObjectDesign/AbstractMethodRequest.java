package activeObjectDesign;

/**
 * @author fangjie
 * @Description: 对应ActiveObject的每一个方法
 * @date 2019/12/18 15:52
 */
public abstract class AbstractMethodRequest {


    protected final Servant servant;

    protected final FutureResult futureResult;

    public AbstractMethodRequest(Servant servant, FutureResult futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execute();


}
