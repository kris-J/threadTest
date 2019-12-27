package futureDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/5 14:24
 */
public interface Future<T> {

    /**
     * 获取最终结果
     * @return
     * @throws InterruptedException
     */
    T get() throws InterruptedException;
}
