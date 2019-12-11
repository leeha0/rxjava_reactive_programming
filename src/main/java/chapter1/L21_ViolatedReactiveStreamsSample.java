package chapter1;

import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class L21_ViolatedReactiveStreamsSample {
    public static void main(String[] args) {
        Flowable.range(1, 3)
                // 구독한다.
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        // onSubscribe 메서드가 끝나면 onNext 메서드 실행
                        // 통지 시점에 대한 차이, request vs onSubscribe 메서드의 끝
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
