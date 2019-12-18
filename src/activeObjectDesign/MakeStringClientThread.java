package activeObjectDesign;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2019/12/18 17:01
 */
public class MakeStringClientThread extends Thread {

    private ActiveObject activeObject;
    private char fillChar;

    public MakeStringClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        fillChar = name.charAt(0);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Result result = activeObject.makeString(fillChar, i);
                Thread.sleep(10);
                String resultValue = (String) result.getResultValue();
                System.out.println(Thread.currentThread().getName() + " value => " + resultValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
