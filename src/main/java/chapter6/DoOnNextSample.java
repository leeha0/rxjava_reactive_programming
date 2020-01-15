package chapter6;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class DoOnNextSample {
    public static void main(String[] args) {
        Flowable.range(1, 5)
                .doOnNext(data -> System.out.println("[doOnNext] 기존 데이터: " + data))
                .filter(data -> data % 2 == 0)
                .doOnNext(data -> System.out.println("[doOnNext] filter 적용 후 데이터: " + data))
                .subscribe(new DebugSubscriber<>());
    }
}
