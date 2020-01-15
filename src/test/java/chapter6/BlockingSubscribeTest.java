package chapter6;

import chapter2.model.Counter;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class BlockingSubscribeTest {

    @Test
    public void runBlockingSubscribeTest() {
        Flowable<Long> flowable =
                Flowable.interval(100L, TimeUnit.MILLISECONDS)
                        .take(5);

        Counter counter = new Counter();
        // 현재 스레드로 구독
        flowable.blockingSubscribe(new DisposableSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " - " + aLong);
                counter.increment();
            }

            @Override
            public void onError(Throwable error) {
                fail(error.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        assertThat(counter.get(), is(5));
    }
}
