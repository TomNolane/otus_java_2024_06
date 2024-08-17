package tomnolane.otus;

import tomnolane.otus.proxy.Ioc;
import tomnolane.otus.test.TestLogging;
import tomnolane.otus.test.TestLoggingInterface;

public class Demo {
    public static void main(String[] args) {
        var testLogging = (TestLoggingInterface) Ioc.createClass(new TestLogging());

        testLogging.calculation(6);
        testLogging.calculation(12, 24);
        testLogging.calculation(48, 96, "192");
    }
}
