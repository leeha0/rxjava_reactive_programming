package chapter2.executor;

import java.net.Socket;

public class Handler implements Runnable {
    private final Socket socket;

    Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // read and service request on socket
    }
}
