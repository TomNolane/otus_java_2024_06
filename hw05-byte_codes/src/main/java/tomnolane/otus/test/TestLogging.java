package tomnolane.otus.test;

import tomnolane.otus.annotation.Log;

public class TestLogging implements TestLoggingInterface {
    @Log
    public void calculation(int param) {
    }

    @Log
    public void calculation(int param1, int param2) {
    }

    @Log
    public void calculation(int param1, int param2, String param3) {
    }
}
