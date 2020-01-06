package chapter4.section5;

import chapter4.DebugSingleObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.concurrent.TimeUnit;

public class CountSample {
    public static void main(String[] args) throws InterruptedException {
        Single<Long> single =
                // Flowable을 생성한다.
                Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                        // 3건까지 통지한다.
                        .take(3)
                        // 데이터 개수를 통지한다.
                        .count();

        // 구독한다.
        single.subscribe(new DebugSingleObserver<>());

        // 잠시 기다린다.
        Thread.sleep(4000L);
    }
}
