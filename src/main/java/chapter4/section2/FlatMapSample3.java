package chapter4.section2;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class FlatMapSample3 {
    public static void main(String[] args) {
        // 에러가 발생할 Flowable
        Flowable<Integer> original = Flowable.just(1, 2, 0, 4, 5) // 1, 2, 4, 5
                // 0이 들어오면 예외가 발생한다.
                .map(data -> 10/data);

        // 일반 통지 시, 에러 발생 시, 완료 시 각각 설정한 데이터로 변환한다.
        Flowable<Integer> flowable = original.flatMap(
                // 일반 통지 시 데이터
                data -> Flowable.just(data),
                // 에러 발생 시 데이터
                error -> Flowable.just(-1),
                // 완료 시 데이터
                () -> Flowable.just(100)
        );

        // 구독 시작
        flowable.subscribe(new DebugSubscriber<>());
    }
}
