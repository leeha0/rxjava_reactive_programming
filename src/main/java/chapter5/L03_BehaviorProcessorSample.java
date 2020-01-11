package chapter5;

import chapter4.DebugSubscriber;
import io.reactivex.rxjava3.processors.BehaviorProcessor;

public class L03_BehaviorProcessorSample {
    public static void main(String[] args) {
        BehaviorProcessor<Integer> processor = BehaviorProcessor.create();

        // Processor를 생성한다.
        processor.subscribe(new DebugSubscriber<>("No.1"));

        // 데이터를 통지한다.
        processor.onNext(1);
        processor.onNext(2);
        processor.onNext(3);

        // 다른 Subscriber도 구독하게 한다.
        System.out.println("Subscriber No.2 추가");
        processor.subscribe(new DebugSubscriber<>("--- No.2"));

        // 데이터를 통지한다.
        processor.onNext(4);
        processor.onNext(5);

        // 완료를 통지한다.
        processor.onComplete();

        // 완료 후에도 다른 Subscriber가 구독한다.
        System.out.println("Subscriber No.3 추가");
        processor.subscribe(new DebugSubscriber<>("------ No.3"));
    }
}
