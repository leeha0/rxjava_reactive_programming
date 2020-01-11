# Chapter5
### 5.1 Processor/Subject
#### 5.1.1 Processor/Subject 란
* 생성자를 나타내는 Publisher 인터페이스와 소비자를 나타내는 Subscriber 인터페이스를 모두 상속하는 인터페이스
* 생성자와 소비자 사이에서 통지의 중개 역할을 수행
* Publisher가 통지하는 데이터를 onNext 메서드를 통해 Processor에 전달하면 Processor를 구독하는 Subscriber에게 데이터 통지
* 전달 받은 데이터의 타입과 통지하는 데이터 타입이 동일 

#### 5.1.2 Processor/Subject의 종류
* 통지 데이터의 캐시 방법에 따라 5가지 클래스 제공

| 클래스 | 설명 |
| :-----: | :----- |
| PublishProcessor/PublishSubject | Processor/Subject가 데이터를 받는 시점에 데이터 통지 |
| BehaviorProcessor/BehaviorSubject | Processor/Subject가 마지막으로 받은 데이터를 캐시히고 구독 시점에 캐시한 데이터를 통지 |
| ReplayProcessor/ReplaySubject | Processor/Subject가 받은 모든 데이터를 캐시하고 구독 시점에 캐시한 데이터를 통지 |
| AsyncProcessor/AsyncSubject | Processor/Subject가 완료 통지를 받았을 때 마지막으로 받은 데이터만 통지 |
| UnicastProcessor/UnicastSubject | 하나의 소비자만 구독 |

##### FlowableProcessor/Subject 메서드
* Flowable/Observable을 상속
* 통지 메서드 구현은 상속한 클래스인 Flowable/Observable에 구현

| 메서드 | 설명 |
| :-----: | :----- |
| hasComplete() | 완료가 통지되면 true 반환 |
| hasThrowable() | 에러가 통지되면 true 반환 |
| getThrowable() | 에러가 통지되면 에러 객체 반환, 그렇지 않다면 null 반환 |
| hasSubscribers()/hasObservers() | 구독 중인 Subscriber/Observer가 있으면 true 반환, 완료나 에러 통지 후에는 false 반환 |

##### toSerialized 메서드
* 하나의 Processor/Subject는 서로 다른 스레드에서 동시에 통지하는 것을 허용하지 않음
* 스레드 안전을 위해 SerializedProcessor/SerializedSubject 클래스 제공
* 패키지 프라이빗하여 직접 접근할 수 없으며 toSerialized() 메서드를 통해 생성
* 통지량이 많은 상황에서는 성능에 영향 

### 5.2 PublishProcessor/PublishSubject
* 구독한 뒤로 받은 데이터만 통지
* 데이터 통지가 완료된 시점에 구독하면 에러나 완료를 통지

### 5.3 BehaviorProcessor/BehaviorSubject
* 마지막으로 통지한 데이터를 캐시하고 구독 시 캐시된 데이터를 통지
* 캐시 된 데이터를 먼저 받고 그 이후에는 통지 시점에 데이터를 통지
* 데이터 통지가 완료된 시점에 구독하면 에러나 완료를 통지

### 5.4 ReplayProcessor/ReplaySubject
* 통지한 데이터를 모두 또는 지정한 범위까지 캐시하고 구독 시점에 캐시된 데이터를 통지
* 통지할 데이터가 증가함에 따라 메모리 사용량 증가하므로 캐시 범위 설정 필요

### 5.5 AsyncProcessor/AsyncSubject
* 데이터 통지가 완료된 시점에 마지막으로 통지한 데이터와 완료만 통지

### 5.6 UnicastProcessor/UnicastSubject
* 1개의 소비자(Subscriber/Observer)만 구독할 수 있음
* 다른 소비자가 구독한다면 IllegalStateException 에러 통지
* 통지한 데이터는 캐시되며 소비자가 구독한 시점에 캐시된 데이터가 통지
* 모든 데이터를 캐시하거나 캐시 크기 설정 가능

```java 
// 구독 해지가 호출 될 떄 실행하는 처리
new Runnable() {
    @Override
    public void run() {
        System.out.println("Cancel");
    }
}
```