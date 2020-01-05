package chapter4.section3;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class DebounceSample {
    public static void main(String[] args) {
        Flowable<String> flowable =
                // Flowable을 생성한다.
                Flowable.<String>create(
                        // 통지 처리
                        emitter -> {
                            // 데이터를 통지하고 잠시 기다린다.
                            emitter.onNext("A");
                            Thread.sleep(1000L); // 500L 후 A 통지

                            emitter.onNext("B");
                            Thread.sleep(300L);

                            emitter.onNext("C");
                            Thread.sleep(300L);

                            emitter.onNext("D");
                            Thread.sleep(1000L); // 500L 후 D 통지

                            emitter.onNext("E");
                            Thread.sleep(100L); // 완료 통지를 받고 E 통지 후 종료

                            // 완료를 통지한다.
                            emitter.onComplete();
                        }, BackpressureStrategy.BUFFER)
                        // 지정 기간 안에 다음 데이터가 오지 않으면 통지한다.
                        .debounce(data -> Flowable.timer(500L, TimeUnit.MILLISECONDS));

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());
    }
}
