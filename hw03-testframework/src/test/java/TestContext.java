import annotation.After;
import annotation.Before;
import annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

public final class TestContext {
    private final Class<?> clazz;
    final Object instance;
    final TestStats testStats;

    public TestContext(Class<?> clazz) {
        this.clazz = clazz;

        try{
            instance = clazz.getDeclaredConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        testStats = new TestStats(this.getTestMethods().size());
    }

    public Method getBeforeMethod() {
        return getAnnotatedMethods(this.clazz, Before.class).stream().findFirst().orElse(null);
    }

    public Method getAfterMethod() {
        return getAnnotatedMethods(clazz, After.class).stream().findFirst().orElse(null);
    }

    public List<Method> getTestMethods() {
        return getAnnotatedMethods(clazz, Test.class);
    }

    public void invokeMethod(Method method) {
        method.setAccessible(true);
        try {
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void invokeTestMethod(Method method) {
        method.setAccessible(true);
        try {
            method.invoke(instance);
            testStats.incrementPassed();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            testStats.incrementFailed();
        }
    }

    public void printStats() {
        System.out.println("Test Execution Summary:");
        System.out.println("Total tests: " + testStats.getTotal());
        System.out.println("Passed tests: " + testStats.getPassed());
        System.out.println("Failed tests: " + testStats.getFailed());
    }

    private static <T extends Annotation> List<Method> getAnnotatedMethods(Class<?> clazz, Class<T> annotation) {
        return Stream.of(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .toList();
    }
}
