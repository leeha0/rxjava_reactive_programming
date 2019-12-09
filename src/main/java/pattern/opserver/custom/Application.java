package pattern.opserver.custom;

import pattern.opserver.custom.subject.impl.ProductLogInfo;

public class Application {
    public static void main(String[] args) {
        ProductLogInfo productLogInfo = new ProductLogInfo();
        // Subjects : Push

        Function1 function1 = new Function1(productLogInfo);
        // Observers

        productLogInfo.setInfo(1, 0);
        productLogInfo.setInfo(3, 0);
        productLogInfo.setInfo(10, 0);
        productLogInfo.setInfo(12, 1);
    }
}
