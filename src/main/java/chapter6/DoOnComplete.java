package chapter6;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class DoOnComplete {
    public static void main(String[] args) {
        Flowable.range(1, 5)
                .doOnComplete(() -> System.out.println("doOnComplete"))
                .subscribe(new DebugSubscriber<>());
    }
}
