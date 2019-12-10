package chapter1;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Consumer;

public class L01_HelloWorldSample {
    public static void main(String[] args) {
        // 데이터를 통지하는 생성자 생성한다
        // Publisher 구현체
        Flowable<String> flowable = Flowable.just("Hello", "World");

        // 통지받은 데이터를 출력한다.
        // Consumer (Default 메서드를 제외하고 하나의 메서드만 보유)
        flowable.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                System.out.println(s);
            }
        });

        // 람다표현식
        flowable.subscribe(data -> System.out.println(data));

        // 메서드참조
        flowable.subscribe(System.out::println);
    }
}
