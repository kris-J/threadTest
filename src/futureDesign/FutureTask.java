package futureDesign;

/**
 * @author fangjie
 * @Description: 具体异步处理的逻辑
 * @date 2019/12/5 14:25
 */
public interface FutureTask<T> {

    T call();
}
