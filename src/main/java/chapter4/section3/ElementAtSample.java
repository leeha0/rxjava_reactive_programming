package chapter4.section3;

import chapter4.DebugMaybeObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;

import java.util.concurrent.TimeUnit;

public class ElementAtSample {
    public static void main(String[] args) throws InterruptedException {
        Maybe<Long> maybe =
                // Flowable을 생성한다.
                Flowable.interval(100L, TimeUnit.MILLISECONDS)
                        // 위치가 3(0부터 시작)인 데이터만을 통지한다.
                        .elementAt(3);

        // 구독한다.
        maybe.subscribe(new DebugMaybeObserver<>());

        // 잠시 기다린다.
        Thread.sleep(1000L);
    }
}
