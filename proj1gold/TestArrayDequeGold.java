import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void Test() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();

        String message="";

        for (int i = 0; i < 10; i++) {
            int number = StdRandom.uniform(20);
            message = message + "addLast("+number+")\n";
            sad1.addLast(number);
            ads1.addLast(number);
        }
        for (int i = 0; i < 10; i++) {
            Integer expected = ads1.removeLast();
            Integer actual = sad1.removeLast();
            message = message+"removeLast()\n";
            assertEquals(message,expected, actual);
        }
        message = "";
        for (int i = 0; i < 10; i++) {
            int number = StdRandom.uniform(20);
            message = message + "addFirst("+number+")\n";
            sad1.addFirst(number);
            ads1.addFirst(number);
        }
        for (int i = 0; i < 10; i++) {
            Integer expected = ads1.removeFirst();
            Integer actual = sad1.removeFirst();
            message=message+"removeFirst()\n";
            assertEquals(message,expected, actual);
        }
    }
}
