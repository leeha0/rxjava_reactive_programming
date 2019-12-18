# Chapter3
* RxJava의 아키텍처는 옵저버(Observer) 패턴과 이터레이터(Iterator) 패턴의 영향을 받음

### 3.1.1 옵저버 패턴
* GoF가 처음 제시한 디자인 패턴
* 관찰 대상 객체의 상태에 변화가 발생하면 객체를 관찰하는 객체가 변화에 따라 일련의 처리를 작업

| 클래스 | 설명 |
| :----- | :----- |
| Subject | 관찰 대상 클래스 (`생산자`) |
| Observer | 관찰자 인터페이스 (`소비자`) |
| ConcreteSubject | Subject를 상속한 클래스, 실제 관찰 대상 객체 |
| ConcreteObserver | Observer를 구현한 클래스, 실제 변화에 따른 처리 내용 구현 |

### 3.1.2 이터레이터 패턴
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