package chapter4.section2;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class FlatMapSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.range(1, 3)
                // mapper와 combiner를 인자로 받은 flatMap 메서드
                .flatMap(
                        // 첫 번째 인자: 데이터를 받으면 새로운 Flowable을 생성한다.
                        data -> {
                            return Flowable.interval(100L, TimeUnit.MILLISECONDS)
                                    // 3건까지 통지한다.
                                    .take(3);
                        },
                        // 두 번째 인자: 원본 데이터와 변환한 데이터로 새로운 통지 데이터를 생성한다.
                        (sourceData, newData) -> "[" + sourceData + "]" + newData);

        // 구독 시작
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(1000L);
    }
}
