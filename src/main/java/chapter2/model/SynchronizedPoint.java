package chapter2.model;

public class SynchronizedPoint {
    private final Object lock = new Object();
    private int x;
    private int y;

    void rightUp() {
        synchronized (lock) {
            x++;
            y++;
        }
    }

    int getX() {
        synchronized (lock) {
            return x;
        }
    }

    int getY() {
        synchronized (lock) {
            return y;
        }
    }
}
