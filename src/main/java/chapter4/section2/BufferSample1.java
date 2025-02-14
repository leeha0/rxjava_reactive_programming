package chapter4.section2;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BufferSample1 {
    public static void main(String[] args) throws InterruptedException {
        // 100밀리초마다 숫자를 통지한느 Flowable을 생성한다.
        Flowable<List<Long>> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
                // 10건까지 통지한다.
                .take(10)
                // 3건씩 모아 통지한다.
                .buffer(3);

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(3000L);
    }
}
