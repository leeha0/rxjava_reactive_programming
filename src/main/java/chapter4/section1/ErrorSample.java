package chapter4.section1;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class ErrorSample {

    public static void main(String[] args) {
        // 에러를 통지하는 Flowable 생성 및 구독을 시작한다.
        Flowable.error(new Exception("에외 발생")).subscribe(new DebugSubscriber<>());
    }
}
