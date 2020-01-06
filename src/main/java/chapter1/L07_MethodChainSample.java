package chapter1;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class L07_MethodChainSample {
    public static void main(String[] args) {
        // 인자의 데이터를 순서대로 통지하는 Flowable을 생성한다.
        Flowable<Integer> flowable =
                Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                        .filter(data -> data % 2 == 0) // 짝수에 해당하는 데이터만 통지한다.
                        .map(data -> data * 100); // 데이터를 100배로 변환한다.

        // 구독해서 받은 데이터를 출력한다.
        flowable.subscribe(data -> System.out.println("data=" + data));

        // Obserable로 구현
        Observable<Integer> observable =
                Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                        .filter(data -> data % 2 == 0)
                        .map(data -> data * 100);

        observable.subscribe(data -> System.out.println("data=" + data));
    }
}
