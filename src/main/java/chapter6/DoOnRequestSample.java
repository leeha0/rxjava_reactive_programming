package chapter6;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DoOnRequestSample {
    public static void main(String[] args) throws InterruptedException {
        // 128 생성, 96 통지
        // 128 생성 및 128 통지가 이루어질 경우 새로운 데이터를 준비 하는데 딜레이 발생
        // 처리량을 높이기 위해 96개 생성
        Flowable.range(1, 196)
                .doOnRequest(size -> System.out.println("기존 데이터: size=" + size)) // Default
                .observeOn(Schedulers.computation())
                .doOnRequest(size -> System.out.println("--- observeOn 적용 후: size=" + size))
                .subscribe(new Subscriber<Integer>() {
                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                        this.subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("에러: " + t);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("완료");
                    }
                });

        // observeOn 을 통해 별도 스레드에서 동작
        Thread.sleep(500L);
    }
}
