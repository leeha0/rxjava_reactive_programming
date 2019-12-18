package pattern.opserver.interal;

import pattern.opserver.interal.observable.PullProductLogInfo;
import pattern.opserver.interal.observable.PushProductLogInfo;
import pattern.opserver.interal.observer.PullConcreteFunction;
import pattern.opserver.interal.observer.PushConcreteFunction;

import java.beans.PropertyEditor;

public class Application {
    public static void main(String[] args) {
        pullTypeTest();
        pushTypeTest();
    }

    private static void pullTypeTest() {
        // Observable
        PullProductLogInfo pullProductLogInfo = new PullProductLogInfo();

        // Observer
        PullConcreteFunction pullConcreteFunction = new PullConcreteFunction(pullProductLogInfo);

        pullProductLogInfo.setInfo(1, 0);
        pullProductLogInfo.setInfo(3, 0);
        pullProductLogInfo.setInfo(10, 0);
        pullProductLogInfo.setInfo(12, 1);
    }

    private static void pushTypeTest() {
        // Observable
        PushProductLogInfo pushProductLogInfo = new PushProductLogInfo();

        // Observer
        PushConcreteFunction pushConcreteFunction = new PushConcreteFunction(pushProductLogInfo);

        pushProductLogInfo.setInfo(1, 0);
        pushProductLogInfo.setInfo(3, 0);
        pushProductLogInfo.setInfo(10, 0);
        pushProductLogInfo.setInfo(12, 1);
    }
}
