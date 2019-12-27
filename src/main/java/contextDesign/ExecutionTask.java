package contextDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/11 10:35
 */
public class ExecutionTask implements Runnable {

    private QueryFromDBAction queryFromDBActionAction = new QueryFromDBAction();

    private QueryFromHttpAction queryFromHttpAction = new QueryFromHttpAction();

    @Override
    public void run() {
        queryFromDBActionAction.execute();
        System.out.println("name query success");
        queryFromHttpAction.execute();
        System.out.println("cardId query success");
        Context context = ActionContext.getInstance().getContext();
        System.out.println("The name is " + context.getName() + " and cardId is " + context.getCardId());
    }
}
