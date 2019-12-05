package ImmutableDesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author fangjie
 * @Description: 不可变对象
 *      类声明使用final、字段声明使用final、不存在set方法、对于集合返回不可更改集合
 * @date 2019/12/5 13:42
 */
public final class ImmutableObject {

    private final String name;
    private final String address;
    private final List<String> list;

    public ImmutableObject(String name, String address) {
        this.name = name;
        this.address = address;
        this.list = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getList() {
        return Collections.unmodifiableList(list);
    }
}
