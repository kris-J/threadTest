package observerDesign.simple;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/3 15:33
 */
public class OctalObserver extends AbstractObserver {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Ocatl string:" + Integer.toOctalString(subject.getState()));
    }
}
