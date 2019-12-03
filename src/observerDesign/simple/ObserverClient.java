package observerDesign.simple;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/3 15:37
 */
public class ObserverClient {

    public static void main(String[] args) {
        Subject subject = new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);
        subject.setState(10);
        subject.setState(20);

    }
}
