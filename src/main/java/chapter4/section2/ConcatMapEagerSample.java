package chapter4.section2;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class ConcatMapEagerSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.range(10, 3)
                // 받은 데이터로 Flowable을 생성하고 바로 실행하지만 통지는 순서대로 한다.
                .concatMapEager(
                        // 데이터를 통지할 Flowable을 생성한다.
                        sourceData -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                                // 2건까지 통지한다.
                                .take(2)
                                // 원본 통지 데이터와 이 Flowable의 데이터를 조합해 문자열을 만든다.
                                .map(data -> {
                                    // 통지 시 시스템 시각도 데이터에 추가한다.
                                    long time = System.currentTimeMillis();
                                    return time + "ms: [" + sourceData + "] " + data;
                                }));

        /*
            // concatMapEager
            RxComputationThreadPool-3: 1578217448646ms: [10] 0
            RxComputationThreadPool-1: 1578217449143ms: [10] 1
            RxComputationThreadPool-1: 1578217448646ms: [11] 0
            RxComputationThreadPool-1: 1578217449144ms: [11] 1
            RxComputationThreadPool-1: 1578217448646ms: [12] 0
            RxComputationThreadPool-1: 1578217449144ms: [12] 1
            RxComputationThreadPool-1: 완료

            // concatMap
            RxComputationThreadPool-1: 1578217498198ms: [10] 0
            RxComputationThreadPool-1: 1578217498701ms: [10] 1
            RxComputationThreadPool-2: 1578217499202ms: [11] 0
            RxComputationThreadPool-2: 1578217499705ms: [11] 1
            RxComputationThreadPool-3: 1578217500210ms: [12] 0
            RxComputationThreadPool-3: 1578217500708ms: [12] 1
            RxComputationThreadPool-3: 완료
         */

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(4000L);
    }
}
