package pattern.iterator;

import java.util.Iterator;
import java.util.List;

public class JavaConcreteIterator implements Iterator {
    List<String> items;
    int position = 0;

    public JavaConcreteIterator(List<String> items, int position) {
        this.items = items;
        this.position = position;
    }

    @Override
    public boolean hasNext() {
        if (position >= items.size() || items.get(position) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object next() {
        String item = items.get(position);
        position = position + 1;
        return item;
    }
}
