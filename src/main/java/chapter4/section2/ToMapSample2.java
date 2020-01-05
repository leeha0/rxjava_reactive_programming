package chapter4.section2;

import chapter4.DebugSingleObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.Map;

public class ToMapSample2 {
    public static void main(String[] args) {
        Single<Map<Long, String>> single =
                // Flowable을 생성한다.
                Flowable.just("1A", "2B", "3C", "1D", "2E")
                        // 데이터에서 생성한 키와 값의 쌍이 담긴 Map을 통지한다.
                        .toMap(
                                // 첫 번째 인자 : 키 생성
                                data -> Long.valueOf(data.substring(0, 1)),
                                // 두 번째 인자 : 값 생성
                                data -> data.substring(1)
                        );

        // 구독한다.
        single.subscribe(new DebugSingleObserver<>());
    }
}
