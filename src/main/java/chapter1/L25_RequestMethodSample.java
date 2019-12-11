package chapter1;

import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class L25_RequestMethodSample {

    public static void main(String[] args) {
        Flowable
                // 인자 데이터를 통지한다.
                .just(1, 2, 3)
                // 구독한다.
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("onSubscribe: start");
                        subscription.request(Long.MAX_VALUE);
                        System.out.println("onSubscribe: end");
                    }

                    @Override
                    public void onNext(Integer data) {
                        System.out.println(data);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.out.println("에러=" + error);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("완료");
                    }
                });
    }
}
