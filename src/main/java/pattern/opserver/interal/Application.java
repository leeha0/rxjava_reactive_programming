package pattern.opserver.interal;

import pattern.opserver.interal.Function2;
import pattern.opserver.interal.subject.impl.ProductLogInfo;

public class Application {
    public static void main(String[] args) {
        ProductLogInfo productLogInfo = new ProductLogInfo();
        // Subjects : Push & Pull
        // 1. 서브 클래스 생성
        // 2. 자바 내장 Observer API에 맞도록 구현
        // 3. setChanged() 메소드는 protected
        // 4. 멀티 쓰레드 구현 불가

        Function2 function2 = new Function2(productLogInfo);
        // Observers

        productLogInfo.setInfo(1, 0);
        productLogInfo.setInfo(3, 0);
        productLogInfo.setInfo(10, 0);
        productLogInfo.setInfo(12, 1);
    }
}
