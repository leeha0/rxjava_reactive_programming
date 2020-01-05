package chapter4.section3;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class TakeLastSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable =
                // Flowable을 생성한다.
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        // 10건까지 통지한다.
                        .take(10)
                        // 완료 전 1000밀리초 동안 통지된 데이터 중 끝에서부터 2건을 통지한다.
                        // count 값을 5이상 설정해도 4건만 통지된다.
                        .takeLast(2, 1000L, TimeUnit.MILLISECONDS);

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(4000L);
    }
}
