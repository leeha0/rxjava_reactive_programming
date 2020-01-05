package chapter4.section3;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class SkipLastSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable =
                // Flowable을 생성한다.
                Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                        // 5건까지 통지한다.
                        .take(5)
                        // 마지막 2건을 통지하지 않는다.
                        .skipLast(2);

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(6000L);
    }
}
