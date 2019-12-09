package pattern.opserver.custom.subject;

import pattern.opserver.custom.observer.Observer;

public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver();
}
