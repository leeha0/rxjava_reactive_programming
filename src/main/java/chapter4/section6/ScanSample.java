package chapter4.section6;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class ScanSample {
    public static void main(String[] args) {
        Flowable<Integer> flowable =
                // 인자의 데이터를 통지하는 Flowable을 생성한다.
                Flowable.just(1, 10, 100, 1000, 10000)
                        // scan 메서드로 받은 데이터를 더한다.
                        .scan(0, (sum, data) -> sum + data);

        // 구독을 시작한다.
        flowable.subscribe(new DebugSubscriber<>());
    }
}
