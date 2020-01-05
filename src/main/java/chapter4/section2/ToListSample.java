package chapter4.section2;

import chapter4.DebugSingleObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public class ToListSample {
    public static void main(String[] args) {
        Single<List<String>> single =
                // Flowable을 생성한다.
                Flowable.just("A", "B", "C", "D", "E")
                        // 전체 데이터를 담은 리스트를 통지한다.
                        .toList();

        // 구독한다.
        // 데이터만 통지하고 종료되며, 완료는 통지하지 않습니다.
        single.subscribe(new DebugSingleObserver<>());
    }
}
