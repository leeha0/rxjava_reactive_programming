package chapter3;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

public class L22_MissingBackpressureFlowableSample {
    public static void main(String[] args) throws InterruptedException {
        Flowable<Long> flowable = Flowable.interval(10L, TimeUnit.MILLISECONDS)
                // 통지 시 정보를 출력한다.
                .doOnNext(value -> System.out.println("emit: " + value));

        flowable
                // 각 스레드에서 데이터를 받는다.
                .observeOn(Schedulers.computation())
                // 구독한다.
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        // 무제한으로 데이터를 통지한다.
                        subscription.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long value) {
                        // 1000밀리초를 기다린 뒤 처리한다.
                        try {
                            System.out.println("waiting......");
                            Thread.sleep(1000L);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("received: " + value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.out.println("에러=" + error);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("종료");
                    }
                });

        // 잠시 기다린다.
        Thread.sleep(5000L);
    }
}
