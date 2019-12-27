package contextDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/11 10:41
 */
public class QueryFromHttpAction {

    public void execute() {
        try {
            Thread.sleep(1000);
            Context context = ActionContext.getInstance().getContext();
            String cardId = getCardId(context.getName());
            context.setCardId(cardId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getCardId(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "12332323" + Thread.currentThread().getId();

    }
}
