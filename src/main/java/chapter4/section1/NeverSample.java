package chapter4.section1;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class NeverSample {

    public static void main(String[] args) {
        // 아무것도 통지하지 않는 Flowable 생성 및 구독을 시작한다.
        Flowable.never().subscribe(new DebugSubscriber<>());
    }
}
