# Chapter4
* 데이터를 통지하는 Flowable/Observable을 생성하는 메서드를 연산자(operator)라고 부름
* 연산자의 범주
  * Flowable/Observable을 생성
  * 통지 데이터를 변환
  * 통지 데이터를 제한
  * Flowable/Observable 결합
  * Flowable/Observable 상태 통지
  * Flowable/Observable 데이터 집계
  * 유틸리티
  
### 4.1 Flowable/Observable을 생성하는 연산자
#### 4.1.1 just
* 인자의 데이터를 통지하는 Flowable/Observable 생성
#### 4.1.2 fromArray/fromIterable
* 배열이나 Iterable에서 Flowable/Observable 생성
* 순서대로 통지
#### 4.1.3 fromCallable
* Callable의 처리를 실행하고 그 결과를 통지하는 Flowable/Observable 생성
#### 4.1.4 range/rangeLong
* 지정한 시작 숫자부터 지정한 개수만큼 통지하는 Flowable/Observable 생성
#### 4.1.5 interval
* 지정한 간격마다 숫자를 통지하는 Flowable/Observable 생성
* 0부터 시작하는 Long 타입의 숫자 데이터 통지
* 기본적으로 Schedulers.computation() 스케줄러에서 실행
#### 4.1.6 timer
* 지정 시간 경과 후 0을 통지하는 Flowable/Observable 생성
* 기본적으로 Schedulers.computation() 스케줄러에서 실행
#### 4.1.7 defer
* 구독될 때마다 새로운 Flowable/Observable 생성
* 호출 시점에서 데이터가 생성 됨
#### 4.1.8 empty
* 빈 Flowable/Observable 생성
* flatMap 메서드에서 데이터가 null일 때 이를 대신하여 empty 메서드로 생성한 Flowable/Observable로 통지 대상 제외 처리
#### 4.1.9 error
* 에러만 통지하는 Flowable/Observable 생성
* flatMap 메서드를 처리하는 도중 에러를 통지하고 싶을 때 error 메서드로 생성한 Flowable/Observable로 명시적으로 에러 통지
#### 4.1.10 never
* 아무것도 통지하지 않는 Flowable/Observable 생성
* 완료도 통지하지 않음

### 4.2 통지 데이터를 변환하는 연산자
#### 4.2.1 map
* 데이터를 변환 통지
* 하나의 데이터는 하나의 데이터로 변환
* null 반한시 NullPointerException 발생

> map(Function<? super T, ? extends R> mapper)
> * mapper : 받은 데이터를 어떻게 변환할지 정의하는 함수형 인터페이스

```java
// String 데이터를 BigDecimal로 변환한다.
new Function<String BigDecimal>() {
    @Override
    public BigDecimal apply(String data) throws Exception {
        return new BigDecimal(data);
    }
}
```

#### 4.2.2 flatMap
* 받은 데이터를 Flowable/Observable로 변환하고 이 Flowable/Obserable의 데이터를 통지
* 하나의 데이터는 여러 데이터로 변환하여 통지 가능
* onErrorMapper, onCompleteSupplier는 둘 중 하나의 함수형 인터페이스만 실행됨

> flatMap(Function<? super T, ? extends Publisher/ObservableSource<? extends R>> mapper)
> * mapper : 받은 데이터로 새로운 Flowable/Observable을 생성하는 방법을 정의하는 함수형 인터페이스
>
> flatMap(Function<? super T, ? extends Publisher/ObservableSource<? extends R>> mapper, BiFunction<? super T, ? super U, ? extends R> combiner)
> * combiner : mapper가 새로 생성한 Flowable/Observable 데이터와 원본 데이터를 조합해 새로운 통지 데이터를 생성하는 함수형 인터페이스
>
> flatMap(Function<? super T, ? extends Publisher/ObservableSource<? extends R>> onNextMapper, Function<? super Throwable, ? extends Publisher/ObservableSource<? extends R>> onErrorMapper, Callable<? extends Publisher<? extends R>> onCompleteSupplier)
> * onNextMapper : 받은 데이터로 새로운 Flowable/Observable을 생성하는 방법을 정의하는 함수형 인터페이스
> * onErrorMapper : 에러가 통지됐을 때 무엇을 통지할지 정의하는 함수형 인터페이스
> * onCompleteSupplier : 완료가 통지됐을 때 무엇을 통지할지 정의하는 함수형 인터페이스

```java
// flatMap 메서더의 mapper 구현 예시
new Function<Integer, Flowable<? extends Integer>() {
    @Override
    public Flowable<? extends Integer> apply(Integer data) throws Throwable {
        // 받은 데이터를 2번 통지한다.
        return Flowable.just(data, data);
    }
};

new BiFunction<Integer, Long, String>() {

    // @param sourceData 원본 Flowable/Observable의 데이터
    // @param newData magger로 생성한 Flowable/Obserable의 데이터
    
    @Override
    public String apply(Integer sourceData, Long newData) {
        return "[" + sourceData + "]" + newData;
    }
};

// 평상시 데이터
new Function<Integer, Publisher<? extends Integer>>() {
    @Override
    public Publisher apply(Integer data) throws Exception {
        // 받은 데이터를 두 번 통지한다.
        return Flowable.just(data, data);
    }
}

// 에러가 발생했을 때 데이터
new Function<Throwable, Publisher<? extends Integer>>() {
    @Override
    public Publisher apply(Throwable error) throws Exeption {
        if (error instanceof ArithmeticException) {
            // 에러가 ArithmeticException이면 '-1'을 통지한다.
            return Flowable.just(-1);
        } else {
            // 그대로 에러를 통지한다.
            return Flowable.error(error);
        }   
    }
}

```java
// 완료 통지 시 데이터
new Callable<Publisher<? extends Integer>>() {
    @Override
    public Publisher call() throws Exception {
        // 완료 통지를 받으면 '100'을 통지한다.
        return FLowable.just(100);
    }
}
```

#### 4.2.3 concatMap/concatMapDelayError
* 받은 데이터를 Flowable/Observable로 변환하고 변환한 Flowable/Observable을 하나씩 순서대로 실행해 데이터를 통지
* 서로 다른 스레드에서 실행 될 때 순서대로 실행하며, 순서를 보장하기 때문에 처리 성능에 영향을 줄 수 있음
* concatMapDelayError : 에러가 발생해도 해당 시점까지 받은 데이터로 생성한 Flowable/Observable이 통지를 마칠 때까지 에러 통지를 미룸

> concatMap(Function<? super T, ? extends Publisher/ObservableSource<? extends R>> mapper)
> * mapper : 받은 데이터로 새로운 Flowable/Observable의 생성 방법을 정의하는 함수형 인터페이스
>
> concatMapDelayError(Function<? super T, ? extends Publisher/ObservableSource<? extends R>> mapper)

```java
new Functon<Long, Publisher<? extends String>>() {
    @Override
    public Publisher apply(Long sourceData) throws Exception {
        // 400밀리초 뒤에 0부터 시작하는 숫자를 통지하는 Flowable을 생성한다.
        return Flowable.interval(400L, TimeUnit.MILLISECONDS)
            // 원본 Flowable 데이터와 interval로 생성한 Flowable 데이터를 합쳐 문자열을 생성한다.
            .map(data -> "[" + sourceData + "]" + data);
    }
}
```

#### 4.2.4 concatMapEager/concatMapEagerDelayError
* 받은 데이터를 Flowable/Observable로 변환하자마자 실행, 생성된 Flowable/Observable 순서대로 데이터 통지
* 서로 다른 스레드에서 실행 될 떄는 즉시 실행되지만 데이터 순서를 보장
* 통지 데이터를 버퍼에 쌓고, 통지 순서에 맞추어 통지
* concatMapEagerDelayError : 에러가 발생해도 해당 시점까지 받은 데이터로 생성한 Flowable/Observable이 통지를 마칠 때까지 에러 통지를 미룸

> concatMapEager(Function<? super T, ? extends Publisher/ObservableSource<? extends R>> mapper)
> concatMapEagerDelayError(Function<? super T, ? extends Publisher/ObservableSource<? extends R>> mapper, boolean tillTheEnd)
> * tillTheEnd : 에러가 발생했을 때 true로 설정돼 있으면 모든 데이터를 통지한 후 에러를 통지하고, false로 설정돼 있으면 에러 발생 전까지 생성된 Flowable/Observable의 데이터를 통지한 후 에러를 통지

```java
new Function<Long, Publisher<? extends  String>>() {
    @Override
    public Publisher apply(Long sourceData) throws Exception {
        // 400밀리 초 뒤에 0부터 시작하는 숫자를 통지하는 Flowable을 생성한다.
        return Flowable.interval(400L, TimeUtil.MILLISECONDS)
            // 원본 Flowable의 데이터와 이 Flowable의 데이터를 조합해 문자열을 만든다.
            .map(data -> "[" + soureData + "] " + data);
    }
}
```

#### 4.2.5 buffer
* 통지할 데이터를 지정한 범위까지 모아 리스트나 컬렉션으로 통지
* 데이터 단위는 데이터 개수나 시간 간격을 지정

> buffer(int count)
> * count : 버퍼에 담을 데이터 개수
>
> buffer(long time, TimeUnit unit)
> * time : 버퍼에 데이터를 담는 시간 간격
> * unit : 버퍼에 데이터를 담는 시간 간격의 단위
>
> buffer(Publisher/ObservableSource<\B> boundaryIndicator)
> * boundaryIndicator : 버퍼에 데이터를 담는 간격을 결정하는 데 사용하는 Flowable/Observable
>
> buffer(Callable<? extends Publisher/ObservableSource<\B>> boundaryIndicatorSupplier)
> * boundaryIndicatorSupplier : 호출되면 버퍼링을 시작하고 반환되는 FLowable/Observable
> 
> buffer(Flowable/Observable<? extends TOpening> openingIndicator, Function<? super TOpening, ? extends Publisher/ObservableSource<? extends TClosing>> closingIndicator)
> * openingIndicator : 데이터를 통지할 때 해당 시점의 데이터를 버퍼에 쌓는 Flowable/Observable
> * closingIndicator : 버퍼링을 시작할 때 openingIndicator가 통지한 데이터를 받으면 버퍼링을 종료하는 Flowable/Observable

```java
// 호출되면 1000밀리초 뒤에 데이터를 통지하는 Flowable을 생성한다.
new Supplier<Flowable<Long>>() {
    @Override
    public Flowable<Long> get() {
        return Flowable.timer(1000L, TimeUnit.MILLISECONDS);
    }
};

// 버퍼링을 시작할 때 호출돼 버퍼링을 종료할 때 사용할 Flowable을 생성한다.
new Function<Long, Flowable<Long>>() {
    @Override
    public Flowable<Long> apply(Long openingData) throws Exception {
        return Flowalbe.timer(1000L, TimeUnit.MILLISECONDS);
    }   
}
```

#### 4.2.6 toList
* 통지할 데이터를 모두 리스트에 담아 통지
* 원본 Flowable/Observable에서 완료 통지를 받은 시점에 결과 리스트 통지
* 메모리가 부족하게 될 위험성이 있으므로 사용 시 주의
* 통지 데이터가 하나뿐이므로 Single을 반환

> toList()  
> toList(Callable<\U> collectionSupplier)
> * collectionSupplier : 데이터를 담는 객체를 생성하는 함수형 인터페이스

```java
// 데이터를 담을 리스트를 생성한다.
new Callable<CopyOnWriteArrayList<String>>() {
    @Override
    public CopyOnWriteArrayList<String> call() throws Exception {
        return new CopyOnWriteArrayList<String>();
    }
}
```

#### 4.2.7 toMap
* 통지할 데이터를 키와 값 한 쌍으로 Map에 담아 통지
* 원본 Flowable/Observable에서 완료 통지를 받는 시점에 결과 Map 통지
* 메모리가 부족하게 될 위험성이 있으므로 사용 시 주의
* 통지 데이터가 하나뿐이므로 Single을 반환
* 이미 사용 중인 키를 생성하면 이 키와 새로운 데이터의 쌍이 이전에 들어있던 데이터 쌍을 엎어쓰게 됨

> toMap(Function<? super T, ? extends K> keySelector)
> * keySelector : 키를 생성하는 함수형 인터페이스
> 
> toMap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector)
> * valueSelector : 값을 생성하는 함수형 인터페이스
> 
> toMap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector, Callable<? extends Map<K, V>> mapSupplier)
> * mapSupplier : 키와 값을 담을 Map객체를 생성하는 함수형 인터페이스

```java
// 처음 4문자를 잘라 Long으로 변환한다.
new Function<String, Long>() {
    @Override
    public Long apply(String data) throws Exceptino() {
        return Long.valueOf(data.substring(0, 4));
    }   
};

// 키 부분을 제외한 나머지가 값이 된다.
new Function<String, String>() {
    @Override
    public String apply(String data) throws Exception() {
        return data.substring(4);
    }
};

// 데이터를 담을 Map을 생성한다.
new Callable<LinkedHashMap<String, Object>>() {
    @Override
    public LinkedHashMap<String, Object> call() throws Exception() {
        return new LinkedHashMap<String, Object>(); 
    }
};
```

#### 4.2.8 toMultimap
* 통지할 데이터를 키와 컬렉션의 쌍으로 Map에 담아 통지
* 원본 Flowable/Observable에서 완료 통지를 받는 시점에 결과 Map 통지
* 메모리가 부족하게 될 위험성이 있으므로 사용 시 주의
* 통지 데이터가 하나뿐이므로 Single을 반환
* 이미 사용 중인 키를 생성하면 이 키에 연결된 컬렉션에 값을 추가

> toMultimap(Function<? super T, ? extends K> keySelector)
> * keySelector : 키를 생성하는 함수형 인터페이스
>
> toMultimap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector)
> * valueSelector : 값을 생성하는 함수형 인터페이스
>
> toMultimap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector, Callable<? extends Map<K, Collection<V>>> mapSupplier)
> * mapSupplier : 통지할 Map객체를 생성하는 함수형 인터페이스
>
> toMultimap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector, Callable<? extends Map<K, Collection<V>>> mapSupplier, Function<? super K, ? extends Collection<? super V>> collectionFactory)
> * collectionFactory : 키를 바탕으로 Map에 값으로 담을 컬렉션 객체를 생성하는 함수형 인터페이스

```java
// 처음 4문자를 잘라 Long으로 변환한다.
new Function<String, Long>() {
    @Override
    public Long apply(String data) throws Exception {
        return Long.valueOf(data.substring(0, 4));
    }
}

// 키 부분을 제외한 나머지가 값이 된다.
new FUnction<String, String>() {
    @Override
    public String apply(String data) throws Exception {
        return data.substring(4);
    }
}

// 데이터를 담을 Map을 생성한다.
new Callable<LinkedHashMap<String, Collection<String>>>() {
    @Override
    public LinkedHashMap?<String, Collection<String>> call() throws Exception {
        return new LinkedHashMap<String, Collection<String>>();
    }
}

// 키를 바탕으로 컬렉션을 생성한다.
new Function<String, Collection<String>>() {
    @Override
    public Collection<String> apply(String key) throws Exception {
        if (key.equals("Set")) {
            return new HashSet<String>();
        } else {
            return new ArrayList<>();
        }
    }
}
```

### 4.3 통지 데이터를 제한하는 연산자
#### 4.3.1 filter
* 지정한 조건에 맞는 데이터만 통지
* 판정 결과가 true인 것만 통지

> filter(Predicate predicate)
> * predicate : 받은 데이터를 판정해 조건에 맞으면 true를 반환하는 함수형 인터페이스

```java
// 짝수면 통지한다.
new Predicate<Long>() {
    @Override
    public boolean test(Long data) throws Exception {
        return data % 2 == 0;
    }
}
```
#### 4.3.2 distinct
* 이미 통지한 데이터와 같은 데이터를 제외하고 통지
* 내부적으로 HashSet을 통해 데이터가 같은지 확인

> distinct()  
> distinct(Function<? super T, K> keySelector)
> * keySelector ; 받은 데이터와 비교할 데이터를 생성하는 함수형 인터페이스

```java
// 받은 데이터를 소문자로 바꾼다.
new Function<String, String>() {
    @Override
    public String apply(String data) throws Exception {
        return data.toLowerCase();
    }
};
```
#### 4.3.3 distinctUntilChanged
* 연속된 같은 값의 데이터는 제외하고 통지

> distinctUntilChanged()  
> distinctUntilChanged(Function<? super T, K> keySelector)
> * keySelector : 받은 데이터와 비교할 데이터를 생성하는 함수형 인터페이스
>
> distinctUntilChanged(BiPredicate<? super T, ? super T> comparer)
> * comparer : 바로 앞 데이터와 현재 데이터가 같은지를 판단하는 함수형 인터페이스

```java
// 받은 데이터를 소문자로 변환한다.
new Function<String, String>() {
    @Override
    public String apply(String data) throws Exception {
        return data.toLowerCase();
    }
}

new BiPredicate<BigDecimal, BigDecimal>() {
    @Override
    public boolean test(BigDecimal data1, BigDecimal data2) throws Exception {
        return (data1.compareTo(data2) == 0);
    }
}
```

#### 4.3.4 take
* 지정한 개수나 기간까지만 데이터를 통지

> take(long count)
> * count : 통지할 수 있는 데이터 개수
>
> take(long time, TimeUnit unit)
> * time : 데이터를 통지할 수 있는 기간
> * unit : 데이터를 통지할 수 있는 기간의 단위

#### 4.3.5 takeUntil
* 지정된 조건에 도달할 때까지 통지

> takeUntil(Predicate<? super T> stopPredicate)
> * stopPredicate : 받은 데이터를 판단해 통지를 끝낼 조건이 되면 ture를 반환하는 함수형 인터페이스
>
> takeUntil(Publisher/ObservableSource<\U> other)
> * other : 첫 번째 데이터의 통지 시점 또는 완료 시점에 결과로 데이터 통지를 멈추고 완료하게 하는 Flowable/Observable

```java
// 받은 데이터가 '3'이 될 때까지 통지한다.
new Predicate<Long>() {
    @Override
    public boolean test(Long data) throws Exception {
        return data == 3;
    }
}
```

#### 4.3.6 takeWhile
* 지정한 조건에 해당할 때만 데이터 통지
* true일 동안 받은 데이터를 그대로 통지하며, false면 완료를 통지하고 처리를 종료

> takeWhile(Predicate predicate)
> * predicate : 받은 데이터를 판단해 통지 조건을 충족하면 true를 반환하는 함수형 인터페이스

```java
// 통지할 데이터가 '3'이 아니면 통지한다.
new Predicate<Long>() {
    @Override
    public boolean test(Long data) throws Exception {
        return data != 3;
    }
}
```

#### 4.3.7 takeLast
* 끝에서부터 지정한 조건까지의 데이터 통지
* 원본 Flowable/Observable의 완료 통지시 데이터 통지

> takeLast(int count)
> * count : 결과로 통지하려고 끝에서부터 세는 데이터 개수
>
> takeLast(long time, TimeUnit unit)
> * time : 통지할 데이터를 결정할 대상 기간
> * unit : 통지할 데이터를 결정할 대상 기간의 단위
>
> takeLast(long count, long time, TimeUnit unit)

#### 4.3.8 skip
* 앞에서부터 지정된 범위까지의 데이터는 통지 대상에서 제외
* 범위는 데이터 개수나 경과 시간으로 설정

> skip(long count)
> * count : 통지 제외 데이터 개수
>
> skip(long time, TimeUnit unit)
> * time : 통지 제외 기간
> * unit : 통지 제외 기간의 단위

#### 4.3.9 skipUntil
* 인지 Flowable/Observable이 데이터를 통지할 때까지 데이터 통지를 건너뜀

> skipUntil(Publisher/ObservableSource other)
> * other : 첫 번째 데이터를 통지하거나 완료를 통지할 때까지 데이터 통지를 건너뛰게 하는 Flowable/Observable

#### 4.3.10 skipWhile
* 지정한 조건에 해당할 때는 데이터 통지 제외
* 지정한 조건이 true이면 데이터를 통지하지 않고, false이면 해당 시점부터 데이터를 통지

> skipWhile(Predicate predicate)
> * predicate : 받은 데이터를 판단해 통지를 시작할 수 없으면 true를 반환하는 함수형 인터페이스

```java
// 데이터가 3이 될 때까지 통지하지 않는다.
new Predicate<Long>() {
    @Override
    public boolean test(Long data) throws Exception {
        return data != 3;
    }
}
```
#### 4.3.11 skipLast
* 끝에서부터 지정한 범위만큼 데이터 통지 제외
* 범위는 데이터 개수나 시간으로 설정

> skipLast(int count)
> * count : 통지하지 않을 데이터 개수
>
> skipLast(int time, TimeUnit unit)
> * time : 데이터를 통지하지 않을 시간
> * unit : 데이터를 통지하지 않을 시간의 단위

#### 4.3.12 throttleFirst
* 데이터 통지 후 지정 시간 동안 데이터를 통지하지 않음
* 지정 시간동안 데이터는 파기 됨
* 단시간에 대량으로 들어오는 데이터가 모두 필요한 것이 아닌 경우 활용가능

> throttleFirst(long time, TimeUnit unit)
> * time : 데이터를 파기하는 시간
> * unit : 데이터를 파기하는 시간의 단위

#### 4.3.13 throttleLast/sample
* 지정한 시간마다 가장 마지막에 통지된 데이터만 통지
* throttleLast, sample 메서드는 동일한 처리 작업을 수행

> throttleLast(long time, TimeUnit unit)
> * time : 간견으로 지정할 시간
> * unit : 간견으로 지정할 시간 단위
>
> sample(long time, TimeUnit unit)  
> sample(Publisher/ObservableSource<\U> sampler)
> * sampler : 데이터 통지 간견을 결정하는 Flowable/Observable. 이 Flowable/Observable이 통지할 때 원본 Flowable/Observable의 가장 마지막으로 받은 데이터 통지

#### 4.3.14 throttleWithTimeout/debounce
* 데이터를 받은 후 일정 기간 안에 다음 데이터를 받지 못하면 현재 데이터를 통지
* throttleWithTimeout, debounce 메서드는 동일한 처리 작업을 수행

> throttleWithTimeout(long time, TimeUnit unit)
> * time : 지정하려는 시간
> * unit : 지정하려는 시간의 단위
>
> debounce(long time, TimeUnit unit)  
> debounce(Function<? super T, ? Publisher/ObservableSource<\U>> debounceIndicator)
> * debounceIndicator : 데이터를 통지하지 않고 건너뛸 시간을 결정하는 Flowable/Observable, 이 Flowable/Observable이 통지할 때까지 다른 데이터가 통지되지 않으면 먼저 받은 데이터 통지

```java
// 데이터를 받으면 500밀리초 뒤에 데이터를 통지하는 Flowable을 생성한다.
new Function<String, Publisher<Long>>() {
    @Override
    public Publisher<Long> apply(String data) throws Exception {
        return Flowable.timer(500L, TimeUnit.MILLISECONDS);
    }
}
```

#### 4.3.15 elementAt/elementAtOrError
* 지정한 위치의 데이터만 통지
* Single이나 Maybe를 반환
* elementAt의 경우 인자에 따라 반환 값이 바뀜

> elementAt(long index)
> * index : 지정한 위치, 데이터가 없으면 Maybe 반환
>
> elementAt(long index, T defaultItem)
> * index : 지정한 위치
> * defaultItem : 데이터가 없는 경우 디폴트 값 리턴, Single 반환
>
> elementAtOrError(long index)
> * index : 지정한 위치, 데이터가 없으면 NoSuchElementException 에러를 통지하는 Single 반환

### 4.4 Flowable/Observable을 결합하는 연산자
#### 4.4.1 merge/mergeDelayError/mergeArray/mergeArrayDelayError/mergeWith
* 여러 개의 Flowable/Observable을 하나로 병합하고 동시 실행
* 결과를 통지할 때는 동기화돼 순차적으로 통지됨
* 모든 Flowable/Observable이 완료될 때 완료를 통지
* 최대 네 개까지 인자를 전달 가능

##### 관련 연산자
* mergeWith : 자신의 통지와 인자 Flowable/Observable의 통지를 병합
* mergeDelayError : 에러 발생시 다른 Flowable/Observable의 데이터를 모두 통지하고 나서 에러를 통지
* mergeArray : 배열을 인자로 받아 하나로 병합
* mergeArrayDelayError : 배열을 인자로 받아 하나로 병합하며, 에러 발생시 다른 Flowable/Observable의 데이터를 모두 통지하고 나서 에러를 통지

> merge(Publisher/ObservableSource<? extends T> source1, Publisher/ObservableSource<? extends T> source2)
> * source1, source2 : 병합되는 Flowable/Observable로 최대 4개까지 설정 가능
> 
> merge(Iterable<? extends Publisher/ObservableSource<? extends T>> sources)
> * sources : 병합된 Flowable/Observable을 저장하는 Iterable이나 Flowable/Observable
> 
> merge(Publisher/Observable<? extends Publisher/ObservableSource<? extends T>> sources)

> mergeArray(Publisher/ObservableSource<? extends T>... sources)
> * sources : 병합되는 Flowable/Observable의 배열, 쉼표로 구분해 지정

> mergeDelayError(Publisher/ObservableSource<? extends T> source1, Publisher/ObservableSource<? extends T> source2)
> mergeDelayError(Iterable<? extends Publisher/ObservableSource<? extends T>> sources)
> mergeDelayError(Publisher/Observable<? extends Publisher/ObservableSource<? extends T>> sources)

> mergeWith(Publisher/ObservableSource<? extends T> other)
> * other : 병합되는 Flowable/Observable

#### 4.4.2 concat/concatDelayError/concatArray/concatArrayDelayError/concatWith
* 여러 개의 Flowable/Observable을 하나씩 실행
* 하나의 Flowable/Observable로 결합한 후에 순차적으로 실행하는 연산자
* 최대 네 개까지 인자를 전달 가능

##### 관련 연산자
* concatDelayError : 에러 발생시 다른 Flowable/Observable의 처리가 완료될 때까지 에러 통지 대기
* concatArray : 배열을 인자로 받아 결합
* concatArrayDelayError : 배열을 인자로 받아 결합하며, 에러 발생시 Flowable/Observable의 데이터를 모두 통지하고 나서 에러를 통지

> concat(Publisher/ObservableSource<? extends T> source1, Publisher/ObservableSource<? extends T> source2)
> * source1, source2 : 병합되는 Flowable/Observable로 최대 4개까지 설정 가능
> 
> concat(Iterable<? extends Publisher/ObservableSource<? extends T>> sources)
> * sources : 병합된 Flowable/Observable을 저장하는 Iterable이나 Flowable/Observable
>
> concat(Publisher/Observable<? extends Publisher/ObservableSource<? extends T>> sources)

> concatDelayError(Iterable<? extends Publisher/ObservableSource<? extends T>> sources)
> concatDelayError(Publisher/Observable<? extends Publisher/ObservableSource<? extends T>> sources)

> concatArray(Publisher/ObservableSource<? extends T>... sources)
> * sources : 결합하는 Flowable/Observable의 배열, 쉼표로 구분해 지정

> concatArrayDelayError(Publisher/ObservableSource<? extends T>... sources)

> concatWith(Publisher/ObservableSource<? extends T> other)
> * 결합하는 Flowable/Observable

#### 4.4.3 concatEager/concatArrayEager
* 여러 개의 Flowable/Observable을 결합해 동시 실행하고 한 건씩 통지
* concat 메서드와 달리 한꺼번에 Flowable/Observable을 실행하며, 캐시에 데이터를 쌓아두고 순서대로 통지

> concatEager(Iterable<? extends Publisher/ObservableSource<? extends T>> sources)
> * sources : 병합된 Flowable/Observable을 저장하는 Iterable이나 Flowable/Observable
>
> concatEager(Publisher/Observable<? extends Publisher/ObservableSource<? extends T>> sources)

> concatArrayEager(Publisher/ObservableSource<? extends T>... sources)
> * sources : 결합하는 Flowable/Observable의 배열, 쉼표로 구분해 지정

#### 4.4.4 startWith/startWithArray
* 인자의 데이터를 통지한 후 자신의 데이터 통지

> startWith(Publisher/ObservableSource<? extends T> other)
> * other : 원본 Flowable/Observable이 통지하기 전에 먼저 통지되는 Flowable/Observable
>
> startWith(Iterable<? extends T> items)
> * items : 원본 Flowable/Observable이 통지하기 전에 먼저 통지되는 데이터의 Iterable
>
> startWith(T value)
> * value : 원본 Flowable/Observable이 통지하기 전에 먼저 통지되는 한 건의 데이터

> startWithArray(T... items)
> * items : 원본 Flowable/Observable이 통지하기 전에 먼저 통지되는 데이터 배열, 쉼표로 구분해 지정

#### 4.4.5 zip/zipWith
* 여러 Flowable/Observable의 데이터를 모아 새로운 데이터를 생성 통지
* 데이터들이 모인 시점에 이 데이터들을 함수형 인터페이스에 전달하고, 새로 생성한 데이터를 결과로 통지
* 완료 통지 시점은 가장 통지 데이터가 가장 적은 Flowable/Observable에 맞추어짐
* 각 Flowable/Observable은 동일한 순서로 통지, 통지 순서에 대한 차이 발생시 가장 느린 속도에 맞춤

> zip(Publisher/ObservableSource<? extends T> source1, Publisher/ObservableSource<? extends T> source2, BiFunction<? super T1, ? super T2, ? extends R> zipper)
> * source1, source2 : 새로운 데이터를 생성하려고 결합하는 Flowable/Observable로, 최대 9개까지 지정 가능
> * zipper : 새로운 데이터를 생성하는 함수형 인터페이스 (Function3, Function4 인터페이스도 사용 가능)
> 
> zip(Iterable<? extends Publisher/ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> zipper)
> * sources : 새로운 데이터를 생성하려고 결합하는 Flowable/Observable을 저장하는 Iterator이나 Flowable/Observable
> * zipper : 인자의 각 Flowable/Observable로부터 데이터를 배열로 받아 새로운 데이터를 생성하는 함수형 인터페이스
>
> zip(Publisher/Observable<? extends Publisher/ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> zipper)

```java
// 각 Flowable/Observable이 통지한 데이터를 저장하는 리스트를 생성한다.
new BiFunction<Long, Long, List<Long>>() {
    @Override
    public List<Long> apply(Long data1, Long data2) throws Exception {
        return Arrays.adList(data1, data2);
    }
}
```

> zipWith(Publisher/ObservableSource<? extends U> other, BiFUnction<? super T, ? super U, ? extends R> zipper)
> * other : 새로운 데이터를 생성하고자 결합하는 Flowable/Observable
> * zipper : 원본 Flowable/Observable이 통지하는 데이터와 인자로부터 받은 데이터에서 새로운 데이터를 생성하는 함수형 인터페이스
>
> zipWith(Iterable<\U> otherDatas, BiFUnction<? super T, ? super U, ? extends R> zipper)
> * otherDatas : 새로운 데이터를 생성하는 결합 데이터를 저장하는 Iterable

```java
// 원본 통지 데이터와 인자 Flowable/Observable의 통지 데이터를 저장하는 리스트 생성
new BiFunction<Long, Long, List<Long>>() {
    @Override
    public List<Long> apply(Long data1, Long data2) throws Exception {
        return Ararys.adList(data1, data2);
    }
}
```

#### 4.4.6 combineLatest/combineLatestDelayError
* 여러 Flowable/Observable에서 데이터를 받을 때마다 새로운 데이터를 생성 통지
* 각 Flowable/Observable이 마지막으로 통지한 데이터를 받아 새로 데이터를 통지하는 연산자

##### 관련 연산자
* combineLatestDelayError : 에러 발생시 다른 Flowable/Observable의 처리가 완료될 때까지 에러 통지 대기

> combineLatest(Publisher/ObservableSource<? extends T> source1, Publisher/ObservableSource<? extends T> source2, BiFunction<? super T1, ? super T2, ? extends R> combiner)
> * source1, source2 : 새로운 데이터를 생성하려고 결합하는 Flowable/Observable, 최대 9개까지 지정 가능
> * combiner : 새로운 데이터를 생성하는 함수형 인터페이스 (Function3, Function4 사용 가능)
> 
> combineLatest(Iterable<? extends Publisher/ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> combiner)
> * sources : 새로운 데이터를 생성하고 결합하는 Flowable/Observable을 저장하는 Iterable이나 Flowable/Observable
> * combiner : 데이터를 배열로 받아 새로운 데이터를 생성하는 함수형 인터페이스
> 
> combineLatest(Publisher/Observable<? extends Publisher/ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> combiner)

> combineLatestDelayError(Function<? super Object[], ? extends R> combiner, Publisher/ObservableSource<? extends T>... sources)
> combineLatestDelayError(<? extends Publisher/ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> combiner)
> combineLatestDelayError(Publisher/Observable<? extends T>[] sources, Function<? super Object[], ? extends R> combiner)

```java
// 인자의 Flowable/Observable이 통지한 데이터를 저장하는 리스트를 생성한다.
new BiFunction<Long long, List<Long>> {
    @Override
    public List<Long> apply(Long data1, data2) throws Exception {
        return Arrays.asList(data1, data2);
    }
}
```

### 4.5 Flowable/Observable 상태를 통지하는 연산자
#### 4.5.1 isEmpty
* Flowable/Observable이 통지할 데이터가 있는지 판단
* Single 반환
* 완료만 통지되면 true, 데이터가 통지되면 false 통지

> isEmpty()

#### 4.5.2 contains
* Flowable/Observable이 지정한 데이터를 포함하는지 판단
* Single 반환
* 지정 데이터를 포함하면 true, 지정 데이터를 포함하지 않거나 통지할 데이터가 없으면 false

> contains(Object item)
> * item : 비교 대상 데이터

#### 4.5.3 all
* Flowable/Observable의 모든 데이터가 조건에 맞는지 판단
* Single 반환
* 모든 데이터가 조건에 맞으면 true, 맞지 않으면 false
* 통지 데이터가 없을 경우에도 true 반환

> all(Predicate<? super T> predicate)
> * predicate : 받은 데이터를 기반으로 판단 조건을 지정하는 함수형 인터페이스로, 구현하는 메서드는 조건이 맞으면 true, 맞지 않으면 false를 반환

```java
// 짝수 여부를 판단한다.
new Predicate<Long>() {
    @Override
    public boolean test(Long data) throws Exception {
        return data % 2 == 0;
    }
}
```

#### 4.5.4 sequenceEqual
* 두 Flowable/Observable이 같은 순서로 같은 수의 같은 데이터를 통지하는지 판단
* Single 반환
* 데이터만 비교하고 통지 시점은 비교하지 않음
* 모든 데이터가 동일하면 true, 그렇지 않다면 false 반환

> sequenceEqual(Publisher/ObservableSource<? extends T> source1, Publisher/ObservableSource<? extends T> source2)
> * source1, source2 : 비교 대상 Flowable/Observable
>
> sequenceEqual(Publisher/ObservableSource<? extends T> source1, Publisher/ObservableSource<? extends T> source2, BiPredicate<? super T, ? super T> isEqual)
> * isEqual : 각 Flowable/Observable이 통지한 같은 인덱스의 데이터를 받아 비교하고 두 데이터가 같으면 true를 반환하는 함수형 인터페이스

```java
// 크기와 상관없이 같은지 판단한다.
new BiPredicate<BigDecimal, BigDecimal>() {
    @Override
    public boolean test(BigDecimal data1, BigDecimal data2) throws Exception {
        return (data1.compareTo(data2) == 0);
    }
}
```
#### 4.5.5 count
* Flowable/Observable의 데이터 개수 통지
* Single 반환
* 완료 통지 시점에 통지
* 데이터 개수는 Long 타입

> count()

### 4.6 Flowable/Observable 데이터를 집계하는 연산자 (~1/14)
#### 4.6.1 reduce/reduceWith
* Flowable/Observable의 데이터를 계산하고 최종 집계 결과만 통지
* 초기값을 받을 경우 Single, 초기값이 없을 경우 Maybe를 반환

```java
// 받은 데이터를 더한다.
new BiFunction<Integer, Integer, Integer>() {
    // @param sum 집계값
    // @param data Flowable/Obervable이 통지한 데이터
    public Integer apply(Integer sum, Integer data) throws Exception {
        // 데이터를 더한다
        return sum + data;
    }
}
```

#### 4.6.2 scan
* Flowable/Observable의 데이터를 계산하고 각 계산 결과를 통지
* 초기 값이 있으면 초기값을 첫 번째 데이터로 통지

```java
// 받은 데이터를 더한다.
new BiFunction<Integer, Integer, Integer>() {
    /**
    * @param sum 집계값
    * @param data Flowable/Observable이 통지한 데이터
    */
    @Override
    public Integer apply(Integer, sum, Integer data) throws Exception {
        // 데이터를 더한다.
        return sum + data;
    }
}
```

### 4.7 유틸리티 연산자
#### 4.7.1 repeat
* 데이터 통지를 처음부터 반복
* 인자에 0보다 작은 값을 전달하면 IllegalArgumentException 발생
* 인자에 0 값을 전달하면 완료만 통지하는 빈 Flowable/Observable 수행

#### 4.7.2 repeatUntil
* 지정한 조건이 될 때까지 데이터 통지 반복
* false면 통지를 반복하고 true면 완료를 통지하고 처리를 종료

```java
// 처리 시작 시각으로부터 500밀리초가 지나면 true를 반환한다.
new BooleanSupplier() {
    @Override
    public boolean getAsBoolean() throws Exception {
        return System.currentTimeMillis() - startTime > 500L;
    }
};
```

#### 4.7.3 repeatWhen
* 반복 처리 정의 및 통지 반복
* 지정한 시점까지 원본 Flowable/Observable의 통지를 반복하는 연산자
* 원본 Flowable/Observable이 완료를 통지하는 시점에 데이터를 통지하는 Flowable/Observable을 인자로 받고 이를 변환해 반환
* 변환된 Flowable/Observable이 데이터를 통지하면 반복하고, 완료를 통지하면 완료를 통지하고 처리를 끝냄
* 변환된 Flowable/Observable이 빈 Flowable/Observable이라면 완료를 통지하고 종료

```java
// 반복 시점을 1000밀리초 지연하고 같은 데이터를 2번 통지한다.
new Function<Flowable<Object>, Publisher<Object>>() {
    @Override
    public Publisher<Object> apply(Flowalbe<Object> completeHandler) throws Exception {
        return completeHandler
            // 통지 시점을 늦춘다.
            .delay(1000L, TimeUnit.MILLISECONDS)
            // 2번 통지한다.
            .take(2);
    }  
}
```

#### 4.7.4 delay
* 처리 시작 및 데이터 통지 시점 지연
* 구독 시작 시간은 지연 되지 않음

```java
// 받은 데이터의 값을 통지하는 시각을 지연한다.
new Function<Long, Flowable<Long>>() {
    @Override
    public Flowable<Long> apply(Long data) throws Exception {
        return Flowable.timer(data, TimeUnit.MILLISECONDS);
    }
}
```

#### 4.7.5 delaySubscription
* 처리 시작 지연
* 구독 시작 시간이 지연됨

#### 4.7.6 timeout
* 데이터 통지의 타임아웃 설정
* 설정된 시간을 넘기면 대체 데이터를 통지하거나, TimeoutException 에러를 통지

```java
// 데이터를 받고 1000밀리초 뒤에 이 데이터를 통지하는 Flowable을 생성한다.
new Function<Integer, Flowable<Long>>() {
    @Override
    public Flowable<Long> apply(Integer data) throws Exception {
        return Flowable.timer(1000L, TimeUnit.MILLISECONDS);
    }
}
```