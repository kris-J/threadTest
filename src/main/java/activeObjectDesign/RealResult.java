package activeObjectDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/18 15:59
 */
public class RealResult implements Result {


    private Object resultValue;

    public RealResult(Object resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public Object getResultValue() {
        return this.resultValue;
    }
}
