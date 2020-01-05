package chapter4.section3;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class TakeUntilSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable =
                // Flowable을 생성한다.
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        // 인자 Flowable이 첫 데이터를 통지할 때까지 받은 데이터를 통지한다.
                        // 1000밀리초 뒤에 숫자 0을 통지하고 종료
                        .takeUntil(Flowable.timer(1000L, TimeUnit.MILLISECONDS));

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(2000L);
    }
}
