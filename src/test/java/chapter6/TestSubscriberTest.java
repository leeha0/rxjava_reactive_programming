package chapter6;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestSubscriberTest {

    @Test
    public void runTestSubscriberTest() throws InterruptedException {
        Flowable<Long> target = Flowable.interval(100L, TimeUnit.MILLISECONDS);

        // TestSubscriber 생성 및 Flowable 처리 시작
        TestSubscriber<Long> testSubscriber = target.test();

        // 아직 데이터가 통지되지 않았는지 확인
        testSubscriber.assertEmpty();

        // 대기
        testSubscriber.await(150L, TimeUnit.MILLISECONDS);

        // 통지 데이터 확인
        testSubscriber.assertValues(0L);

        // 대기
        testSubscriber.await(100L, TimeUnit.MILLISECONDS);

        // 통지 데이터 확인
        testSubscriber.assertValues(0L, 1L);
    }

    @Test
    public void runAssertTest() {
        Flowable.empty()
                // TestSubscriber 생성
                .test()
                // 데이터를 받지 않으면 성공
                .assertNoValues()
//                .assertValues(0L)
                // 에러가 없으면 성공
                .assertNoErrors()
                // 완료했으면 성공
                .assertComplete();
    }
}
