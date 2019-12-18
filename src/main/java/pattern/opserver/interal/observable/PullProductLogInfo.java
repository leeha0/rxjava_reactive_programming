package pattern.opserver.interal.observable;

import java.util.Observable;

public class PullProductLogInfo extends Observable {
    // Observer, Observable is deprecated (Java 9)
    // https://bugs.openjdk.java.net/browse/JDK-8154801
    private int clickCount;
    private int salesCount;

    public PullProductLogInfo() {
    }

    public void infoChanged() {
        setChanged();
        notifyObservers();
    }

    public void setInfo(int clickCount, int salesCount) {
        this.clickCount = clickCount;
        this.salesCount = salesCount;
        infoChanged();
    }

    public int getClickCount() {
        return clickCount;
    }

    public int getSalesCount() {
        return salesCount;
    }
}
