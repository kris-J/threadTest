package activeObjectDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/18 16:09
 */

/**
 * {@link ActiveObject#makeString(char, int)}
 */
public class MakeStringRequest extends AbstractMethodRequest {

    private char fillChar;
    private int count;

    public MakeStringRequest(Servant servant, FutureResult futureResult, char fillChar, int count) {
        super(servant, futureResult);
        this.fillChar = fillChar;
        this.count = count;
    }

    @Override
    public void execute() {
        Result result = servant.makeString(fillChar, count);
        futureResult.setResult(result);
    }
}
