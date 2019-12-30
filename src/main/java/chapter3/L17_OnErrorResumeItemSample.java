package chapter3;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

public class L17_OnErrorResumeItemSample {
    public static void main(String[] args) {
        Flowable.just(1, 3, 5, 0, 2, 4)
                // 받은 데이터로 100을 나눈다.
                .map(data -> 100 / data)
                // 에러가 발생하면 0을 통지한다.
                .onErrorReturnItem(0)
                // 구독한다.
                .subscribe(new DisposableSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer data) {
                        System.out.println("data=" + data);
                    }

                    @Override
                    public void onError(Throwable error) {
                        System.out.println("error=" + error);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("완료");
                    }
                });
    }
}
