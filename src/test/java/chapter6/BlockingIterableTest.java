package chapter6;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class BlockingIterableTest {

    @Test
    public void runBlockingIterableTest() throws InterruptedException {
        Iterable<Long> result =
                Flowable.interval(300L, TimeUnit.MILLISECONDS)
                        .take(5)
                        // Iterable 변환 및 메인 스레드에서 얻음
                        .blockingIterable();

        Iterator<Long> iterator = result.iterator();

        // 데이터 유무 확인
        assertTrue(iterator.hasNext());

        assertThat(iterator.next(), is(0L));
        assertThat(iterator.next(), is(1L));
        assertThat(iterator.next(), is(2L));

        Thread.sleep(1000L);

        assertThat(iterator.next(), is(3L));
        assertThat(iterator.next(), is(4L));

        assertFalse(iterator.hasNext());
    }
}
