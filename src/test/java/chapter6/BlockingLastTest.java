package chapter6;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class BlockingLastTest {

    @Test
    public void runBlockingLastTest() {
        long actual =
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        .take(3)
                        .blockingLast();

        assertThat(actual, is(2L));
    }
}
