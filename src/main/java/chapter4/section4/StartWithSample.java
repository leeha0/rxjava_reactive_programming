package chapter4.section4;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class StartWithSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable =
                // 300밀리초마다 데이터를 통지한다.
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        // 5건까지 통지한다.
                        .take(5);

        Flowable<Long> other =
                // 500밀리초마다 데이터를 통지한다.
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                        // 2건까지 통지한다.
                        .take(2)
                        // 100을 더한다.
                        .map(data -> data + 100L);

        // 인자 Flowable을 먼저 실행한다.
        Flowable<Long> result = flowable.startWith(other);

        // 구독한다.
        result.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(3000L);
    }
}
