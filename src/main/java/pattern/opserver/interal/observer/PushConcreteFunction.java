package pattern.opserver.interal.observer;

import pattern.opserver.interal.function.Function;
import pattern.opserver.interal.observable.PullProductLogInfo;
import pattern.opserver.interal.observable.PushProductLogInfo;

import java.util.Observable;
import java.util.Observer;

public class PushConcreteFunction implements Observer, Function {

    Observable observable;
    private int clickCount;
    private int salesCount;

    public PushConcreteFunction(Observable observable) {
        // 구독 요청
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        // Object args : Push 방식

        if (o instanceof PushProductLogInfo) {
            PushProductLogInfo pushProductLogInfo = (PushProductLogInfo) o;
//            this.clickCount = pullProductLogInfo.getClickCount();
//            this.salesCount = pullProductLogInfo.getSalesCount();
            this.clickCount = (int) arg;
            this.salesCount = 0;
            calculate();
        }
    }

    @Override
    public void calculate() {
        System.out.println("Calculate : FinalScore(" + clickCount + ", " + salesCount + ")");
    }
}
