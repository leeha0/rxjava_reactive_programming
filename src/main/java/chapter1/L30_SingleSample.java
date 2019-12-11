package chapter1;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class L30_SingleSample {

    public static void main(String[] args) {
        // Single 생성
        Single<DayOfWeek> single = Single.create(emitter -> {
            emitter.onSuccess(LocalDate.now().getDayOfWeek());
        });

        // 구독
        single.subscribe(new SingleObserver<DayOfWeek>() {

            // 구독 준비가 됐을 때의 처리
            @Override
            public void onSubscribe(Disposable d) {
                // 아무것도 하지 않는다.
            }

            // 데이터 통지를 받았을 때의 처리
            @Override
            public void onSuccess(DayOfWeek value) {
                System.out.println(value);
            }

            // 에러 통지를 받았을 때의 처리
            @Override
            public void onError(Throwable error) {
                System.out.println("에러=" + error);
            }
        });
    }
}
