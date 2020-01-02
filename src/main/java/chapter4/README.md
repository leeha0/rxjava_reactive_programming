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
* null은 반한시 NullPointerException 발생
#### 4.2.2 flatMap
#### 4.2.3 concatMap/concatMapDelayError
#### 4.2.4 concatMapEager/concatMapEagerDelayError
#### 4.2.5 buffer
#### 4.2.6 toList
#### 4.2.7 toMap
#### 4.2.8 toMultimap

### 4.3 통지 데이터를 제한하는 연산자 (~1/7)
#### 4.3.1 filter
#### 4.3.2 distinct
#### 4.3.3 distinctUntilChanged
#### 4.3.4 take
#### 4.3.5 takeUntil
#### 4.3.6 takeWhile
#### 4.3.7 takeLast
#### 4.3.8 skip
#### 4.3.9 skipUntil
#### 4.3.10 skipWhile
#### 4.3.11 skipLast
#### 4.3.12 throttleFirst
#### 4.3.13 throttleLast/sample
#### 4.3.14 throttleWithTimeout/debounce
#### 4.3.15 elementAt/elementAtOrError

### 4.4 Flowable/Observable을 결합하는 연산자 (~1/9)
#### 4.4.1 merge/mergeDelayError/mergeArray/mergeArrayDelayError/mergeWith
#### 4.4.2 concat/concatDelayError/concatArray/concatArrayDelayError/concatWith
#### 4.4.3 concatEager/concatArrayEager
#### 4.4.4 startWith/startWithArray
#### 4.4.5 zip/zipWith
#### 4.4.6 combineLatest/combineLatestDelayError

### 4.5 Flowable/Observable 상태를 통지하는 연산자 (~1/14)
#### 4.5.1 isEmpty
#### 4.5.2 contains
#### 4.5.3 all
#### 4.5.4 sequenceEqual
#### 4.5.5 count

### 4.6 Flowable/Observable 데이터를 집계하는 연산자
#### 4.6.1 reduce/reduceWith
#### 4.6.2 scan

### 4.7 유틸리티 연산자
#### 4.7.1 repeat
#### 4.7.2 repeatUntil
#### 4.7.3 repeatWhen
#### 4.7.4 delay
#### 4.7.5 delaySubscription
#### 4.7.6 timeout
