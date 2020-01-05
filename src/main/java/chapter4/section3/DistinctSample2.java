package chapter4.section3;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class DistinctSample2 {
    public static void main(String[] args) {
        Flowable<String> flowable =
                // Flowable을 생성한다.
                Flowable.just("A", "a", "B", "b", "A", "a", "B", "b")
                        // 대소문자를 관계없이 같이 데이터를 제외하고 통지한다.
                        .distinct(data -> data.toLowerCase());

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());
    }
}
