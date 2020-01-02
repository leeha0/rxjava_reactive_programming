package chapter4.section1;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.Callable;

public class FromCallableSample {
    public static void main(String[] args) {

        // Callable의 결과를 통지한느 Flowable을 생성한다.
        Flowable<Long> flowable = Flowable.fromCallable(() -> System.currentTimeMillis());

        // 호출되면 현재 시각을 반환한다.
//        new Callable<Long>() {
//
//            @Override
//            public Long call() throws Exception {
//                return System.currentTimeMillis();
//            }
//        };

        // 구독을 시작한다.
        flowable.subscribe(new DebugSubscriber<>());
    }
}
