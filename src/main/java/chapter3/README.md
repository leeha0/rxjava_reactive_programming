# Chapter3
* RxJava의 아키텍처는 옵저버(Observer) 패턴과 이터레이터(Iterator) 패턴의 영향을 받음

### 3.1 RxJava와 디자인 패턴
#### 3.1.1 옵저버 패턴
* GoF가 처음 제시한 디자인 패턴
* 관찰 대상 객체의 상태에 변화가 발생하면 객체를 관찰하는 객체가 변화에 따라 일련의 처리를 작업

| 클래스 | 설명 |
| :----- | :----- |
| Subject | 관찰 대상 클래스 (`생산자`) |
| Observer | 관찰자 인터페이스 (`소비자`) |
| ConcreteSubject | Subject를 상속한 클래스, 실제 관찰 대상 객체 |
| ConcreteObserver | Observer를 구현한 클래스, 실제 변화에 따른 처리 내용 구현 |

#### 3.1.2 이터레이터 패턴
* GoF가 처음 제시한 디자인 패턴
* 데이터 집합체(aggregate)에서 순서대로 데이터를 꺼내기 위한 패턴

* RxJava 비교
  * 차이점
    * Pull vs Push
      * 이터레이터 패턴 : 소비자가 데이터를 가져가는 Pull 방식
      * RxJava : 소비자에게 데이터를 통지하는 Push 방식
    * 통지 종료 시점
      * 이터레이터 패턴 : hasNext가 false일 경우
      * RxJava : 완료 및 에러 통지시
  * 공통점
    * 데이터를 하나씩 순서대로 처리하는 매커니즘

| 클래스 | 설명 |
| :----- | :----- |
| Aggregate | 데이터 집합체 인터페이스 |
| Iterator | 데이터를 순서대로 받기 위한 인터페이스 |
| ConcreteAggregate | Aggregate를 구현한 클래스, 실제 데이터 집합체 |
| ConcreteIterator | Iterator를 구현한 클래스, 실제 데이터를 받을 수 있게 구현 |
 
### 3.2 비동기 처리
* 비동기 처리 : 어떤 작업을 처리하는 도중에 다른 작업도 처리할 수 있는 방법
* 논리 프로세서가 여러 개 있는 멀티코어 CPU를 사용하는 환경에서 동시에 여러 처리 작업 실행
* RxJava는 생산자와 소비자의 작업을 비동기로 처리하며, 스레드 관리를 위한 클래스 제공

#### 3.2.1 RxJava에서 비동기 처리
* 기본적으로 생산자, 연산자 및 소비자가 같은 스레드에서 실행
* 비동기 처리 설정을 통해 생산자, 연산자 및 소비자의 처리 작업을 실행할 스레드 분리 가능

> 비동치 처리를 위해 생산자와 소비자는 논리적으로 분리 되어야 한다.   
> 즉, 상호 독립, 책임 분리

| 메서드 | 스레드 |
| :-----: | :-----: |
| just | 메인 스레드 |
| from | 메인 스레드 | 
| timer | 메인 스레드와는 다른 스레드 |
| interval | 메인 스레드와는 다른 스레드 |   

* 생산자가 처리 실행 스레드와, 데이터를 받는 측의 스레드를 모두 관리

| 메서드 | 설명 |
| :-----: | :----- |
| subscribeOn | 생산자 스레드 종류 설정 |
| observeOn | 데이터를 처리하는 스레드 종류를 설정 |

##### 스케줄러
* 스케줄러(Scheduler)는 RxJava에서 제공하는 스레드를 관리하는 클래스
* I/O 작업은 대기 시간이 발생할 가능성이 커서 논리 프로세서 수를 초과하여 스레드를 생성하여도 효율적으로 작업이 가능
* 연산 작업은 대기 시간 없이 빠른 처리가 가능하여 논리 프로세서 수를 초과하여 스레드 생성시 스레드 전환 비용이 발생하여 성능 저하
? 스레드 전환 비용

| 메서드 | 스케줄러 |
| :-----: | :----- |
| computation | 연산 처리시 사용, 논리 프로세서 수 만큼 스레드 생성 |
| io | I/O 처리시 사용, 스레드 풀(Thread Pool)로 관리 |
| single | 싱글 스레드 생성 |
| newThread | 매번 새로운 스레드 생성 | 
| from(Executor executor) | 지정한 Executor가 생성한 스레드로 처리 | 
| trampoline | 현재 스레드의 큐(Queue)에 처리 작업을 넣는 스케줄러 |

##### subscribeOn 메서드
* 생산자의 처리 작업을 어떤 스케줄러에서 실행할지 설정하는 메서드
* 최초 1회만 설정하며, 중복 설정시 무시

##### observeOn 메서드
* 데이터를 받는 측의 처리 작업을 어떤 스케줄러에서 실행할지 설정하는 메서드
* 연산자마다 별도의 스케줄러 설정 가능

> observeOn 메서드 인자
> * observeOn(Scheduler scheduler, boolean delayError, int bufferSize)
>   * scheduler : 스레드를 관리하는 스케줄러 클래스
>   * delayError : 에러 통지 시점 설정
>       - true : 모든 데이터를 통지 후 에러통지
>       - false(Default) : 즉시 에러 통지
>   * bufferSize : 통지를 기다리는 데이터 버퍼 크기 (Default : 128)

#### 3.2.2 연산자 내에서 생성되는 비동기 Flowable/Observable
* 연산자 내부에서 Flowable/Observable을 생성하고 그 결과를 통지
* 이때 생성되는 Flowable/Obserable은 별도의 스레드에서 실행
* 순서대로 실행하지만, 별도의 스레드에서 동작하기 때문에 결과 데이터의 순서는 보장되지 않음

##### flatMap 메서드
* 새로운 Flowable/Observable을 생성하고 이를 실행해 통지되는 데이터를 메서드의 결과물로 통지하는 연산자

##### concatMap 메서드
* 새로운 Flowable/Observable을 생성하고 이를 순서대로 실행해 통지되는 데이터를 메서드의 결과물로 통지하는 연산자
* 순서가 보장, 순차 처리 동작

##### concatMapEager
* 새로운 Flowable/Observable을 생성하고 이를 즉시 실행해 통지되는 데이터를 메서드의 결과물로 통지하는 연산자
* 결과 데이터를 버퍼에 담고 있기 때문에 메모리 부족 위험이 있음
* 순서가 보장, 병렬 처리 동작

#### 3.2.3 다른 스레드 간 공유되는 객체
* 여러 소비자가 공유 객체에 접근할 때는 순차적으로 데이터에 접근하지 못하는 이슈
* 여러 Flowable/Obserable을 하나의 Flowable/Observable로 결합하여 순차적으로 공유 객체에 접근하도록 함
* merge 메서드 제공

> Ajax vs React
> * Ajax : Reload 없이 웹 페이지를 refresh하기 위한 기술
> * React : 동적으로 페이지를 업데이트 하기 위한 자바스크립트 라이브러리로 Javascript Interaction 이나 Ajax에 의해 계산 된 컴포넌트를 페이지에 업데이트 (Ajax를 이용하여 페이지 업데이트)
> * Mustache & Handlebars : ReactJS와 약간의 차이가 있으며, 주 목적은 페이지에 보여질 컴포넌트 템플릿을 생성하는 것 (Ajax를 이용하여 데이터를 가져옴)

> Callable vs Runnable  
> * Callable : Exception이 발생하며, Return 값이 존재 (Runnable의 확장형 개념, Java 1.5+)
> * Runnable : Exception이 발생하지 않으며, Return 값이 존재하지 않음 (Java 1.0+)

### 3.3 에러처리
* 소비자(Subscriber/Observer)에게 에러 통지하기
* 처리 작업 재시도(retry)
* 대체 데이터 통지

#### 3.3.1 소비자에게 에러 통지하기
* 데이터 통지 처리 중 에러가 발생하면 소비자에게 에러를 통지
* 기본적으로 스택 트레이스를 출력하도록 처리됨

#### 3.3.2 처리 재시도
* 에러가 발생하면 재시도 하여 에러 처리 하는 방법
* 순간적인 에러로 회복 가능성이 있는 상황에서 사용
* retry, retryUntil, retryWhen 메서드 등 제공

| 연산자 | 설명 |
|:-----: | :----- |
| retry | 재시도 횟수, 재시도 여부를 판단하는 함수형 인터페이스를 인자로 재시도 (true의 경우 재시도) |
| retryUntil | 재시도 여부를 판단하는 함수형 인터페이스를 인자로 재시도 (false의 경우 재시도) |
| retryWhen | 재시도를 위한 Flowable/Observable을 생성하는 함수형 인터페이스를 인자로 재시도 |

#### 3.3.3 대체 데이터 통지
* 에러가 발생하면 대체 데이터를 통지하고 완료 처리 하는 방법
* 에러가 발생한 이후 데이터는 통지되지 않음
* onErrorReturnItem, onErrorResumeNext, onExceptionResumeNext 메서드 등 제공

| 연산자 | 설명 | 
|:-----: | :----- |
| onErrorReturnItem, onErrorReturn | 애러를 통지하지 않고 대체 데이터 통지 |
| onErrorResumeNext | 에러를 통지하지 않고 Flowable/Observable을 생성하여 데이터 통지 |
| onExceptionResumeNext | Exception 발생시 에러를 통지하지 않 Flowable/Observable을 생성하여 데이터 통지 |

### 3.4 리소스 관리
* 구독시 리소스 생성
* 완료, 에러가 통지시 리소스를 해제

#### 3.4.1 using 메서드
* 리소스 라이프사이클에 맞춘 Flowable/Observable을 생성
* 리소스 관리는 Flowable/Observable 내부에서 관리
* 세가지 함수형 인터페이스를 인자로 사용
  * Callable : 리소스 얻기
  * Function : 리소스에서 얻은 데이터를 사용하는 Flowable/Observable 생성
  * Consumer : 리소스 해제

#### 3.4.2 FlowableEmitter/ObservableEmitter
* create 메서드 내부의 FlowableEmitter/ObservableEmitter 객체도 리소스 해제 지원
* setCancellable, setDisposable 메서드 제공

##### setCancellable
* 완료, 에러 통지 및 구독 해지시 cancel 메서드 실행
* 내부적으로 setDisposable 메서드를 호출 (Disposable로 감싸서 구현)
```java
emitter.setCancellable(() -> resource.close());
```

##### setDisposable
* 완료, 에러 통지 및 구독 해지시 dispose 메서드 실행

> 리소스 관리의 주의할 점 !   
> * 구독 중도 해지시 이미 파기한 리소스에 접근하면 에러 발생, 리소스 파기 여부 확인 로직 필요
> * setCancellable, setDisposable 중복 설정시 리소스 파기가 중복하여 수행됨

### 3.5 배압
* 데이터 통지량을 제어하는 기능
* `Reactive Streams`에서 필수 기능
* Reactive Streams를 강제하는 Flowable에서 제공 (Observable은 미제공)
* 통지 속도보다 처리 속도가 느릴때 배압이 필요
* `BackpressureStrategy`에 설정

#### 3.5.1 request 메서드
* 통지할 데이터 개수를 요청할 때 사용
* `1 ~ Long.MAX_VALUE` 값 설정 (Long.MAX_VALUE는 무제한을 의미)
* 데이터 처리 속도에 맞춰서 통지 진행

#### 3.5.2 observeOn 메서드와 배압
* 생산자/소비자의 통지/처리 속도 차이 발생시 스케줄 설정 가능
* 생산자의 스케줄러 설정하여 새로운 Flowable 생성
* scheduler, delayError, bufferSize(Default 128) 인자 사용
* 소비자는 `Long.MAX_VALUE` 지정 권장

#### 3.5.3 MissingBackpressureException
* 버퍼에 쌓인 데이터가 임계치를 넘으면 MissingBackpressureException 발생

##### BackpressureStrategy
* 통지를 기다리는 데이터를 다루는 배압 전략을 나타냄
* create 메서드의 두번째 인자로 설정

| 종류 | 설명 | 
|:-----:|:-----|
|BUFFER|모든 데이터를 버퍼에 쌓음|
|DROP|새로 생성한 데이터는 파기|
|LATEST|최신 데이터만 버퍼에 쌓음|
|ERROR|버퍼 크기를 넘기면 MissingBackpressureException 에러 통지|
|NONE|특정 전략을 설정하지 않으며, onBackpressure 메서드로 배압 설정|

#### 3.5.4 메서드로 통지할 데이터양 제어하기
* 통지 데이터 제어 (filter 등)
* 통지 횟수를 제어

##### 스로틀링
* 지정된 기간 조건에 맞는 데이터만 통지하는 메서드

##### buffer 메서드와 window 메서드
* 데이터를 모아서 통지 (통지 빈도 및 처리 횟수를 제한하여 효율적 운영 가능)
  * buffer 메서드 : 데이터를 컬렉션에 저장한 후 통지하는 메서드
  * window 메서드 : Flowable/Observable에 데이터를 모아 통지하는 메서드