package chapter3;

import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class L11_ConcatMapSample {
    public static void main(String[] args) throws InterruptedException {
        // Flowable을 생성한다.
        Flowable<String> flowable =
                // 받은 데이터로 Flowable을 생성하고 이 Flowable이 가진 데이터를 통지한다.
                Flowable
                        .just("A", "B", "C")
                        .concatMap(data -> {
                            // 1000밀리초 늦게 데이터를 통지하는 Flowable을 생성하다.
                            return Flowable.just(data).delay(1000L, TimeUnit.MILLISECONDS);
                        });

        // 구독한다.
        flowable.subscribe(data -> {
            String threadName = Thread.currentThread().getName();
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("ss.SSS"));
            System.out.println(threadName + ": data=" + data + ", time=" + time);
        });

        // 잠시 기다린다.
        Thread.sleep(4000L);
    }
}
