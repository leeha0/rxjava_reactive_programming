package chapter2.model;

// 순차로 값을 증가시키는 클래스
public class Counter {
    private volatile int count;

    public void increment() {
        count++;
    }

    public synchronized void syncIncrement() {
        count ++;
    }

    public int get() {
        return count;
    }
}
