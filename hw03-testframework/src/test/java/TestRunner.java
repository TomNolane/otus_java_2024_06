import java.lang.reflect.Method;
import java.util.List;

public class TestRunner {
    public static void main(String... args) {
        runTests(ClassForTest.class);
    }

    private static void runTests(Class<?> clazz) {
        final TestContext context = new TestContext(clazz);
        final Method beforeMethod = context.getBeforeMethod();
        final Method afterMethod = context.getAfterMethod();
        final List<Method> testMethods = context.getTestMethods();

        if (testMethods.isEmpty()) {
            System.out.println("No tests found");
            return;
        }

        for (Method testMethod : testMethods) {
            if(beforeMethod != null) {
                context.invokeMethod(beforeMethod);
            }

            context.invokeTestMethod(testMethod);

            if(afterMethod != null) {
                context.invokeMethod(afterMethod);
            }
        }

        context.printStats();
    }
}