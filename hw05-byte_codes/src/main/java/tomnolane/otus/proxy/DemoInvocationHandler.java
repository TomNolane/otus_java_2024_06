package tomnolane.otus.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tomnolane.otus.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class DemoInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DemoInvocationHandler.class);
    private final Object myClass;
    private final Set<String> annotatedMethods = new HashSet<>();

    DemoInvocationHandler(Object myClass) {
        this.myClass = myClass;

        for (Method method : myClass.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                annotatedMethods.add(method.getName() + Arrays.toString(method.getParameterTypes()));
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String key = method.getName() + Arrays.toString(method.getParameterTypes());
        final StringBuilder stringArgs = new StringBuilder();
        if(annotatedMethods.contains(key)) {
            for (int i = 0; i < args.length; i++) {
                stringArgs.append(args[i]);
                if (i < args.length - 1) {
                    stringArgs.append(", ");
                }
            }
        }
        logger.info("executed method: {}, param: {}", method.getName(), stringArgs);

        return method.invoke(myClass, args);
    }
}
