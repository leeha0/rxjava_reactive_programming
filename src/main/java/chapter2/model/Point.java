package chapter2.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Point {
    private final AtomicInteger x = new AtomicInteger(0);
    private final AtomicInteger y = new AtomicInteger(0);

    void rightUp() {
        x.incrementAndGet();
        y.incrementAndGet();
    }

    int getX() {
        return x.get();
    }

    int getY() {
        return y.get();
    }
}
