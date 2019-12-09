# Chapter 1. RxJava의 기본
1. RxJava
2. Reactive Streams

## RxJava란
리액티브 프로그래밍을 구현하는데 사용하는 라이브러리

#### RxJava 개요
* 2009년 마이크로소프트 - Reactive Extensions(Rx, ReactiveX)
* 2013년 넷플릭스 - RxJava

> ReactiveX  
> : 자바, .NET, Javascript, Swift 등 지원

#### 리액티비 프로그래밍이란
* 데이터 통지에 의해 프르그램이 반응(reaction)하여 데이터를 처리하는 방식
* 마이크로서비스와 같은 분산 시스템을 구현하기 적합한 프로그래밍 기법

#### RxJava 특징
* 옵저버(Observer) 패턴 확장
* 쉬운 비동기 처리
* 함수형 프로그래밍 영향으로 함수형 인터페이스를 인자로 사용

## Reactive Streams
라이브러리나 프레임워크에 상관없이 데이터 스트림을 비동기로 처리하는 공통 매커니즘

> 데이터 스트림  
> : 데이터의 흐름으로, 이미 생성된 데이터와 앞으로 생성할 가능성이 있는 데이터까지 포함하는 개념

#### Reactive Streams 구성
* 구성
  * Publisher : 데이터를 통지하는 생산자
  * Subscriber : 데이터를 받아 처리하는 소비자
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

#### Reactive Streams 규칙
* 구독 시작 통지(onSubscribe)는 해당 구독에서 한 번만 발생
* 통지는 순차적으로 수행
* null은 통지 하지 않음
* 완료 통지(onComplete) 또는 에러 통지(onError)로 종료

## RxJava 기본 구조
#### 기본 구조
* 생산자와 소비자 구조로 구성
  * Flowable/Subscriber (Subscription) : 소비자의 요청에 의한 통지 (Reactive Streams 및 배압 지원)
  * Observable/Observer (Disposable) : 데이터가 생성되자 마자 통지
* 연산자
  * 통지하는 데이터를 생성, 필터링, 변환하는 메서드
  * 연산자는 메서드 체인 방식으로 연결 (`GoF 빌더 패턴`과 유사)
  * 외부 객체의 상태값과 연동시 사이드이펙트 고려
* 비동기 처리
  * 스레드를 관리하는 스케줄러 제공
  * 생성자, 소비자 각각 스케줄러 설정 가능
* Cold, Hot 생성자
  * Cold 생성자 (Default) : 생성자와 소비자 간의 1개의 구독 관계(timeline)를 맺음
  * Hot 생성자 : 생성자와 소비자 간의 여러 구독 관계(timeline)를 맺음
  * Cold에서 Hot으로 변환하는 메서드
     * publish()
     * replay()
     * share()
* ConnectableFlowable/ConnectableObservable (Hot)
  * connect로 구독 시작
  * 여러 구독자에게 동시에 데이터 통지 가능
  * Flowable/Obserable로 변환하는 메서드
    * refCount()
    * autoConnect()