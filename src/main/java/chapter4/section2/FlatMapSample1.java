package chapter4.section2;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;

public class FlatMapSample1 {
    public static void main(String[] args) {
        // 인자의 데이터를 통지하는 FLowable을 생성한다.
        Flowable<String> flowable = Flowable.just("A", "", "B", "", "C")
                // flatMap 메서드로 빈 문자를 제거하거나 소문자로 변환한다,
                .flatMap(data -> {
                    if ("".equals(data)) {
                        // 빈 문자라면 빈 Flowable을 반환한다.
                        return Flowable.empty();
                    } else {
                        // 소문자로 변환한 데이터가 담긴 Flowable을 반한한다.
                        return Flowable.just(data.toLowerCase());
                    }
                });

        // 구독 시작
        flowable.subscribe(new DebugSubscriber<>());
    }
}
