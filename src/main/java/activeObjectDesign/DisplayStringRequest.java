package activeObjectDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/18 16:15
 */

/**
 * {@link ActiveObject#displayString(String)}
 */
public class DisplayStringRequest extends AbstractMethodRequest {

    private String text;

    public DisplayStringRequest(Servant servant, FutureResult futureResult, String text) {
        super(servant, futureResult);
        this.text = text;
    }

    @Override
    public void execute() {
        servant.displayString(text);
    }
}
