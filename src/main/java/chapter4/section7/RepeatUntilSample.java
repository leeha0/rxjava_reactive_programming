package chapter4.section7;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class RepeatUntilSample {
    public static void main(String[] args) throws InterruptedException {
        // 처리 시작 시각
        final long startTime = System.currentTimeMillis();

        Flowable<Long> flowable =
                // Flowable을 생성한다.
                Flowable.interval(100L, TimeUnit.MILLISECONDS)
                        // 3건까지 통지한다.
                        .take(3)
                        // 통지를 반복한다.
                        .repeatUntil(() -> {
                            // 호출 시점에 출력한다.
                            System.out.println("called");
                            // 처리를 시작하고 500밀리초가 될 때까지 반복한다.
                            return System.currentTimeMillis() - startTime > 500L;
                        });

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(1000L);
    }
}
