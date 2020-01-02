package atomic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/2 14:58
 */
public class AtomicIntegerTest {

    private static volatile int va = 0;
    private static Set<Integer> sa = Collections.synchronizedSet(new HashSet<>());
    private static AtomicInteger vb = new AtomicInteger();
    private static Set<Integer> sb = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                int index = 0;
                while (index < 500){
                    sa.add(va);
                    sb.add(vb.getAndIncrement());
                    va++;
                    index++;
                }
            }
        };
        Thread t2 = new Thread(){
            @Override
            public void run() {
                int index = 0;
                while (index < 500){
                    sa.add(va);
                    sb.add(vb.getAndIncrement());
                    va++;
                    index++;
                }
            }
        };
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(sa.size());
        System.out.println(sb.size());
    }
}
