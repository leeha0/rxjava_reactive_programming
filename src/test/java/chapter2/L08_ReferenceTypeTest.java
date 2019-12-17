package chapter2;

import chapter2.model.ReferenceTypeObject;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

public class L08_ReferenceTypeTest {

    @Test
    public void changeFinalReferenceTypeObject() {
        // final이 붙은 참조 변수
        final ReferenceTypeObject instance = new ReferenceTypeObject();

        // 참조를 변경하면 컴파일 에러가 발생한다.
        // instance = new ReferenceTypeObject();

        // 변경 전 객체 상태를 확인한다.
        assertThat(instance.getValue(), is("A"));

        // instance의 상태를 변경할 수 있다.
        instance.setValue("B");

        // 상태를 변경한 객체를 확인힌다.
        assertThat(instance.getValue(), is("B"));
    }
}
