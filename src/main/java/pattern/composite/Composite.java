package pattern.composite;

import java.util.ArrayList;
import java.util.Iterator;

public class Composite extends Component {
    ArrayList leafs = new ArrayList();
    String name;

    public Composite(String name) {
        this.name = name;
    }

    @Override
    public void add(Component component) {
        leafs.add(component);
    }

    @Override
    public void remove(Component component) {
        leafs.remove(component);
    }

    @Override
    public Component getChild(int i) {
        return (Component) leafs.get(i);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void print() {
        System.out.println(getName());

        Iterator iterator = leafs.iterator();
        while (iterator.hasNext()) {
            Component component = (Component) iterator.next();
            component.print();
        }
    }

    public Iterator createIterator() {
        return new CompositeIterator(leafs.iterator());
    }
}
