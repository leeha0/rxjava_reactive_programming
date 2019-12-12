package chapter2.model;

import java.util.Date;

public final class ImmutableObject {

    private final Date value;

    public ImmutableObject(Date value) {
        // 기본적인 Date가 변경돼도 영향이 없도록 복제한 값을 설정한다.
        this.value = (Date) value.clone();
    }

    public Date getValue() {
        // 반환값 Date가 외부에서 변경돼도 영향이 없도록 복제한 값을 반환한다.
        return (Date) value.clone();
    }

    public ImmutableObject changeValue(Date value) {
        return new ImmutableObject(value);
    }
}
