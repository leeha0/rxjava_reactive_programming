package chapter4;

import io.reactivex.rxjava3.core.Flowable;

public class RangeSample {

    public static void main(String[] args) {
        // 10 부터 순서대로 데이터를 3건까지 통지하는 Flowable을 생성한다.
        Flowable<Integer> flowable = Flowable.range(10, 3);

        // 구독을 시작한다.
        flowable.subscribe(new DebugSubscriber<>());
    }
}
