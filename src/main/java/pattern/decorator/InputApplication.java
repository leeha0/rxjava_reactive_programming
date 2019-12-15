package pattern.decorator;

import java.io.*;

public class InputApplication {

    public static void main(String[] args) {
        int c;
        try {
            InputStream in = new LowerCaseInputStream(new BufferedInputStream(new FileInputStream("pattern/decorator/test.txt")));

            while((c = in.read()) >= 0) {
                System.out.println(c);
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
