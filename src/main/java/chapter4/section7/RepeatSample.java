package chapter4.section7;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class RepeatSample {
    public static void main(String[] args) {
        Flowable<String> flowable =
                // Flowable을 생성한다.
                Flowable.just("A", "B", "C")
                        // 통지를 반복한다.
//                        .repeat(0);
                        .repeat(2);

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());
    }
}
