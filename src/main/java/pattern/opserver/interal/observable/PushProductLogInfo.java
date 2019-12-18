package pattern.opserver.interal.observable;

import java.util.Map;
import java.util.Observable;

public class PushProductLogInfo extends Observable {
    // Observer, Observable is deprecated (Java 9)
    // https://bugs.openjdk.java.net/browse/JDK-8154801
    private int clickCount;
    private int salesCount;

    public PushProductLogInfo() {
    }

    public void infoChanged() {
        setChanged();
        notifyObservers(this.clickCount);
    }

    public void setInfo(int clickCount, int salesCount) {
        this.clickCount = clickCount;
        this.salesCount = salesCount;
        infoChanged();
    }
}
