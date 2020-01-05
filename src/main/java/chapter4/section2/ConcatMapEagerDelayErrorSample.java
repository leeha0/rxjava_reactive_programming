package chapter4.section2;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class ConcatMapEagerDelayErrorSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.range(10, 3)
                // 받은 데이터로 Flowable을 생성해 바로 실행하나 통지는 받는 데이터 순서대로 한다.
                .concatMapEagerDelayError(
                        // 첫 번째 인자: 토지할 데이터를 가진 Flowable을 생성한다.
                        sourceData -> Flowable.interval(500L, TimeUnit.MILLISECONDS)
                                // 3건까지 통지한다.
                                .take(3)
                                // '[11] 1'을 통지할 때 예외가 발생하게 한다.
                                .doOnNext(data -> {
                                    if (sourceData == 11 && data == 1) {
                                        throw new Exception("예외 발생");
                                    }
                                })
                                // 원본 통지 데이터와 이 Flowable의 데이터를 합쳐 문자열을 생성한다.
                                .map(data -> "[" + sourceData + "] " + data),
                        // 두 번째 인자: 에러를 통지할 시점을 설정한다.
                        // 모든 데이터를 통지한 뒤에 에러를 통지
                        true);

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(4000L);
    }
}
