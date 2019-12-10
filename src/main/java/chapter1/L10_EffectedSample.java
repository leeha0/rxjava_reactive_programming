package chapter1;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public class L10_EffectedSample {

    // 계산 방법을 나타내는 enum 객체
    private enum State {
        ADD, MULTIPLE
    }

    // 계산 방법
    private static State calcMethod;

    public static void main(String[] args) throws Exception {
        // 계산 방법을 덧셈으로 설정한다.
        calcMethod = State.ADD;

        // 300 밀리초마다 데이터를 통지하는 Flowable을 생성한다.
        Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
                .take(7) // 7건까지 통지한다.
                .scan((sum, data) -> { // 각 데이터를 계산한다.
                    if (calcMethod == State.ADD) {
                        return sum + data;
                    } else {
                        return sum * data;
                    }
                });

        // 구독하고 받은 데이터를 출력한다.
        flowable.subscribe(data -> System.out.println("data=" + data));

        // 잠시 기다렸다가 계산 방법을 곱셈으로 변환한다.
        Thread.sleep(1000);
        System.out.println("계산 방법 변경");
        calcMethod = State.MULTIPLE;

        // 잠시 기다린다.
        Thread.sleep(2000);
    }
}
