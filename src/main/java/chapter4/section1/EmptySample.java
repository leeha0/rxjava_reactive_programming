package chapter4.section1;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class EmptySample {

    public static void main(String[] args) {
        // 빈 Flowable을 생성 및 구독한다.
//        Flowable.empty().subscribe(new DebugSubscriber<>());

        Flowable.just(1, 2, 3, 0, 2, 3)
                .flatMap(data -> {
                    try {
                        return Flowable.just(100 / data);
                    } catch (ArithmeticException error) {
                        return Flowable.empty();
                    }
                }).subscribe(new DebugSubscriber<>());
    }
}
