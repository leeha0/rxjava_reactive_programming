# Chapter6
### 6.1 디버깅과 테스트
* RxJava에서 디버깅이 어려운 이유
  * 생산자가 소비자에게 통지하는 데이터는 기본적으로 외부에서 접근할 수 없다
  * 비동기 처리 시에는 여러 처리가 동시에 실행되므로 실행할 때마다 결과가 달라질 수 있다
  * 실행 환경의 영향을 받아 시스템의 자원에 따라 실패할 수 있다 
  
* RxJava에서 지원하는 클래스
  * 'do'로 시작하는 메서드: 통지 시점 지정한 처리를 수행하는 메서드 
  * 'blocking'으로 시작하는 메서드: 비동기 처리 결과를 받게 하는 메서드 
  * TestSubscriber/TestObserver: 테스트 목적으로 사용하는 메서드 제공
  * TestScheduler: 테스트 용도의 스케줄러

### 6.2 'do'로 시작하는 메서드
* 통지 전/후에 특정한 처리를 수행하는 메서드를 제공
* 'doOn', 'doAfter'로 시작
* doOnNext, doOnComplete, doOnError, doOnSubscribe, doOnRequest, doOnCancel, doOnDispose

#### 6.2.1 doOnNext
* 데이터 통지 시에 지정한 처리를 실행
* 연산자 간의 데이터 통지 시에도 수행

#### 6.2.2 doOnComplete
* 완료 통지(onComplete)시 지정한 처리를 실행

#### 6.2.3 doOnError
* 에러를 통지하면 지정한 처리를 실행

#### 6.2.4 doOnSubscribe
* 구독 시작(onSubscribe)시 지정한 처리를 실행
* 구독 시작시 전달되는 Subscription/Disposable도 동일하게 인자로 전달 받음

#### 6.2.5 doOnRequest
* Flowable이 데이터 개수를 요청받을 때 지정한 처리를 실행
* 배압 기능이 없는 Observable에는 이 메서드가 제공되지 않음

#### 6.2.6 doOnCancel/doOnDispose
* 구독을 해지하면 지정한 처리를 실행
* doOnCancel은 Flowable 구독 해지시 수행
* doOnDispose는 Observable 구독 해지시 수행

### 6.3 'blocking'으로 시작하는 메서드
* 비동기 처리 결과를 현재 실행 중인 스레드에서 받을 수 있는 메서드
* blockingFirst, blockingLast, blockingIterable, blockingSubscribe

#### 6.3.1 blockingFirst
* 첫 번째 통지 데이터를 받게 하는 메서드
* 메서드를 호출하면 Flowable/Observable의 처리를 시작
* 데이터나 완료를 통지하지 않는 경우 사용 불가

#### 6.3.2 blockingLast
* 마지막 통지 데이터를 받게 하는 메서드
* 메서드를 호출하면 Flowable/Observable의 처리를 시작
* 완료통지가 없는 경우 사용 불가

#### 6.3.3 blockingIterable
* 통지하는 모든 데이터를 받는 Iterable을 얻게 하는 메서드
* 메서드를 호출하면 Flowable/Observable의 처리를 시작
* Flowable/Observable이 통지한 데이터는 버퍼에 보관
* MissingBackpressureException, NoSuchElementException
* 데이터나 완료를 통지하지 않는 경우 사용 불가

#### 6.3.4 blockingSubscribe
* 호출한 원본 스레드에서 Subscriber/Observer의 통지 데이터 처리를 실행할 수 있게 하는 메서드
* 메서드를 호출하면 Flowable/Observable의 처리를 시작

### 6.4 TestSubscriber/TestObserver
* 통지 내용을 테스트하기 위해 사용하는 소비자 클래스

#### 6.4.1 TestSubscriber/TestObserver 생성
* 생산자의 test 메스드
* 생성자로 직접 생성
* create 메서드

##### assert 메서드
* 통지 데이터를 테스트 할 수 있는 메서드

##### await 메서드
* 지정한 시간 동안 또는 지정한 종료 시각까지 대기하게 하는 메서드

### 6.5 TestScheduler
* 통지 시 걸리는 시간을 다루는 클래스
* interval, timer 메서드와 같은 시간과 관련된 데이터를 다룰 때 사용

#### 6.5.1 주요 메서드
* advanceTimeBy : 지정한 시간 동안만 처리
* advanceTimeTo : 지정한 시각까지 처리
* now : 스케줄러 생성 이후 부터 지금까지의 경과 시간

#### 6.5.2 TestScheduler를 사용한 예제

```
           upstream                           downstream
source <------------- operator (parameters) -------------> consumer/further operators
```

```java
// subscribeOn is affecting its upstream and downstream. For example, subcsribeOn on this code
just("Some String")
  .map(str -> str.length())
  .subsribeOn(Schedulers.computation()) // change thread
  .map(length -> 2 * length)
  .subscribe(number -> Log.d("", "Number " + number))
```

```java
// On the other hand, observeOn only affecting downstream. observeOn on this code
just("Some String")
  .map(str -> str.length())
  .observeOn(Schedulers.computation()) // change thread
  .map(length -> 2 * length)
  .subscribe(number -> Log.d("", "Number " + number))
```