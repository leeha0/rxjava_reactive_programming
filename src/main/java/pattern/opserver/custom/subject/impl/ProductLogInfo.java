package pattern.opserver.custom.subject.impl;

import pattern.opserver.custom.observer.Observer;
import pattern.opserver.custom.subject.Subject;

import java.util.ArrayList;

public class ProductLogInfo implements Subject {

    private ArrayList observers;
    private int clickCount;
    private int salesCount;

    public ProductLogInfo() {
        observers = new ArrayList();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObserver() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update(clickCount, salesCount);
        }
    }

    public void infoChanged() {
        notifyObserver();
    }

    public void setInfo(int clickCount, int salesCount) {
        this.clickCount = clickCount;
        this.salesCount = salesCount;
        infoChanged();
    }
}
