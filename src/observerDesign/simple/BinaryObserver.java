package observerDesign.simple;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/3 15:33
 */
public class BinaryObserver extends AbstractObserver {

    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Binary string:" + Integer.toBinaryString(subject.getState()));
    }
}
