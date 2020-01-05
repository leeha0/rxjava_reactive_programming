package chapter4.section3;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class DistinctSample1 {
    public static void main(String[] args) {
        Flowable<String> flowable =
                // Flowable을 생성한다.
                Flowable.just("A", "B", "C", "b", "A", "a", "B", "b")
                        // 같은 데이터를 제외하고 통지한다.
                        .distinct();

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());
    }
}
