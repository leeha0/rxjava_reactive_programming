package chapter4.section2;

import chapter4.DebugSingleObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.Map;

public class ToMapSample1 {
    public static void main(String[] args) {
        Single<Map<Long, String>> single =
                // Flowable을 생성한다.
                Flowable.just("1A", "2B", "3C", "1D", "2E")
                        // 데이터로 생성한 키와 데이터 쌍을 담은 Map을 통지한다.
                        .toMap(data -> Long.valueOf(data.substring(0, 1)));

        // 구독한다.
        // 데이터만 통지하고 종료되며, 완료는 통지하지 않습니다.
        single.subscribe(new DebugSingleObserver<>());
    }
}
