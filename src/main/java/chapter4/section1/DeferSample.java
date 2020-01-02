package chapter4.section1;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalTime;
import java.util.concurrent.Callable;

public class DeferSample {

    public static void main(String[] args) throws InterruptedException {
//        Flowable<LocalTime> flowable = Flowable.defer(() -> Flowable.just(LocalTime.now()));
        Flowable<LocalTime> flowable = Flowable.just(LocalTime.now());
//        new Callable<Flowable<LocalTime>>() {
//
//            @Override
//            public Flowable<LocalTime> call() throws Exception {
//                return Flowable.just(LocalTime.now());
//            }
//        };

        // 구독한다.
        flowable.subscribe(new DebugSubscriber<>());

        // 잠시 기다린다.
        Thread.sleep(2000L);

        // 다시 구독한다.
        flowable.subscribe(new DebugSubscriber<>("No. 2"));
    }
}
