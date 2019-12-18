package workerDesign;

/**
 * @author fangjie
 * @Description: 被传送的信息
 * @date 2019/12/18 11:45
 */
public class Request {

    private String name;
    private int number;

    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " executed " + this);
    }

    @Override
    public String toString() {
        return "Request -> name." + name + " No." + number;
    }
}
