package chapter4;

import io.reactivex.rxjava3.observers.DisposableSingleObserver;

public class DebugSingleObserver<T> extends DisposableSingleObserver<T> {

    private String label;

    public DebugSingleObserver() {
        super();
    }

    public DebugSingleObserver(String label) {
        super();
        this.label = label;
    }

    @Override
    public void onSuccess(T data) {
        // onSuccess 메서드 호출 시 출력한다.
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + ": " + data);
        } else {
            System.out.println(threadName + ": " + label + ": " + data);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        // onError 메서드 호출 시 출력한다.
        String threadName = Thread.currentThread().getName();
        if (label == null) {
            System.out.println(threadName + ": 에러 = " + throwable);
        } else {
            System.out.println(threadName + ": " + label + ": 에러 = " + throwable);
        }
    }
}
