package chapter6;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class BlockingSubscribeVersusSubscribe {
    public static void main(String[] args) throws InterruptedException {
//        runBlockingSubscribe();
        runSubscribe();
    }

    public static void runSubscribe() throws InterruptedException {
        System.out.println("Before subscribe");
        System.out.println("Before Thread: " + Thread.currentThread().getName());

        Flowable.timer(1000L, TimeUnit.MILLISECONDS, Schedulers.io())
                .concatWith(Flowable.timer(1000L, TimeUnit.MILLISECONDS, Schedulers.single()))
                .subscribe(t -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    System.out.println("Value:  " + t);
                });


        System.out.println("After subscribe");
        System.out.println("After Thread: " + Thread.currentThread().getName());

        // RxJava uses daemon threads, without this, the app would quit immediately
        Thread.sleep(3000);

        System.out.println("Done");
    }

    public static void runBlockingSubscribe() {
        System.out.println("Before blockingSubscribe");
        System.out.println("Before Thread: " + Thread.currentThread().getName());

        Flowable.interval(100L, TimeUnit.MILLISECONDS)
                .take(5)
                .blockingSubscribe(t -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    System.out.println("Value:  " + t);
                });

        System.out.println("After blockingSubscribe");
        System.out.println("After Thread: " + Thread.currentThread().getName());
    }
}
