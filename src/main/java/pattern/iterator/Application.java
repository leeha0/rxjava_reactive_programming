package pattern.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        Iterator iterator = new ConcreteIterator(list, 0);

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}