package pattern.opserver.custom;

import pattern.opserver.custom.subject.ProductLogInfo;

public class Application {
    public static void main(String[] args) {
        ProductLogInfo productLogInfo = new ProductLogInfo();
        // Subjects : Push

        ConcreteFunction function1 = new ConcreteFunction(productLogInfo);
        // Observers

        productLogInfo.setInfo(1, 0);
        productLogInfo.setInfo(3, 0);
        productLogInfo.setInfo(10, 0);
        productLogInfo.setInfo(12, 1);
    }
}
