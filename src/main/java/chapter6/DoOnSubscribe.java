package chapter6;

import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DoOnSubscribe {
    public static void main(String[] args) {
        Flowable.range(1, 5)
                .doOnSubscribe(subscription -> System.out.println("[doOnSubscribe]"))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("--- Subscriber : onSubscribe");
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("--- Subscribe : onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("--- Subscribe : onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("--- Subscribe : onComplete");
                    }
                });
    }
}
