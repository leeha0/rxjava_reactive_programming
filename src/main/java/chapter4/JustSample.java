package chapter4;

import io.reactivex.rxjava3.core.Flowable;

public class JustSample {
    public static void main(String[] args) {
        // 인자 데이터를 순서대로 통지하는 Flowable을 생성한다.
        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E");

        // 구독을 시작한다.
        flowable.subscribe(new DebugSubscriber<>());
    }
}
