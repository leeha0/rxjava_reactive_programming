package chapter1;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class L32_CompletableSample {

    public static void main(String[] args) throws Exception {

        Completable completable = Completable.create(emitter -> {
            // 업무 로직 처리

            // 완료 통지
            emitter.onComplete();
        });

        completable
                // Completable을 비동기로 실행
                .subscribeOn(Schedulers.computation())
                // 구독
                .subscribe(new CompletableObserver() {

                    // 구독 준비가 됐을 때의 처리
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        // 아무 것도 하지 않는다.
                    }

                    // 완료 통지를 받을 때의 처리
                    @Override
                    public void onComplete() {
                        System.out.println("완료");
                    }

                    // 에러 통지를 받을 떄의 처리
                    @Override
                    public void onError(Throwable error) {
                        System.out.println("에러=" + error);
                    }
                });

        // 잠시 기다린다.
        Thread.sleep(100L);
    }
}
