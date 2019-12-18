package chapter3;

import io.reactivex.rxjava3.core.Flowable;

import java.util.Arrays;
import java.util.List;

public class L03_FlowableSample {

    public static void main(String[] args) {
        // 리스트로 Flowable을 생성한다.
        List<String> list = Arrays.asList("a", "b", "c");
        Flowable<String> flowable = Flowable.fromIterable(list);

        // 처리를 시작한다.
        flowable.subscribe(System.out::println);
    }
}
