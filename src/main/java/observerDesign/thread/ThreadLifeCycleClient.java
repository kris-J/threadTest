package observerDesign.thread;

import java.util.Arrays;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/3 16:40
 */
public class ThreadLifeCycleClient {

    public static void main(String[] args) {
        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("1", "2"));
    }
}
