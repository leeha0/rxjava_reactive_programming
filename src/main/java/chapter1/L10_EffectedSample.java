package chapter1;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class L10_EffectedSample {

    private enum State {
        ADD, MULTIPLE
    }

    private static State calcMethod;

    public static void main(String[] args) throws Exception {
        calcMethod = State.ADD;

        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(7)
                .scan((sum, data) -> {
                    if (calcMethod == State.ADD) {
                        return sum + data;
                    } else {
                        return sum * data;
                    }
                });

        flowable.subscribe(data -> System.out.println("data=" + data));

        Thread.sleep(1000);
        System.out.println("계산 방법 변경");
        calcMethod = State.MULTIPLE;

        Thread.sleep(2000);
    }
}
