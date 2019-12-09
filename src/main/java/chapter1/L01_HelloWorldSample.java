package chapter1;

import io.reactivex.rxjava3.core.Flowable;

public class L01_HelloWorldSample {
    public static void main(String[] args) {
        // 데이터를 통지하는 생성자 생성한다
        Flowable<String> flowable = Flowable.just("Hello", "World");
        // 통지받은 데이터를 출력한
        flowable.subscribe(data -> System.out.println(data));
    }
}
