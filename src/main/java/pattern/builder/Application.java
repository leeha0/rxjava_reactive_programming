package pattern.builder;

import pattern.builder.model.Log;
import pattern.builder.model.TraditionalLog;

public class Application {

    public static void main(String[] args) {
        Log log = new Log.Builder()
                .setProductId(1)
                .setClickCount(1)
                .setSalesCount(1)
                .build();

        System.out.println(log.toString());

        TraditionalLog traditionalLog = new TraditionalLog();
        traditionalLog.setProductId(1);
        traditionalLog.setClickCount(1);
        traditionalLog.setSalesCount(1);

        System.out.println(traditionalLog.toString());
    }
}
