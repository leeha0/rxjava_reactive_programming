package chapter6;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class DoOnErrorSample {
    public static void main(String[] args) {
        Flowable.range(1, 5)
                .doOnError(error -> System.out.println("[doOnError] 기존 데이터: " + error.getMessage()))
                .map(data -> {
                    if (data == 3) {
                        throw new Exception("예외 발생");
                    } else {
                        return data;
                    }
                })
                .doOnError(error -> System.out.println("[doOnError] map 적용 후: " + error.getMessage()))
                .subscribe(new DebugSubscriber<>());
    }
}
