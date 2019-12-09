package pattern.opserver.interal;

import pattern.opserver.interal.function.Function;
import pattern.opserver.interal.subject.impl.ProductLogInfo;

import java.util.Observable;
import java.util.Observer;

public class Function2 implements Observer, Function {

    Observable observable;
    private int clickCount;
    private int salesCount;

    public Function2(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof ProductLogInfo) {
            ProductLogInfo productLogInfo = (ProductLogInfo) o;
            this.clickCount = productLogInfo.getClickCount();
            this.salesCount = productLogInfo.getSalesCount();
            calculate();
        }
    }

    @Override
    public void calculate() {
        System.out.println("Calculate : FinalScore(" + clickCount + ", " + salesCount + ")");
    }
}
