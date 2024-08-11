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
    private final ArrayList<Method> annotatedMethods = new ArrayList<>();

    DemoInvocationHandler(Object myClass) {
        this.myClass = myClass;

        for (Method method : myClass.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                annotatedMethods.add(method);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        for (Method annotatedMethod : annotatedMethods) {
            if (annotatedMethod.getName().equals(method.getName()) &&
                    annotatedMethod.getReturnType() == method.getReturnType() &&
                    Arrays.equals(annotatedMethod.getParameterTypes(), method.getParameterTypes())
            ) {
                StringBuilder stringArgs = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    stringArgs.append(args[i]);
                    if (i < args.length - 1) {
                        stringArgs.append(", ");
                    }
                }
                logger.info("executed method: {}, param: {}", method.getName(), stringArgs);
            }
        }

        return method.invoke(myClass, args);
    }
}
