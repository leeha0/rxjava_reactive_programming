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

    @Override
    public void remove() {
        if (position <= 0) {
            throw new IllegalStateException("next()를 한 번도 호출하지 않은 상태에서는 삭제할 수 없습니다.");
        }

        if (items.get(position - 1) != null) {
            items.remove(position - 1);
        }
    }
}
