package chapter4;

import io.reactivex.rxjava3.core.Flowable;

public class FromArraySample {
    public static void main(String[] args) {
        // 배열 데이터를 순서대로 통지하는 Flowable을 생성한다.
        Flowable<String> flowable = Flowable.fromArray("A", "B", "C", "D", "E");

        // 구독을 시작한다.
        flowable.subscribe(new DebugSubscriber<>());
    }
}
