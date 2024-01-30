package question5;

import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleTest {
                    // 8



    
    @Test
    public void TestTournament() {
        System.out.println("Bakery:\n");
        int res = question5.Bakery.PIncrement.parallelIncrement(0, 8);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.Bakery.PIncrement.parallelIncrement(0, 4);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.Bakery.PIncrement.parallelIncrement(0, 2);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.Bakery.PIncrement.parallelIncrement(0, 1);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        System.out.println("\n");
    }

    @Test
    public void TestAtomicInteger() {
        System.out.println("Atomic:");
        int res = question5.AtomicInteger.PIncrement.parallelIncrement(0, 8);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.AtomicInteger.PIncrement.parallelIncrement(0, 4);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.AtomicInteger.PIncrement.parallelIncrement(0, 2);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.AtomicInteger.PIncrement.parallelIncrement(0, 1);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        System.out.println("\n");
    }

    @Test
    public void TestSynchronized() {
        System.out.println("Synch:");
        int res = question5.Synchronized.PIncrement.parallelIncrement(0, 8);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.Synchronized.PIncrement.parallelIncrement(0, 4);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.Synchronized.PIncrement.parallelIncrement(0, 2);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.Synchronized.PIncrement.parallelIncrement(0, 1);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        System.out.println("\n");
    }

    @Test
    public void TestReentrantLock() {
        System.out.println("Reent:");
        int res = question5.ReentrantLock.PIncrement.parallelIncrement(0, 8);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.ReentrantLock.PIncrement.parallelIncrement(0, 4);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.ReentrantLock.PIncrement.parallelIncrement(0, 2);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        res = question5.ReentrantLock.PIncrement.parallelIncrement(0, 1);
        assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
        System.out.println("\n");
    }



    // @Test
    // public void TestTournament8() {
    //     int res = question5.Bakery.PIncrement.parallelIncrement(0, 8);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestAtomicInteger8() {
    //     int res = question5.AtomicInteger.PIncrement.parallelIncrement(0, 8);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestSynchronized8() {
    //     int res = question5.Synchronized.PIncrement.parallelIncrement(0, 8);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestReentrantLock8() {
    //     int res = question5.ReentrantLock.PIncrement.parallelIncrement(0, 8);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }
    //                     // 4
    // @Test
    // public void TestTournament4() {
    //     int res = question5.Bakery.PIncrement.parallelIncrement(0, 4);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestAtomicInteger4() {
    //     int res = question5.AtomicInteger.PIncrement.parallelIncrement(0, 4);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestSynchronized4() {
    //     int res = question5.Synchronized.PIncrement.parallelIncrement(0, 4);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestReentrantLock4() {
    //     int res = question5.ReentrantLock.PIncrement.parallelIncrement(0, 4);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }
    //                         // 2
    // @Test
    // public void TestTournament2() {
    //     int res = question5.Bakery.PIncrement.parallelIncrement(0, 2);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestAtomicInteger2() {
    //     int res = question5.AtomicInteger.PIncrement.parallelIncrement(0, 2);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestSynchronized2() {
    //     int res = question5.Synchronized.PIncrement.parallelIncrement(0, 2);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestReentrantLock2() {
    //     int res = question5.ReentrantLock.PIncrement.parallelIncrement(0, 2);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }
    //                         // 1
    // @Test
    // public void TestTournament1() {
    //     int res = question5.Bakery.PIncrement.parallelIncrement(0, 1);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestAtomicInteger1() {
    //     int res = question5.AtomicInteger.PIncrement.parallelIncrement(0, 1);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestSynchronized1() {
    //     int res = question5.Synchronized.PIncrement.parallelIncrement(0, 1);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }

    // @Test
    // public void TestReentrantLock1() {
    //     int res = question5.ReentrantLock.PIncrement.parallelIncrement(0, 1);
    //     assertTrue("Result is " + res + ", expected result is 1200000.", res == 1200000);
    // }
}
