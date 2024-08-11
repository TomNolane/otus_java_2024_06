package tomnolane.otus.proxy;

import tomnolane.otus.Demo;
import tomnolane.otus.test.TestLoggingInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Ioc {
    private Ioc() {
    }

    public static Object createClass(Object clazz) {
        InvocationHandler handler = new DemoInvocationHandler(clazz);
        return Proxy.newProxyInstance(Demo.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }
}
