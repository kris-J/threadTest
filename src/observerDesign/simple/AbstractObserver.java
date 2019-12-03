package observerDesign.simple;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/3 15:26
 */
public abstract class AbstractObserver {

    protected Subject subject;

    public AbstractObserver(Subject subject) {
        this.subject = subject;
        this.subject.addObserver(this);
    }

    public abstract void update();
}
