package chapter1;

import io.reactivex.rxjava3.core.Flowable;

public class L07_MethodChainSample {
    public static void main(String[] args) {
        Flowable<Integer> flowable =
                Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                    .filter(data -> data % 2 == 0)
                    .map(data -> data * 100);

        flowable.subscribe(data -> System.out.println("data=" + data));
    }
}
