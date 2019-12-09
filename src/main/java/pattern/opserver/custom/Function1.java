package pattern.opserver.custom;

import pattern.opserver.custom.function.Function;
import pattern.opserver.custom.observer.Observer;
import pattern.opserver.custom.subject.Subject;

public class Function1 implements Observer, Function {

    private Subject productLogInfo;
    private int clickCount;
    private int salesCount;

    public Function1(Subject productLogInfo) {
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
