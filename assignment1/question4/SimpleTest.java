package question4;

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleTest {

    @Test
    public void TestBasic() {
        int[] A = { 1 };
        int frequency = Frequency.parallelFreq(1, A, 8);
        assertTrue("Result is " + frequency + ", expected frequency of 1 is 1.", frequency == 1);
    }

    @Test
    public void TestArray() {
        int[] A = { 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 33, 3, 3, 3, 3, 3, 3, 4, 5, 423, 2342, 423, 4, 23, 23, 423,
                7, 23, 3, 23, 2, 5, 11, 232, 32, 32, 32, 65 };
        int frequency = Frequency.parallelFreq(3, A, 8);
        assertTrue("Result is " + frequency + ", expected frequency of 3 is 19.", frequency == 19);
        frequency = Frequency.parallelFreq(23, A, 8);
        assertTrue("Result is " + frequency + ", expected frequency of 23 is 4.", frequency == 4);
        frequency = Frequency.parallelFreq(32, A, 8);
        assertTrue("Result is " + frequency + ", expected frequency of 32 is 3.", frequency == 3);
    }

    @Test
    public void testNumberNotPresent() {
        int[] array = {1, 2, 3, 4, 5};
        int frequency = Frequency.parallelFreq(6, array, 4);
        assertEquals("Number not present should return frequency 0", 0, frequency);
    }

    @Test
    public void testEmptyArray() {
        int[] array = {};
        int frequency = Frequency.parallelFreq(3, array, 4);
        assertEquals("Empty array should return frequency -1 for bad input", -1, frequency);
    }

    @Test
    public void testSingleElementArray() {
        int[] array = {3};
        int frequency = Frequency.parallelFreq(3, array, 4);
        assertEquals("Single element array should return correct frequency", 1, frequency);
    }

    @Test
    public void testAllIdenticalElements() {
        int[] array = {5, 5, 5, 5, 5, 5};
        int frequency = Frequency.parallelFreq(5, array, 4);
        assertEquals("Array with all identical elements should return correct frequency", 6, frequency);
    }
}
