package chapter4.section6;

import chapter4.DebugSingleObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class ReduceSample {

    public static void main(String[] args) {
        Single<Integer> single =
                // 인자의 데이터를 통지하는 Flowable을 생성한다.
                Flowable.just(1, 10, 100, 1000, 10000)
                        .reduce(0, (sum, data) -> sum + data);

        // 구독하기
        single.subscribe(new DebugSingleObserver<>());
    }
}
