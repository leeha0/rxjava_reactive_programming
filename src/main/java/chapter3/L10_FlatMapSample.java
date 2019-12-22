package chapter3;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class L10_FlatMapSample {
    public static void main(String[] args) throws InterruptedException {
        // Flowable을 생성한다
        Flowable<String> flowable =
                Flowable
                        .just("A", "B", "C")
                        // 받은 데이터로 Flowable을 생성하고 이 Flowable의 데이터를 통지한다.
                        .flatMap(data -> {
                            // 1000밀리초 늦게 데이터를 통지하는 Flowable을 생성한다.
                            return Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS);
                        });

        // 구독한다.
        flowable.subscribe(data -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + ": " + data);
        });

        // 잠시 기다린다.
        Thread.sleep(20000L);
    }
}
