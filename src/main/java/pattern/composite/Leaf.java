package pattern.composite;

import java.util.Iterator;

public class Leaf extends Component {
    String name;

    public Leaf(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void print() {
        System.out.println(getName());
    }

    public Iterator createIterator() {
        return new NullIterator();
    }
}
