package chapter4.section2;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.core.Flowable;

public class MapSample {

    public static void main(String[] args) {
        // 인자로 받은 데이터를 순서대로 통지하는 Flowable 생성
        // map 메서드를 사용하여 소문자로 변환
        Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E").map(data -> data.toLowerCase());

        // 구독 시작
        flowable.subscribe(new DebugSubscriber<>());
    }
}
