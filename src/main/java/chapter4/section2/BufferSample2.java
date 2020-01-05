package chapter4.section2;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Supplier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BufferSample2 {
    public static void main(String[] args) throws InterruptedException {
        Flowable<List<Long>> flowable =
                // 300밀리초마다 숫자를 통지하는 Flowable 생성
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        // 7건까지 통지한다.
                        .take(7)
                        // 버퍼 구간을 나누는 Flowable 생성한다.
                        .buffer(() -> Flowable.timer(1000L, TimeUnit.MILLISECONDS));

        flowable.subscribe(new DebugSubscriber<>());

        Thread.sleep(4000L);
    }
}
