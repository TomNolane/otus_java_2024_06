package tomnolane.otus;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String... args) {
        System.out.println("Hello Otus.ru!");

        String[] array = new String[] { "Meaning", "of", "life", "is", "the", "number:"};
        String joined = String.join(" ", array);

        System.out.println(joined + " 42");
    }
}
