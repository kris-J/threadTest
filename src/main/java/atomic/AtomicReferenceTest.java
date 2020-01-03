package atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2020/1/3 10:28
 */
public class AtomicReferenceTest {

    public static void main(String[] args) {
        Simple simple = new Simple("a");
        AtomicReference atomicReference = new AtomicReference(simple);
        System.out.println(atomicReference.get());
        boolean b = atomicReference.compareAndSet(simple, new Simple("b"));
        System.out.println(b);
        System.out.println(atomicReference.get());
    }

    static class Simple {
        private String name;

        public Simple(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("{");
            sb.append("\"name\":\"")
                    .append(name).append('\"');
            sb.append('}');
            return sb.toString();
        }
    }
}
