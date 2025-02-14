package chapter4.section1;

import io.reactivex.rxjava3.core.Flowable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class IntervalSample {

    public static void main(String[] args) throws InterruptedException {
        // "분:초.밀리초" 문자열로 변환하는 Formatter
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss.SSS");

        // 1000밀리초마다 숫자를 통지하는 Flowable을 생성한다.
        Flowable<Long> flowable = Flowable.interval(1000L, TimeUnit.MILLISECONDS);

        // 처리 작업을 시작하기 전 시각
        System.out.println("시작 시간: " + LocalTime.now().format(formatter));

        // 구독한다.
        flowable.subscribe(data -> {
            // 스레드 이름을 가져온다.
            String threadName = Thread.currentThread().getName();
            // 현재 시각의 "분:초.밀리초"를 가져온다.
            String time = LocalTime.now().format(formatter);
            // 출력한다.
            System.out.println(threadName + ": " + time + ": data=" + data);
        });

        // 잠시 기다린다.
        Thread.sleep(5000L);
    }
}
