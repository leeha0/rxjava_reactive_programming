package chapter3;

import chapter2.model.Counter;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class L13_CounterSample {

    public static void main(String[] args) throws InterruptedException {
        // 숫자를 세는 객체
        final Counter counter = new Counter();
        // synchronized할 경우 해결 가능

        // Counter의 increment 메서드를 10,000번 호출한다
        Flowable.range(1, 10000)
                // Flowable을 다른 스레드에서 처리하게 한다.
                .subscribeOn(Schedulers.computation())
                // 다른 스레드에서 처리 작업을 하게 한다.
                .observeOn(Schedulers.computation())
                // 구독한다.
                .subscribe(
                        // 데이터를 받을 때의 처리
                        data -> counter.syncIncrement(),
                        // 에러를 받을 때의 처리
                        error -> System.out.println("에러= " + error),
                        // 완료 통지를 받을 때의 처리
                        () -> System.out.println("counter.get()=" + counter.get())
                );

        // 다른 스레드를 동시에 실행한다.
        Flowable.range(1, 10000)
                // Flowable을 다른 스레드에서 치라하게 한다.
                .subscribeOn(Schedulers.computation())
                // 다른 스레드에서 처리 작업을 하게 한다.
                .observeOn(Schedulers.computation())
                // 구독한다.
                .subscribe(
                        // 데이터를 받을 때의 처리
                        data -> counter.syncIncrement(),
                        // 에러를 받을 때의 처리
                        error -> System.out.println("에러= " + error),
                        // 완료 통지를 받을 때의 처리
                        () -> System.out.println("counter.get()=" + counter.get())
                );

        // 잠시 기다린다.
        Thread.sleep(1000L);
    }
}
