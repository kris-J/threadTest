package activeObjectDesign;

/**
 * @author fangjie
 * @Description: 真正执行者，包可见,class没有修饰词,默认为包可见
 * @date 2019/12/18 15:56
 */
class Servant implements ActiveObject {
    @Override
    public Result makeString(char fillChar, int count) {
        char[] buff = new char[count];
        for (int i = 0; i < count; i++) {
            buff[i] = fillChar;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new RealResult(new String(buff));
    }

    @Override
    public void displayString(String text) {
        try {
            System.out.println("Display=>" + text);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
