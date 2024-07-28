import annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Stream;

public class TestRunner {
    public static void main(String... args) {
        runTests(ClassForTest.class);
    }

    private static void runTests(Class<?> clazz) {
        final List<Method> beforeMethods = getAnnotatedMethods(clazz, Before.class);
        final List<Method> afterMethods = getAnnotatedMethods(clazz, After.class);
        final List<Method> testMethods = getAnnotatedMethods(clazz, Test.class);
        final List<Method> beforeAllMethods = getStaticAnnotatedMethods(clazz, BeforeAll.class);
        final List<Method> afterAllMethods = getStaticAnnotatedMethods(clazz, AfterAll.class);

        if (beforeAllMethods.size() > 1) {
            throw new IllegalArgumentException("More than one @BeforeAll method found");
        } else if (afterAllMethods.size() > 1) {
            throw new IllegalArgumentException("More than one @AfterAll method found");
        } else if (beforeMethods.size() > 1) {
            throw new IllegalArgumentException("More than one @Before method found");
        } else if (afterMethods.size() > 1) {
            throw new IllegalArgumentException("More than one @After method found");
        } else if (testMethods.isEmpty()) {
            throw new IllegalArgumentException("No tests found");
        }

        final TestStats stats = new TestStats(testMethods.size());
        boolean beforeAllFailed = false;

        try {
            if (!beforeAllMethods.isEmpty()) {
                final Method beforeAllMethod = beforeAllMethods.get(0);
                try {
                    invokeMethod(beforeAllMethod, null);
                } catch (Exception e) {
                    beforeAllFailed = true;
                    e.printStackTrace();

                    for (int i = 0; i < stats.getTotal(); i++) {
                        stats.incrementFailed();
                    }
                }
            }

            boolean beforeFailed = false;
            if (!beforeAllFailed) {
                for (Method testMethod : testMethods) {
                    final Object instance = clazz.getDeclaredConstructor().newInstance();

                    if (!beforeMethods.isEmpty()) {
                        final Method beforeMethod = beforeMethods.get(0);
                        try {
                            invokeMethod(beforeMethod, instance);
                        } catch (Exception e) {
                            beforeFailed = true;
                            e.printStackTrace();
                            stats.incrementFailed();
                        }
                    }

                    if (!beforeFailed) {
                        try {
                            invokeMethod(testMethod, instance);
                            stats.incrementPassed();
                        } catch (Exception e) {
                            e.printStackTrace();
                            stats.incrementFailed();
                        }
                    }

                    if (!afterMethods.isEmpty()) {
                        final Method afterMethod = afterMethods.get(0);
                        try {
                            invokeMethod(afterMethod, instance);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!afterAllMethods.isEmpty()) {
                final Method afterAllMethod = afterAllMethods.get(0);
                try {
                    invokeMethod(afterAllMethod, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        printStats(stats);
    }

    private static <T extends Annotation> List<Method> getAnnotatedMethods(Class<?> clazz, Class<T> annotation) {
        return Stream.of(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .toList();
    }

    private static <T extends Annotation> List<Method> getStaticAnnotatedMethods(Class<?> clazz, Class<T> annotation) {
        return Stream.of(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation) && Modifier.isStatic(method.getModifiers()))
                .toList();
    }

    private static void invokeMethod(Method method, Object instance) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(instance);
    }

    private static void printStats(TestStats stats) {
        System.out.println("Test Execution Summary:");
        System.out.println("Total tests: " + stats.getTotal());
        System.out.println("Passed tests: " + stats.getPassed());
        System.out.println("Failed tests: " + stats.getFailed());
    }
}