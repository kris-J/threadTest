package atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author fangjie
 * @Description: 类的属性操作具备原子性
 *              1.volatile
 *              2.不是当前类的属性，必须非private、protected
 *              3.类型必须一致
 * @date 2020/1/3 13:57
 */
public class AtomicFieldUpdaterTest {

    private volatile int a;

    public static void main(String[] args) {
//        updateSuccessExample();
//        updateFailedExample();
        updateProtectedExample();
    }

    public static void updateSuccessExample() {
        //对integer字段进行原子性更新
        AtomicIntegerFieldUpdater<TestMe> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe testMe = new TestMe();
        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 20; j++) {
                        int v = fieldUpdater.incrementAndGet(testMe);
                        System.out.println(Thread.currentThread().getName() + "=>" + v);
                    }
                }
            }.start();
        }
    }

    /**
     * 对于private私有的属性，会抛出IllegalAccessException，无法修改private属性
     */
    public static void updateFailedExample() {
        TestMe testMe = new TestMe();
        AtomicIntegerFieldUpdater<TestMe> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "y");
        boolean b = fieldUpdater.compareAndSet(testMe, 0, 1);
        System.out.println(b);
    }

    /**
     * 对于本类属性，可以为protected
     */
    public static void updateProtectedExample() {
        AtomicFieldUpdaterTest test = new AtomicFieldUpdaterTest();
        AtomicIntegerFieldUpdater<AtomicFieldUpdaterTest> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(AtomicFieldUpdaterTest.class, "a");
        boolean b = fieldUpdater.compareAndSet(test, 0, 1);
        System.out.println(b);
    }

    static class TestMe {
        volatile int i;
        private volatile int y;
    }
}
