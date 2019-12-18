package pattern.opserver.interal.observer;

import pattern.opserver.interal.function.Function;
import pattern.opserver.interal.observable.PullProductLogInfo;

import java.util.Observable;
import java.util.Observer;

public class PullConcreteFunction implements Observer, Function {

    Observable observable;
    private int clickCount;
    private int salesCount;

    public PullConcreteFunction(Observable observable) {
        // 구독 요청
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PullProductLogInfo) {
            // Pull 방식
            PullProductLogInfo pullProductLogInfo = (PullProductLogInfo) o;
            this.clickCount = pullProductLogInfo.getClickCount();
            this.salesCount = pullProductLogInfo.getSalesCount();
            calculate();
        }
    }

    @Override
    public void calculate() {
        System.out.println("Calculate : FinalScore(" + clickCount + ", " + salesCount + ")");
    }
}
