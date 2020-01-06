package chapter4.section5;

import chapter4.DebugSingleObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.concurrent.TimeUnit;

public class IsEmptySample {
    public static void main(String[] args) throws InterruptedException {
        Single<Boolean> single =
                // Flowable을 생성한다.
                Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                        // 3건까지 통지한다.
                        .take(3)
                        // 3 이상만 통지한다.
                        .filter(data -> data >= 3)
                        // 통지할 데이터가 있는지 판단한다.
                        .isEmpty();

        // 구독한다.
        single.subscribe(new DebugSingleObserver<>());

        // 잠시 기다린다.
        Thread.sleep(4000L);
    }
}
