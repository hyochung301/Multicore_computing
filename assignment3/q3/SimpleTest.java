
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.HashMap;

import queue.*;
import stack.*;

public class SimpleTest {

    @Test
    public void seq_test_lockq() {
        LockQueue q = new LockQueue();
        final int TSIZE = 100000;

        Assert.assertTrue(q.empty());
        for (int i = 0; i < TSIZE; i++) {
            Assert.assertEquals(i,q.size());
            Assert.assertTrue(q.enq(i));
        }
        Assert.assertTrue(!q.empty());
        Assert.assertEquals(TSIZE,q.size());

        for (int i = 0; i < TSIZE; i++) {
            Assert.assertEquals(TSIZE-i,q.size());
            Assert.assertTrue(q.deq()==i);
            if (i != TSIZE-1) Assert.assertTrue(!q.empty());
        }
        Assert.assertTrue(q.empty());
    }

    @Test
    public void seq_test_lockfreeq() {
        MyQueue q = new LockFreeQueue();
        final int TSIZE = 100000;

        Assert.assertTrue(q.deq()==null);
        for (int i = 0; i < TSIZE; i++) {
            Assert.assertTrue(q.enq(i));
        }

        for (int i = 0; i < TSIZE; i++) {
            Assert.assertTrue(q.deq()==i);
        }
        Assert.assertTrue(q.deq()==null);
    }

    @Test
    public void conc_test_lockq_enq() {

        LockQueue q = new LockQueue();
        final int TSIZE = 10000;
        final int NT = 32;

        Runnable enqer = new Runnable() {
            @Override
            public void run() {
                // System.out.println("runnable");
                for (int i = 0; i < TSIZE; i++) {
                    Assert.assertTrue(q.enq(i));
                }
            }
        };

        Thread[] threads = new Thread[NT];
        for (int i = 0; i < NT; i++) { threads[i] = new Thread(enqer); }
        for (Thread t : threads) { t.start(); }

        // System.out.println("joining");     
        try {for (Thread t : threads){t.join();}} catch (Exception e) {e.printStackTrace();}
        // System.out.println("joined");  

        HashMap<Integer, Integer> valCounter = new HashMap<>();
        for (int i = 0; i < TSIZE; i++) { valCounter.put(i,0); }
        // System.out.println("made map, deqin:");

        for (int i = 0; i < NT*TSIZE; i++) {
            Assert.assertFalse(q.empty());
            Integer res = q.deq();
            valCounter.put(res, valCounter.get(res)+1);
        }

        // System.out.println("final checks");
        Assert.assertTrue(q.empty());
        
        // System.out.println("map check");
        for (Integer key : valCounter.keySet()) {
            Assert.assertTrue(valCounter.get(key)==NT);
        }

    }

    @Test
    public void conc_test_lockq_deq() {

        LockQueue q = new LockQueue();
        final int TSIZE = 10000;
        final int NT = 32;

        Runnable deqer = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < TSIZE; i++) {
                    q.deq();
                }
            }
        };

        for (int i = 0; i < NT*TSIZE; i++) {
            q.enq(i);
            Assert.assertFalse(q.empty());
        }

        Thread[] threads = new Thread[NT];
        for (int i = 0; i < NT; i++) { threads[i] = new Thread(deqer); }
        for (Thread t : threads) { t.start(); }
        try {for (Thread t : threads){t.join();}} catch (Exception e) {e.printStackTrace();}
        
        Assert.assertTrue(q.empty());

    }

    @Test
    public void conc_test_lockfreeq_enq() {

        LockFreeQueue q = new LockFreeQueue();
        final int TSIZE = 10000;
        final int NT    = 32;

        Runnable enqer = new Runnable() {
            @Override
            public void run() {
                // System.out.println("runnable");
                for (int i = 0; i < TSIZE; i++) {
                    Assert.assertTrue(q.enq(i));
                }
            }
        };

        Thread[] threads = new Thread[NT];
        for (int i = 0; i < NT; i++) { threads[i] = new Thread(enqer); }
        for (Thread t : threads) { t.start(); }
        
        // System.out.println("joining");     
        try {for (Thread t : threads){t.join();}} catch (Exception e) {e.printStackTrace();}
        // System.out.println("joined");  

        HashMap<Integer, Integer> valCounter = new HashMap<>();
        for (int i = 0; i < TSIZE; i++) { valCounter.put(i,0); }
        // System.out.println("made map, deqin:");

        for (int i = 0; i < NT*TSIZE; i++) {
            Integer res = q.deq();
            Assert.assertNotEquals(res,null);
            valCounter.put(res, valCounter.get(res)+1);
        }

        // System.out.println("final checks");
        Assert.assertEquals(q.deq(), null);
        
        // System.out.println("map check");
        for (Integer key : valCounter.keySet()) {
            Assert.assertTrue(valCounter.get(key)==NT);
        }

    }

    @Test
    public void conc_test_lockfreeq_deq() {

        LockFreeQueue q = new LockFreeQueue();
        final int TSIZE = 10000;
        final int NT = 32;

        Runnable deqer = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < TSIZE; i++) {
                    q.deq();
                }
            }
        };

        for (int i = 0; i < NT*TSIZE; i++) {
            q.enq(i);
            Assert.assertFalse(q.deq()==null);
        }

        Thread[] threads = new Thread[NT];
        for (int i = 0; i < NT; i++) { threads[i] = new Thread(deqer); }
        for (Thread t : threads) { t.start(); }
        try {for (Thread t : threads){t.join();}} catch (Exception e) {e.printStackTrace();}
        
        Assert.assertTrue(q.deq()==null);

    }

    /*
        ================================STACKS================================
        ================================STACKS================================
        ================================STACKS================================
        ================================STACKS================================
        ================================STACKS================================
    */

    @Test
    public void seq_test_lockstk() {
        LockStack s = new LockStack();
        final int TSIZE = 100000;

        Assert.assertTrue(s.empty());
        for (int i = 0; i < TSIZE; i++) {
            Assert.assertEquals(i,s.size());
            Assert.assertTrue(s.push(i));
        }
        Assert.assertTrue(!s.empty());
        Assert.assertEquals(TSIZE,s.size());

        for (int i = TSIZE-1; i >= 0; i--) {
            try{Assert.assertTrue(s.pop()==i);}catch(Exception e){Assert.assertTrue(false);}
            Assert.assertEquals(TSIZE,s.size());
            if (i != 0) Assert.assertTrue(!s.empty());
        }
        Assert.assertTrue(s.empty());
    }

    // crude empty check that will pop
    public boolean lfse(MyStack s) {
        boolean res = false;
        try {s.pop();} catch (EmptyStack e) {res = true;}
        return res;
    }

    @Test
    public void seq_test_lockfreestk_push() {
        MyStack s = new LockFreeStack();
        final int TSIZE = 100000;

        Assert.assertTrue(lfse(s));
        for (int i = 0; i < TSIZE; i++) {
            Assert.assertTrue(s.push(i));
        }

        for (int i = TSIZE-1; i >= 0; i--) {
            try {Assert.assertTrue(s.pop()==i);} catch (EmptyStack e) {Assert.assertTrue(false);}
        }
        Assert.assertTrue(lfse(s));
    }

    @Test
    public void conc_test_lockstk_push() {

        LockStack s = new LockStack();
        final int TSIZE = 10000;
        final int NT = 32;

        Runnable enqer = new Runnable() {
            @Override
            public void run() {
                // System.out.println("runnable");
                for (int i = 0; i < TSIZE; i++) {
                    Assert.assertTrue(s.push(i));
                }
            }
        };

        Thread[] threads = new Thread[NT];
        for (int i = 0; i < NT; i++) { threads[i] = new Thread(enqer); }
        for (Thread t : threads) { t.start(); }

        // System.out.println("joining");     
        try {for (Thread t : threads){t.join();}} catch (Exception e) {e.printStackTrace();}
        // System.out.println("joined");  

        HashMap<Integer, Integer> valCounter = new HashMap<>();
        for (int i = 0; i < TSIZE; i++) { valCounter.put(i,0); }
        // System.out.println("made map, deqin:");

        for (int i = 0; i < NT*TSIZE; i++) {
            Assert.assertFalse(s.empty());
            Integer res = 0;
            try{res = s.pop();}catch(Exception e){Assert.assertTrue(false);}
            valCounter.put(res, valCounter.get(res)+1);
        }

        // System.out.println("final checks");
        Assert.assertTrue(s.empty());
        
        // System.out.println("map check");
        for (Integer key : valCounter.keySet()) {
            Assert.assertTrue(valCounter.get(key)==NT);
        }

    }

    @Test
    public void conc_test_lockstk_pop() {

        LockStack s = new LockStack();
        final int TSIZE = 10000;
        final int NT = 32;

        Runnable deqer = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < TSIZE; i++) {
                    try{s.pop();}catch(Exception e){Assert.assertTrue(false);}
                }
            }
        };

        for (int i = 0; i < NT*TSIZE; i++) {
            s.push(i);
            Assert.assertFalse(s.empty());
        }

        Thread[] threads = new Thread[NT];
        for (int i = 0; i < NT; i++) { threads[i] = new Thread(deqer); }
        for (Thread t : threads) { t.start(); }
        try {for (Thread t : threads){t.join();}} catch (Exception e) {e.printStackTrace();}

        Assert.assertTrue(s.empty());

    }

    @Test
    public void conc_test_lockfreestk_push() {

        LockFreeStack s = new LockFreeStack();
        final int TSIZE = 10000;
        final int NT    = 32;

        Runnable enqer = new Runnable() {
            @Override
            public void run() {
                // System.out.println("runnable");
                for (int i = 0; i < TSIZE; i++) {
                    Assert.assertTrue(s.push(i));
                }
            }
        };

        Thread[] threads = new Thread[NT];
        for (int i = 0; i < NT; i++) { threads[i] = new Thread(enqer); }
        for (Thread t : threads) { t.start(); }
        
        // System.out.println("joining");     
        try {for (Thread t : threads){t.join();}} catch (Exception e) {e.printStackTrace();}
        // System.out.println("joined");  

        HashMap<Integer, Integer> valCounter = new HashMap<>();
        for (int i = 0; i < TSIZE; i++) { valCounter.put(i,0); }
        // System.out.println("made map, deqin:");

        for (int i = 0; i < NT*TSIZE; i++) {
            Integer res = null;
            try {res = s.pop();} catch (EmptyStack e) {Assert.assertTrue(false);}
            Assert.assertNotEquals(res,null);
            valCounter.put(res, valCounter.get(res)+1);
        }

        // System.out.println("final checks");
        Assert.assertTrue(lfse(s));
        
        // System.out.println("map check");
        for (Integer key : valCounter.keySet()) {
            Assert.assertTrue(valCounter.get(key)==NT);
        }

    }

    private class stk_deqer implements Runnable {
            boolean good = true;
            LockFreeStack s;
            int ts;
            public stk_deqer(LockFreeStack st, int sz){s=st;ts=sz;}
            @Override
            public void run() {
                for (int i = 0; i < ts; i++) {
                    try{ s.pop(); } catch(Exception e){
                        e.printStackTrace();
                        System.out.println((String.format("\n\nfail on i=%d",i)));
                        Assert.fail();
                    }
                }
            }
            public boolean gd() {return good;}
    }

    @Test
    public void conc_test_lockfreestk_pop() {

        LockFreeStack s = new LockFreeStack();
        final int TSIZE = 10000;
        final int NT = 32;

        boolean good = true;

        stk_deqer deqer = new stk_deqer(s,TSIZE);

        for (int i = 0; i < (NT*TSIZE); i++) {
            s.push(i);
        }

        Thread[] threads = new Thread[NT];
        for (int i = 0; i < NT; i++) { threads[i] = new Thread(deqer); }
        for (int i = 0; i < NT; i++) { threads[i].start(); }
        try {for (int i = 0; i < NT; i++){threads[i].join();}} catch (Exception e) {e.printStackTrace();}

        Assert.assertTrue(lfse(s));
        Assert.assertTrue(deqer.gd());
    }

    @Test 
    public void simple_lockfreestk_pop() {
        LockFreeStack s = new LockFreeStack();
        s.push(1);
        try {s.pop();} catch (Exception e) {Assert.fail();}
    }

}
