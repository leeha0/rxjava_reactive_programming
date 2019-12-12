package chapter2;

import io.reactivex.rxjava3.functions.Action;

public class DifferenceOfThisSample {
    public static void main(String[] args) throws Throwable {
        DifferenceOfThisSample target = new DifferenceOfThisSample();
        target.execute();
    }

    // 익명 클래스와 람다식의 this 출력
    public void execute() throws Throwable {
        // 익명 클래스
        Action anonymous = new Action() {

            @Override
            public void run() {
                System.out.println("익명 클래스의 this: " + this);
            }
        };

        // 람다식
        Action lambda = () -> System.out.println("람다식의 this: " + this);

        // 각각 실행
        anonymous.run();
        lambda.run();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
