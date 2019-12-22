package chapter3;

import chapter2.model.Counter;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class L15_CounterWithMergeSample {

    public static void main(String[] args) throws InterruptedException {
        // 순차적으로 값을 증가하게 하는 Counter 객체
        final Counter counter = new Counter();

        // Counter의 increment 메서드를 10,000번 호출한다.
        Flowable<Integer> source1 = Flowable.range(1, 10000)
                // Flowable의 다른 스레드에서 처리 작업을 하게 한다.
                .subscribeOn(Schedulers.computation())
                // 다른 스레드에서 처리 작업을 하게 한다.
                .observeOn(Schedulers.computation());

        // Counter의 increment 메서드를 10,000번 호출한다.(다른 스레드에서 동시에 실행)
        Flowable<Integer> source2 = Flowable.range(1, 10000)
                // Flowable이 다른 스레드에서 처리 작업을 하게 한다.
                .subscribeOn(Schedulers.computation())
                // 다른 스레드에서 처리 작업을 하게 한다.
                .observeOn(Schedulers.computation());

        // 두 Flowable을 합친다.
        Flowable.merge(source1, source2)
                // 구독한다.
                .subscribe(
                        // 데이터를 받을 때의 처리
                        data -> counter.increment(),
                        // 에러를 받을 때의 처리
                        error -> System.out.println("에러=" + error),
                        // 완료 통지를 받을 때의 처리
                        () -> System.out.println("Counter.get()=" + counter.get())
                );

        // 잠시 기다린다.
        Thread.sleep(1000L);
    }
}
