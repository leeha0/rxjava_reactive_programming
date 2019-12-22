package chapter3;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.ResourceSubscriber;

public class L06_MainThreadSample {
    public static void main(String[] args) {
        System.out.println("start");

        Flowable.just(1, 2, 3)
                // 구독한다.
                .subscribe(new ResourceSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer data) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + data);
                    }

                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": 완료");
                    }
                });

        System.out.println("end");
    }
}
