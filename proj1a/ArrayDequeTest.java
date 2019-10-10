public class ArrayDequeTest {

    public static void testList() {
        ArrayDeque<String> act = new ArrayDeque<>();
        for (int i = 3; i > -2; i--) {
            act.addFirst(Integer.toString(i));
        }
        for (int i = 1; i < 6; i++) {
            System.out.printf("%s", act.get(i));
        }
    }

    public static void main(String[] args) {
        testList();
    }

}