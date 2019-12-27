package activeObjectDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/18 16:48
 */
public final class ActiveObjectFactory {

    public ActiveObjectFactory() {
    }

    public static ActiveObject createActiveObject(){
        Servant servant = new Servant();
        ActivationQueue activationQueue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(activationQueue);
        ActiveObjectProxy activeObjectProxy = new ActiveObjectProxy(schedulerThread, servant);
        schedulerThread.start();
        return activeObjectProxy;
    }
}
