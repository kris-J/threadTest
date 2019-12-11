package contextDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/11 10:37
 */
public class QueryFromDBAction {

    public void execute() {
        try {
            Thread.sleep(1000);
            String name = "Alex " + Thread.currentThread().getName();
            ActionContext.getInstance().getContext().setName(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
