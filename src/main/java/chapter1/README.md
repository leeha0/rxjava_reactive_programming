# Chapter1

### 1.1 RxJava와 리액티브 프로그래밍
#### 1.1.1 RxJava란
* RxJava는 리액티브 프로그래밍을 구현하는데 사용하는 라이브러리이다.
* 2.0 버전부터 Reactive Streams 사양을 구현한다.
* Reactive Streams는 데이터 스트림을 비동기로 처리하는 공통 메커니즘을 인터페이스로 제공한다.
* 자바 6비전 이상, 안드로이드 2.3 버전 이상을 지원한다.
* 함수형 프로그래밍의 영향으로 함수형 인터페이스를 인자로 받는다.
* 데이터를 통지하는 생성자를 생성하고, 통지한 데이터를 소비자가 받아 처리하는 방식이 RxJava의 기본 처리 방식이다.

#### 1.1.2 리액티브 프로그래밍이란
* 리액티브 프로그래밍은 데이터가 통지될 때마다 관련 프로그램이 반응(reaction)해 데이터를 처리하는 프로그래밍 방식이다.
* 각각의 데이터가 생성될 떄마다 순서대로 통지되며, 이러한 데이터 흐름을 데이터 스트림(data stream)라고 한다.

> 리스너(listener)와의 차이점?
> * 책임의 관점 : 리액티브는 이벤트 발생 주체가 데이터를 전달하는 것 까지 책임을 갖지만, 리스너는 이벤트가 발생하는 주체가 데이터의 처리에 대한 책임까지 갖음
> * 비동기 처리 관점 : 리액티브는 데이터를 처리하는 주체인 소비자가 데이터로 무엇을 하는지 몰라도 되며, 처리를 기다릴 필요가 없어 비동기 처리 구현이 가능
> * 마이크로서비스 : 리액티브는 시스템 구축 관점에서 볼때 마이크로서비스와 같은 분산 시스템을 구현하는데 적합하다.

#### 1.1.3 RxJava 개요
* 2009년 마이크로소프트 - Reactive Extensions(Rx, ReactiveX)
* 2013년 넷플릭스 - RxJava (Java로 이식)

> ReactiveX (Reactive Extentions)  
> * 자바, .NET, Javascript, Swift 등 지원

> 배압(backpressure)
> * 임계값에 도달하면 버터에 있는 요청의 내부 처리가 들어오는 요청을 따라잡을 때까지 버퍼에 넣는 것을 일시로 정지하는 것을 말한다.

#### 1.1.4 RxJava 특징
* 옵저버(Observer) 패턴 확장
* 쉬운 비동기 처리
* 함수형 프로그래밍 영향으로 함수형 인터페이스를 인자로 사용

> 옵저버(Observer) 패턴
> * 감시 대상 객체의 상태가 변하면 이를 관찰하는 객체에게 알려주는 구조

### 1.2 Reactive Streams
#### 1.2.1 Reactive Streams란
* 라이브러리다 프레임워크에 상관없이 데이터 스트림을 비동기로 다를 수 있는 공통 메커니즘
* 이런 메커니즘을 사용할 수 있는 인터페이스 제공

#### 1.2.2 Reactive Streams 구성
* 구성
  * Publisher(생산자) : 데이터를 통지하는 생산자
  * Subscriber(소비자) : 데이터를 받아 처리하는 소비자
  * 소비자가 생성자를 구독(subscribe)하면 소비자는 생성자로부터 데이터를 통지 받을 수 있다.
* 프로토콜
  * onSubscribe : 데이터 통지 준비 완료
  * onNext : 데이터 통지
  * onError : 에러 통지
  * onComplete : 완료 통지
* 인터페이스
  * Publisher : 데이터 생성 및 통지
  * Subscriber : 통지된 데이터를 전달받아 처리
  * Subscription : 요청 개수 및 구독 해지
  * Processor : Publisher + Subscriber 

#### 1.2.3 Reactive Streams 규칙
* 구독 시작 통지(onSubscribe)는 해당 구독에서 한 번만 발생
* 통지는 순차적으로 수행
  * 데이터가 동시에 통지돼 불일치가 발생하는 것을 방지한다.
* null은 통지 하지 않음
  * null을 통지할 경우 NPE(NullPointException)가 발생한다.
* 완료 통지(onComplete) 또는 에러 통지(onError)로 종료
* Reactive Streams는 RxJava의 [Observable 규약(Observable Contract)](http://reactivex.io/documentation/contract.html)라는 규칙을 따른다.

> Subscription 규칙
> * 데이터 개수 요청에 `Long.MAX_VALUE`를 설정하면 데이터 개수에 의한 통지 제한이 없어진다.
> * Subscription의 메서드는 동기화된 상태로 호출해야 한다.
> * Subscription객체는 onSubscribe를 통해 인자로 전달 된다.
> * RxJava에서는 Subscription의 메서드를 호출할 때 동기화가 이루어지므로 처리 자체가 스레드 안전(thread safety)한지를 신경써야 한다.

### 1.3 RxJava 기본 구조
#### 1.3.1 기본 구조
* 생산자와 소비자 구조로 구성

  | 구분 | 생성자 | 소비자 | 데이터 요청 및 구독 해지 |
  | :----- | :-----:| :------: | :------: |
  | Reactive Streams 지원 | Flowable | Subscriber | Subscription |
  | Reactive Streams 미지원 | Observable | Observer | Disposable |
  
#### 1.3.2 연산자
* 통지하는 데이터를 생성, 필터링, 변환하는 메서드
* 연산자는 메서드 체인(method chain) 방식으로 연결 (`GoF 빌더 패턴`과 유사)
* 외부 객체의 상태값과 연동시 부가작용(side effect) 고려
* 통지를 받은 시점에 연산자 처리가 실행된다.

#### 1.3.3 비동기 처리
* 스레드를 관리하는 스케줄러 제공
* 생성자, 소비자 각각 스케줄러 설정 가능

#### 1.3.4 Cold, Hot 생성자
* Cold 생성자 (Default) : 생성자와 소비자 간의 1개의 구독 관계(timeline)를 맺음
* Hot 생성자 : 생성자와 소비자 간의 여러 구독 관계(timeline)를 맺음

#### 1.3.5 ConnectableFlowable/ConnectableObservable
* Hot Flowable/Obserable
* connect로 구독 시작
* 여러 구독자에게 동시에 데이터 통지 가능
* Flowable/Observable로 변환하는 메서드 (`Hot to Cold`)
  * refCount() : 새로운 Flowable/Observable을 생성하며, 모든 구독이 끝난 뒤 다시 구독 가능
  * autoConnect() : 지정한 개수의 구독이 시작된 시점에 처리를 시작하는 Flowable/Observable을 생성

#### 1.3.6 Flowable/Observable을 Cold에서 Hot으로 변환하는 연산자 (`Cold to Hot`)   
* publish() : 구독한 이후에 생산된 데이터부터 새로운 소비자에게 통지
* replay() : 통지한 데이터를 캐시하고, 처리한 뒤에 구독하면 캐시된 데이터를 먼저 통지
* share() : 같은 타임라인에서 생성되는 데이터를 통지 (`flowable.publish().refCount()`와 같음)

### 1.4 마블 다이어그램
* 시간 경과에 따른 데이터 변화를 시각화한 다이어그램

### 1.5 RxJava 예제
#### 1.5.1 환경 구축
* RxJava 라이브러리, Reactive Streams 라이브러리 필요 (2.x+)
* Maven, Gradle 프로젝트 관리 도구 필요

> Maven
> * 프로젝트 객체 모델(POM, Project Object Model)이라는 개념을 바탕으로 의존 관리, 라이브러리 관리, 생명 주기 관리 제공

> Gradle 
> * Groovy를 기반으로 한 오픈 소스 빌드 도구

#### 1.5.2 Flowable 사용 예제
* Cold 생성자
* 소비자가 구독(subscribe)하면 바로 처리를 시작
* BackpressureStrategy의 종류

  | 옵션 | 설명 |
  | :----: | :------ |
  | BUFFER | 통지할 수 있을 때까지 모든 데이터를 버퍼링 |
  | DROP | 통지할 수 있을 때까지 새로운 데이터는 모두 삭제 |
  | LATEST | 최신 데이터만 버퍼링 (최신만 유지) |
  | ERROR | 통지 대기 데이터가 버퍼 크기를 초과하면 MissingBackpressureException 에러 통지 |
  | NONE | 특정 배압 전략을 선택하지 않으며, onBackPressure()메서드를 통해 배압 모드 설정 |
  
> Flowable vs Observable
> * Flowable 사용
>   * 대량 데이터 처리
>   * 네트워크 통신, 데이터베이스 등의 I/O 작업시
> * Observable 사용
>   * GUI 이벤트
>   * 소량 데이터 처리
>   * 동기 방식 처리
>   * 자바 표준 Stream을 대신하여 사용

### 1.6 RxJava의 전체 구성
* 소비자(Subscriber/Observer)가 생산자(Flowable/Observable)을 구독하는 형식
* Flowable/Subscriber는 Reactive Streams 사양을 지원
* Observable/Observer는 Reactive Streams 사양을 지원하지 않음
* 생성자와 소비자 사이에서 공유되는 Subscription, Disposable 존재
* ObservableSource/Observer 인터페이스를 통해 Reactive Streams를 지원할 수 있음
* FlowableProcessor는 Processor를 구현하고 Flowable과 Subscriber가 모두 될 수 있는 추상 클래스이다.
* Subject는 Processor를 구현하고 Observable과 Observer가 될 수 있는 추상 클래스이다.

##### Flowable/Observable
* 데이터를 생성하고 통지하는 클래스로 배압 기능 유무에 따른 차이가 있다.

##### 통지시 규칙
* null을 통지하면 안된다.
* 데이터 통지는 해도 되고 안 해도 된다.
* Flowable/Obserable의 처리를 끝낼 때는 완료나 에러 통지를 해야하며, 둘 다 통지하지 않는다.
* 완료나 에러 통지를 한 뒤에는 다른 통지를 해서는 안 된다.
* 통지할 때는 1건씩 순차적으로 통지하며, 동시에 통지하면 안 된다.

##### Subscriber/Observer
* 통지된 데이터를 전달받아 이 데이터를 처리하는 인터페이스로 배압 기능 유무에 따른 차이가 있다.
* subscription의 request 메서드를 호출하면 통지처리를 시작한다.
* onSubscription 메서드가 끝난 뒤 onNext 메서드가 실행된다.

##### Subscription
* 통지 데이터 개수를 요청하는 request 메서드와 구독을 해지하는 cancel 메서드를 포함하는 인터페이스이다.

##### Disposable
* 구독을 해지하는 메서드를 포함하는 인터페이스이다.
* 외부에서 구독을 해지하는 것이 가능하다.
* 자원을 해지하는 처리가 가능하다.

##### FlowableProcessor/Subject
* 생성자(Publisher)와 소비자(Subscriber)의 기능이 모두 있는 인터페이스이다.

##### DisposableSubscriber/DisposableObserver
* Disposable을 구현한 Subscriber/Observer의 구현 클래스이다.
* 외부에서 비동기로 구독 해지 메서드를 호출해도 안전하게 구독 해지가 가능하다.
* onSubscriber가 final 메서드로 구현되어 있다.
* Subscription/Disposable은 은닉되어 직접 접근하지 못한다.

##### ResourceSubscriber/ResourceObserver
* Disposable을 구현한 Subscriber/Observer의 구현 클래스이다.
* 외부에서 비동기로 구독 해지 메서드를 호출해도 안전하게 구독 해지가 가능하다.

##### subscribe/subscribeWith 메서드
* 소비자가 생성자를 구독하는 메서드이다.
* 생산자가 데이터를 통지할 소비자를 등록한다.
* RxJava는 처음 request 메서드를 호출한 시점에 통지 처리가 시작된다.
* subscribeWith는 Subscriber/Observer를 인자로 전달하면 내부에서 subscribe를 호출하고, 인자로 받은 Subscriber/Observer를 반환한다.

##### CompositeDisposable
* 여러 Disposable을 모아 CompositeDisposable의 disposable 메서드를 호출함으로써 가지고 있는 모든 Disposable의 dispose 메서드를 호출할 수 있는 클래스
* 동시 구독 해지 가능

#### 1.6.2 Single/Maybe/Completable
* 통지 데이터가 1건 또는 전혀 없을 때 사용한다.

| 클래스 | 설명 |
| :-----: | :----- |
| Single | 데이터를 1건만 통지하거나 에러를 통지하는 클래스 |
| Maybe | 데이터를 1건만 통지하거나 1건도 통지하지 않고 완료를 통지하거나 에러를 통지하는 클래스 |
| Completable | 데이터를 1건도 통지하지 않고 완료를 통지하거나 에러를 통지하는 클래스 |

> RxJava에서 Java의 Optional과 같은 기능을 수행할 수 있도록 한다.

#### 1.6.3 RxJava의 확장 모듈
* RxJavaString : 문자열을 다루는 확장 모듈
* RxJavaFileUtils : 파일 관련 처리를 하는 확장 모듈
* RxJavaMath : 수학 관련 처리를 하는 확장 모듈
* RxJavaJoins : 여러 Observable을 사용하는 처리를 위한 확장 모듈
* RxJavaAsyncUtil : 비동치 처리를 위한 확장 모듈