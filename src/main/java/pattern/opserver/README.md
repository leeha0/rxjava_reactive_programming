# 옵저버 패턴(Observer Pattern)
한 객체의 상태가 바뀌면 그 객체에 의존하는 다른 객체들한테 연락이 가고 자동으로 내용이 갱신되는 방식으로 일대다(one-to-many) 의존성을 정의

#### 구성 요소

| 구성 | 설명 |
| :----- | :----- |
| Subject | 데이터를 관리하며 데이터의 상태 변경시 Observer에게 통지 |
| Observer | Subject 객체를 구독하며 변경되는 데이터에 대해 통지 받음 |

#### 특징
* 느슨한 결합
* Observer는 Observer interface를 공통으로 구현함
* Push 방식

> 느슨한 결합(Loose Coupling)  
> : 두 객체가 상호작용하지만 서로에 대해서 잘 모르는 것을 의미
>
> * Subject는 Observer가 특정 인터페이스를 구현하는 것만 알고 있음
> * Observer는 언제든 추가/삭제가 가능
> * Subject, Observer는 독립적으로 재사용 가능

#### Push & Pull
* Observer 패턴은 기본적으로 Push 방식으로 동작
  * Push : Subject가 Observer에게 데이터 통지
  * Pull : Observer가 Subject의 데이터를 가져옴

#### Java에서 제공하는 옵저버 패턴
* `java.util` 패키지에서 제공
* Push & Pull 방식 모두 지원
* 통지 순서를 보장하지 않음

| 구성 | 설명 |
| :----- | :----- |
| Observable | 데이터를 관리하며 데이터의 상태 변경시 Observer에게 통지 |
| Observer | Subject 객체를 구독하며 변경되는 데이터에 대해 통지 받음 |

* setChanged() 메서드를 통해 데이터 상태 변경 여부 변경
* Object arg 전달받은 인자를 통지

> java에서 제공하는 옵저버 패턴을 사용하면 안되는 이유
> * Java 9 이후 미지원
> * Observable(Subject)는 클래스이기 때문에 서브 클래스를 생성해야함
> * Observable에서 다르게 동작하기 위해서 직접 클래스를 구현해야함
> * 활용도와 재사용성 떨어짐