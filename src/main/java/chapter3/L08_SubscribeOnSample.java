package chapter3;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class L08_SubscribeOnSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.just(1, 2, 3, 4, 5) // Flowable 설정
                .subscribeOn(Schedulers.computation()) // RxComputationThreadPool
                .subscribeOn(Schedulers.io()) // RxCachedThreadScheduler
                .subscribeOn(Schedulers.single()) // RxSingleScheduler
                .subscribe(data -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + ": " + data);
                });

        // 잠시 기다린다
        Thread.sleep(500);
    }
}
