package chapter4.section5;

import chapter4.DebugSingleObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.concurrent.TimeUnit;

public class SequenceEqualSample {
    public SequenceEqualSample() {
    }

    public static void main(String[] args) throws InterruptedException {
        // 비교 대상
        Flowable<Long> flowable1 =
                Flowable.interval(1000L, TimeUnit.MILLISECONDS)
                        .take(3);

        // 비교 대상
        Flowable<Long> flowable2 =
                Flowable.just(0L, 1L, 2L);

        // 같은 데이터가 같은 순서로 같은 수만큼 있는지를 판단한다.
        Single<Boolean> single = Flowable.sequenceEqual(flowable1, flowable2);

        // 구독한다.
        single.subscribe(new DebugSingleObserver<>());

        // 잠시 기다린다.
        Thread.sleep(4000L);
    }
}
