package chapter4.section5;

import chapter4.DebugSingleObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.concurrent.TimeUnit;

public class ContainsSample {
    public static void main(String[] args) throws InterruptedException {
        Single<Boolean> single =
                // Flowable을 생성한다.
                Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                        .take(1)
                        // 인자의 데이터가 포함됐는지 판단한다.
                        .contains(3L);

        // 구독한다.
        single.subscribe(new DebugSingleObserver<>());

        // 잠시 기다린다.
        Thread.sleep(4000L);
    }
}
