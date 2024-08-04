import annotation.*;

import static org.assertj.core.api.Assertions.*;

public class ClassForTest {
    @Before
    public void init() {
        System.out.println("Before each test");
        //throw new RuntimeException("Oooppss in init (@Before)");
    }

    @After
    public void tearDown() {
        System.out.println("After each test");
    }

    @Test
    public void firstTest() {
        System.out.println("Running test 1");
        //throw new RuntimeException("Oooppss in firstTest");
        assertThat(42).isEqualTo(42);
    }

    @Test
    public void secondTest() {
        System.out.println("Running test 2");
        //throw new RuntimeException("Oooppss in firstTest");
        assertThat(1 + 1).isEqualTo(2);
    }

    @Test
    public void thirdTest() {
        System.out.println("Running test 3");
        assertThat("hello").isEqualTo("hello");
    }
}