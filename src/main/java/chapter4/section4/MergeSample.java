package chapter4.section4;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class MergeSample {
    public static void main(String[] args) throws InterruptedException {
        // 병합하는 대상
        Flowable<Long> flowable1 =
                // 300 밀리초마다 데이터를 통지한다.
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        // 5건까지 통지한다.
                        .take(5);

        // 병합하는 대상
        Flowable<Long> flowable2 =
                // 300 밀리초마다 데이터를 통지한다.
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                        // 2건까지 통지한다.
                        .take(2)
                        .map(data -> data + 100L);

        // 여러 Flowable을 병합한다.
        Flowable<Long> result = Flowable.merge(flowable1, flowable2);

        // 구독한다.
        result.subscribe(new DebugSubscriber<>());

        // 잠시 기다란다.
        Thread.sleep(2000L);
    }
}
