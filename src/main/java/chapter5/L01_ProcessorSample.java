package chapter5;

import io.reactivex.rxjava3.processors.PublishProcessor;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class L01_ProcessorSample {
    public static void main(String[] args) {
        Processor<Integer, Integer> processor =
                PublishProcessor.create();

        // Processor를 구독한다.
        processor.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer data) {
                System.out.println(data);
            }

            @Override
            public void onError(Throwable error) {
                System.out.println("에러: " + error.getStackTrace());
            }

            @Override
            public void onComplete() {
                System.out.println("완료");
            }
        });

        // Processor에 데이터를 전달한다.
        processor.onNext(1);
        processor.onNext(2);
        processor.onNext(3);
    }
}
