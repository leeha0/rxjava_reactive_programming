package chapter4.section4;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConcatEagerSample {
    public static void main(String[] args) throws InterruptedException {
        // 결합 대상
        Flowable<Long> flowable1 =
                // 300 밀리초마다 데이터를 통지한다.
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        // 5건까지 통지한다.
                        .take(5)
                        .doOnNext((aLong) -> {
                            // 통지 시 시스템 시각도 데이터에 추가한다.
                            long time = System.currentTimeMillis();
                            System.out.println(time + "ms: [" + (aLong) + "] ");
                        });


        // 결합 대상
        Flowable<Long> flowable2 =
                // 500 밀리초마다 데이터를 통지한다.
                Flowable.interval(500L, TimeUnit.MILLISECONDS)
                        // 5건까지 통지한다.
                        .take(5)
                        // 100을 더한다.
                        .map(data -> data + 100L)
                        .doOnNext((aLong) -> {
                            // 통지 시 시스템 시각도 데이터에 추가한다.
                            long time = System.currentTimeMillis();
                            System.out.println(time + "ms: [" + (aLong) + "] ");
                        });

        // 여러 개의 Flowable을 결합한다.
        List<Flowable<Long>> sources = Arrays.asList(flowable1, flowable2);
        Flowable<Long> result = Flowable.concatEager(sources);

        // 구독한다.
        result.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(3000L);
    }
}
