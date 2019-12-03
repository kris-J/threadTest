package observerDesign.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/3 15:25
 */
public class Subject {

    private List<AbstractObserver> observers = new ArrayList<>();

    private int state;

    public int getState() {
        return state;
    }

    /**
     * 设置状态，发生变化时进行通知
     * @param state
     */
    public void setState(int state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        notifyAllObserver();
    }

    public void addObserver(AbstractObserver observer) {
        this.observers.add(observer);
    }

    private void notifyAllObserver() {
        for (AbstractObserver observer : observers) {
            observer.update();
        }
    }
}
