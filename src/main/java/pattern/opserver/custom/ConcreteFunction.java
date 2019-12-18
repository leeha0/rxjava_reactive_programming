package pattern.opserver.custom;

import pattern.opserver.custom.function.Function;
import pattern.opserver.custom.observer.Observer;
import pattern.opserver.custom.subject.Subject;

public class ConcreteFunction implements Observer, Function {

    private Subject productLogInfo;
    private int clickCount;
    private int salesCount;

    public ConcreteFunction(Subject productLogInfo) {
        this.productLogInfo = productLogInfo;
        productLogInfo.registerObserver(this);
    }

    @Override
    public void update(int clickCount, int salesCount) {
        this.clickCount = clickCount;
        this.salesCount = salesCount;
        calculate();
    }

    @Override
    public void calculate() {
        System.out.println("Calculate : FinalScore(" + clickCount + ", " + salesCount + ")");
    }
}
