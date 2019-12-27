package activeObjectDesign;

/**
 * @author fangjie
 * @Description: 接受异步消息的主动方法
 * @date 2019/12/18 15:51
 */
public interface ActiveObject {

    Result makeString(char fillChar,int count);

    /**
     * 展示字符串
     * @param text
     */
    void displayString(String text);
}
