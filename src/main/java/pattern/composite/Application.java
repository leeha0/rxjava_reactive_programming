package pattern.composite;

import java.util.Iterator;

public class Application {
    public static void main(String[] args) {
        Composite composite1 = new Composite("노드1");
        Composite composite2 = new Composite("노드2");
        Composite composite3 = new Composite("노드3");

        Composite allComposite = new Composite("최상단");
        allComposite.add(composite1);
        allComposite.add(composite2);
        allComposite.add(composite3);

        composite1.add(new Leaf("잎1"));
        composite1.add(new Leaf("잎2"));
        composite2.add(new Leaf("잎1"));
        composite3.add(new Leaf("잎1"));

//        allComposite.print();

        Iterator iterator = allComposite.createIterator();
        while (iterator.hasNext()) {
            Composite composite = (Composite) iterator.next();
            composite.print();
        }
    }
}
