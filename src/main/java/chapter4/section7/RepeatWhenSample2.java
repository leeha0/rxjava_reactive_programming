package chapter4.section7;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class RepeatWhenSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable =
                Flowable.interval(100L, TimeUnit.MILLISECONDS)
                        // 3건까지 통지한다.
                        .take(3)
                        // 반복 통지를 제어한다.
                        .repeatWhen(completeHandler -> {
                            return completeHandler
                                    // 통지 시점을 늦춘다.
                                    .delay(1000L, TimeUnit.MILLISECONDS)
                                    // 2번 반복한다.
                                    .take(2)
                                    // 통지 시점에 정보를 출력한다.
                                    .doOnNext(data -> System.out.println("emit: " + data))
                                    .doOnComplete(() -> System.out.println("complete"));
                        })
                        // 데이터에 시스템 시각을 추가한다.
                        .map(data -> {
                            long time = System.currentTimeMillis();
                            return time + "ms :" + data;
                        });

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(5000L);
    }
}
