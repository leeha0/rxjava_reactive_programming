# Charter2

### 2.1 람다식(Lambda Expression)
  * `함수형 인터페이스를 구현`하기 위한 표현식 (자바 8+)
  * 함수형 인터페이스는 구현해야 하는 메서드가 1개만 있는 인터페이스
  * static, default 메서드는 제외

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

##### default 메서드
  * 자바8에서 기본 구현을 포함하는 인터페이스를 정의하는 방법
  * 인터페이스를 구현하는 모든 클래스의 소스 호환성을 유지
```java
public interface MyRunnable {
    public abstract void run();
    default boolean isRun() {
        return true;
    }
}
```

##### static 메서드
  * 객체가 생성되지 않은 상태에서도 사용가능한 static 멤버
  * static 멤버는 클래스 로딩시 생성
  * static 멤버는 클래스당 하나만 생성
```java
class StaticMethod {
    static void myMethod() {
    }   
}
```
```java
StaticMethod.myMethod()
```

#### 2.1.1 함수형 인터페이스(Functional Interface)
  * 함수형 인터페이스는 오직 `하나의 추상 메서드`를 지정하는 인터페이스
  * RxJava는 독자적으로 함수형 인터페이스를 제공
    * `io.reactive.functions` 패키지에 정의
    * Java8과 다르게 예외를 던지는 것을 허용

| Interface | 설명 |
| ------------- | :-----|
| Function/Predicate | 인자를 전달받아 반환값 반환 |
| - Function | 인자를 전달 받아 반환(제한없음) 값 리턴 |
| - Predicate | 인자를 전달 받아 Boolean 값 리턴 |
| BooleanSupplier | 인자 없이 반환값(Boolean) 반환 |
| Action/Consumer | 반환값 없음 |
| - Action | 인자 없이 받아 부가 작용 발생 |
| - Consumer | 인자를 받아 부가 작용 발생 |
| Cancellable | 인자도 반환값도 없음 (Action과 동일하나 의미하는 바가 다름) |

##### Java8 함수형 인터페이스
  * 예외를 던지는 것을 허용하지 않음
  * 예외처리 ? 
    * 람다를 try / catch
    * 예외를 throws하는 함수형 인터페이스 정의

#### 2.1.3 람다식과 익명 클래스의 차이점
  * `this`를 가르키는 대상이 다르다
  * 익명클래스 : 구현한 인터페이스
  * 람다식 : 구현한 클래스의 인스턴스

### 2.2 비동기 처리
  * 비동기 처리 : 어떤 작업을 실행하는 동안 처리가 끝나기를 `기다리지 않고 다른 작업`을 처리하는 것
  * 멀티스레딩 : `여러 개의 스레드로 작업`을 처리

#### 2.2.1 비동기 처리 시 주의할 점
##### 메모리와 캐시
  * 캐시로 인하여 필드가 가르키는 값과 실제 메모리가 가리키는 값은 동일하지 않을 수 있음

##### 원자성(Atomicity)
  * 일련의 처리 흐름이 분할되서는 안되는 것
  * 더이상 쪼개질 수 없는 성질
  * `volatile` 제한자를 통해 원자성을 유지, 멀티스레드 환경에서는 원자성 유지할 수 없음
  * `volatile` 제한자 : 업데이트한 값을 반드시 메모리에 반영하는 제한자
  
#### 2.2.2 비동기 처리 시 발생하는 문제에 대한 대응 방안
##### final 제한자와 불변 객체(Immutable object)
  * 재할당이 불가능한 변수 사용
  * 상태가 변하지 않는 객체를 사용 (내부 필드에도 final 제한자 사용)
  
```java
// 상태가 변하지 않는 객체
public final class ImmutableObject {
    // 재할당이 불가능한 변수
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
  * `객체 캡슐화 기법`으로 new를 통한 객체생성을 은닉
  * 객체를 생성하는 static 메서드를 선언하는 기법
```java
public class StaticFactoryMethod {

    private StaticFactoryMethod() {
        // 생성자 은익
    }
    
    // static 메서드 선언
    public static StaticFactoryMethod newStaticFactoryMethod() {
        return new StaticFactoryMethod();
    }   
}
```
* 싱글톤(Singletone) 패턴
  * 매번 인스턴스를 생성하는 것이 아니라 하나만 만들고 `인스턴스를 재사용`
```java
public class SingleTone {

    public static SingleTone instance = new SingleTone();
    
    private SingleTone() {}       

    public static SingleTone getInstance() {
        return instance;
    }   
}
```

##### 공유되는 가변 객체 다루기
  * 가변 객체를 다루어야 할 경우 `메모리 캐시`와 `원자성` 문제를 해결해야 함

##### volatile 제한자
  * 즉시 메모리 값을 변경하여, 최신 메모리 값을 가져오도록 보장
  * 멀티 스레드 환경에서 값을 업데이트 할 때 원자성을 보장하지 않음
  * 싱글 스레드(특정 스레드)로 데이터를 업데이트 하도록 구현해야함
  
#### 2.2.3 java.util.concurrent.atomic 패키지
  * `원자성`은 일련의 동작(읽기, 변경, 쓰기)을 외부에서 `분할할 수 없는 특성`
  * 일련의 동작 처리중 외부에서 접근하지 못하도록 해야함
  * `java.util.concurrent.atomic` 패키지에 제공
  * 자신의 클래스 변경 처리에 대해서만 원자성 보장

##### synchronized 블록
  * 하나의 스레드가 synchronized 블록을 처리하는 도중 `다른 스레이드의 접근을 배차 제어함`
    * synchronized 대상 객체를 락 객체(Lock Object)라고 함
  * 데이터 불일치를 막을 수 있음
  * 교착 상태(deadlock)에 주의해야 함
    * 교착 상태 : 서로 다른 스레드가 각각 상태의 락 객체를 소유하고자 할때 `무한 대기 상태`에 빠지는 것을 말함
  * synchronized 대상 객체가 참조형 객체일 때는 복사복을 전달
  
### ExecutorService
  * 비동기모드로 Tasks를 실행하는 JDK에서 제공하는 프레임워크
  * ExecutorService 인터페이스 제공
  * Executors 팩토리 클래스 제공
```java
ExecutorService executorService = Executors.newFixedThreadPool(10);
```
  * 수동으로 설정값을 설정하지 않고, 생성자를 통해 간편하게 생성 가능
```java
ExecutorService executorService = 
    new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILISECONDS, new LinkedBlockingQueue<Runnable>());
```
  * ThreadPool
     * FixedThreadPool : 고정된 개수만큼의 멀티 스레트 Pool 생성
     * SingleThreadPool : 싱글 스레드 Pool 생성
     * CachedThreadPool : 필요에 따라 새로운 스레드를 생성
     * ScheduledThreadPool : 일정한 시간에 따라 스케줄되는 스레드 Poll 생성
  * Callable, Runnable Tasks
    * Callable
      * Callable Object를 리턴
      * Task를 인자로 하고, 그에따른 결과값을 리턴
    * Runnable
      * 
  * 사용 가능한 메서드
    * execute(Runnable Task) : 실행
    * submit(Runnable/Callable Task) : 결과를 리턴하는 실행 
    * invokeAny(Callable Task) : 여러 Tasks를 수행하고 성공적으로 끝난 하나의 결과를 리턴
    * invokeAll(Callable Task) : 여러 Tasks를 수행하고 Future 타입의 리스트를 리턴
  * ExecutorService의 종료
    * 자동으로 종료되지 않음
    * 새로운 작업이 들어올 때까지 대기
    * shutdown() : 모든 동작중인 스레드가 종료되고 종료됨
    * shutdownNow() : 즉시 종료를 시도하며, 모든 동작중인 스레드가 동시에 종료되는 것은 보장되지 않음
  * Future 
    * Tasks 실행 결과 리턴, 상태 체크를 위한 인터페이스
    * get() : Callable tasks의 실제 결과 리턴 
  * ScheduledExecutorService
    * 사전에 설정한 우선순위 및 스케줄에 의해 Tasks를 수행
    * 
        
```java
public static <T> Callable<T> callable(Runnable task, T result)
```
    
```java
// 결과 값 없이 실행
Runnable runnableTask = () -> {
    try {
        TimeUnit.MILLISECONDS.sleep(300);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
};

// 결과 값 리턴
Callable<String> callableTask = () -> {
    TineUnit.MILLISECONDS.sleep(300);
    return "Task's execution";
};
```

```java
ScheduleExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
Future<String> resultFuture = executorService.schedule(callableTask, 1, TimeUnit.SECONDS);
 

```

##### Reference
  * [Java Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html)

#### 기타
  * 병렬 스트림
    * 별렬 스트림을 사용하지 않는 것을 권장
    * Fork-join Pool