# Charter2

### 2.1 람다식(Lambda Expression)
* 함수형 인터페이스를 구현하기 위한 표현식 (자바 8+)
* 함수형 인터페이스는 구현해야 하는 메서드가 1개만 있는 인터페이스
* static 메서드, default 메서드는 제외

```java
@FunctionalInterface
public interface Runnable {

    public abstract void run();
}
```
```java
Runnable task = () -> {
    // Do Somthing
};
```

#### 2.1.1 함수형 인터페이스(Functional Interface)
* RxJava에서도 독자적으로 함수형 인터페이스를 제공
* `io.reactive.functions` 패키지에 정의
* Java8 함수형 인터페이스와 다르게 예외를 던짐

| Interface | 설명 |
| ------------- | :-----|
| Function/Predicate | 인자를 전달받아 반환값 반환 |
| - Function | 인자를 전달 받아 반환(제한없음) 값 리턴 |
| - Predicate | 인자를 전달 받아 Boolean 값 리턴 |
| BooleanSupplier | 인자 없이 반환값(Boolean) 반환 |
| Action/Consumer | 반환값 없음 |
| - Action | 인자 없이 받아 부가 작용 발생 |
| - Consumer | 인자를 받아 부가 작용 발생 |
| Concellable | 인자도 반환값도 없음 (Action과 동일하나 의미하는 바가 다름) |

#### 2.1.3 람다식과 익명 클래스의 차이점
* `this`를 가르키는 대상이 다르다
* 익명클래스 : 구현한 인터페이스
* 람다식 : 구현한 클래스의 인스턴스

### 2.2 비동기 처리
* 비동기 처리 : 어떤 작업을 실행하는 동안 처리가 끝나기를 기다리지 않고 다른 작업을 처리하는 것
* 멀티스레딩 : 여러 개의 스레드로 작업을 처리

#### 2.2.1 비동기 처리 시 주의할 점
* 메모리와 캐시
  * 하나의 클래스 필드가 가리키는 값과 실제 메모리가 가리키는 값이 동일하지 않을 수 있다.
* 원자성(Atomicty)
  * 일련의 처리 흐름이 불할되서는 안되는 것
  * 더이상 쪼개질 수 없는 성질
  * `volatile` 제한자 : 업데이트한 값을 반드시 메모리에 반영하는 제한자
  
#### 2.2.2 비동기 처리 시 발생하는 문제에 대한 대응 방안
* final 제한자와 불변 객체(Immutable object)
  * 재할당이 불가능한 변수 사용
  * 상태가 변하지 않는 객체를 사용 (내부 필드에도 final 제한자 사용)
  
```java
public final class ImmutableObject {
    private final Date value;
    
    // 생성자만 상태 설정 가능
    public ImmutableObject(Date date) {
        this.value = (Date) date.clone();
    }
    
    // 메모리 값에 영향이 없도록 복제 데이터 전달
    public Date getValue() {
        return (Date) value.clone();
    }   
}
```
* 정적 팩토리 메서드
  * 매번 인스턴스를 생성하는 것이 아니라 하나만 만들고 인스턴스를 재사용
  * 싱글톤(Singletone) 패턴
  
* 공유되는 가변 객체 다루기 ???
* volatile 제한자
  * 최신 메모리 값을 가져오게 보장하는 제한자
  * 변경 즉시 메모리 값을 변경
  * 값을 업데이트 할 때 원자성을 보장하지 않는다
  * 멀티스레드 환경에서 동시에 동일한 필드 데이터를 업데이트 할 경우
  * 싱글 스레드로 데이트를 업데이트 하도록 구현
  
#### 2.2.3 java.util.concurrent.atomic 패키지
* 원자성은 일련의 동작을 외부에서 분할할 수 없는 특성이다.
* 일련의 동작 처리과정 중 외부에서 접근하지 못하도록 해야한다.
* 이러한 처리를 보장하는 클래스를 `java.util.concurrent.atomic` 패키지에서 제공한다.
* 자신의 변경 처리에만 원자성을 갖는다.

* synchronized 블록
  * 자신의 스레드가 synchronized 블록을 처리하는 중에는 다른 스레이드애서 해당 블록에 접근하는 것을 막는다.
  * 다른 스레드의 접근을 막는 것을 배차 제어라고 한다.
  * synchronized 대상 객체(소유권을 얻으려는 대상)를 락 객체(Lock Object)라고 한다.
  * 다른 스레드로부터 인터럽트를 방지하여 데이터 불일치를 피할 수 있다.
  * 대상 객체가 참조형 객체일 때는 복사복을 전달해야 한다.
  * 교착 상태(deadlock)에 주의해야한다. 
  * 교착 상태 : 서로 다른 스레드가 각각 상태의 락 객체를 소유하고자 할때 무한 대기 상태에 빠지는 것을 말함
  
  