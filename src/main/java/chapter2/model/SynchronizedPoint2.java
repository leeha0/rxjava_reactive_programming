package chapter2.model;

public class SynchronizedPoint2 {
    private int x;
    private int y;

    synchronized void rightUp() {
        x++;
        y++;
    }

    synchronized int getX() {
        return x;
    }

    synchronized int getY() {
        return y;
    }
}
