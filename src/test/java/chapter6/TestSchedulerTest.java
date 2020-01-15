package chapter6;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestSchedulerTest {

    @Test
    public void runTestSchedulerTest() {
        long start = System.currentTimeMillis();
        TestScheduler testScheduler = new TestScheduler();

        // 테스트 대상 Flowable 생성
        Flowable<Long> flowable =
                Flowable.interval(500L, TimeUnit.MILLISECONDS, testScheduler);

        // 구독 시작
        TestSubscriber<Long> result = flowable.test();

        System.out.println("data=" + result.values());
        result.assertEmpty();

        testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);
        System.out.println("data=" + result.values());
        result.assertValues(0L);

        testScheduler.advanceTimeBy(500L, TimeUnit.MILLISECONDS);
        System.out.println("data=" + result.values());
        result.assertValues(0L, 1L);

        testScheduler.advanceTimeTo(2000L, TimeUnit.MILLISECONDS);
        System.out.println("data=" + result.values());
        result.assertValues(0L, 1L, 2L, 3L);

        System.out.println("testScheduler#now=" + testScheduler.now(TimeUnit.MILLISECONDS));

        long totalTime = System.currentTimeMillis() - start;
        System.out.println("테스트에 걸린 시간=" + totalTime);
    }
}
