package chapter3;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class L16_RetrySample {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.<Integer>create(emitter -> {
            // Flowable 처리를 시작한다.
            System.out.println("Flowable 처리 시작");

            // 통지 처리
            for (int i = 1; i <= 3; i++) {
                // 데이터가 2일 때 에러가 발생하게 한다.
                if (i == 2) {
                    throw new Exception("예외 발생");
                }

                // 데이터를 통지한다.
                emitter.onNext(i);
            }

            // 완료를 통지한다.
            emitter.onComplete();

            // Flowable 처리를 완료한다.
            System.out.println("Flowable 처리 완료");
        }, BackpressureStrategy.BUFFER)
                // doOnSubscribe를 실행한다.
                // 구독을 시작할 때 메세지 출력
                .doOnSubscribe(
                        subscription -> {
                            System.out.println("flowable: doOnSubscribe");
                        }
                )
                // 에러가 발생하면 두 번까지 재시도 한다.
                // 처리 작업을 처음부터 다시 시작
                .retry(2);

        // 구독한다.
        flowable.subscribe(new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("subscriber: onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer data) {
                System.out.println("data=" + data);
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
    }
}
