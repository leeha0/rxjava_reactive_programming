package pattern.opserver.interal.subject.impl;

import java.util.Observable;

public class ProductLogInfo extends Observable {
    private int clickCount;
    private int salesCount;

    public ProductLogInfo() {
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
