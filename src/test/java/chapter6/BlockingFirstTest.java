package chapter6;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class BlockingFirstTest {

    @Test
    public void runBlockingFirstTest() {
        long actual =
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        .blockingFirst();

        assertThat(actual, is(0L));
    }
}
