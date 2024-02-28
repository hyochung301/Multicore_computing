package queue;

import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class LockQueue implements MyQueue {
// you are free to add members
  ReentrantLock enqLock, deqLock;
  Condition notEmpty;
  Node head;
  Node tail;
  Node sentinel;
  AtomicInteger count;

  public int size() {return count.get();} 
  public boolean empty() {return this.size()==0;} // for testing

  public LockQueue() {
	// implement your constructor here
    sentinel = new Node(null);
    tail = head = sentinel;  
    enqLock = new ReentrantLock();
    deqLock = new ReentrantLock();
    notEmpty = deqLock.newCondition();
    count = new AtomicInteger(0);
  }
  
  public boolean enq(Integer value) {
	// implement your enq method here
    if (value == null) {
      throw new NullPointerException();
    }
    enqLock.lock();
    try {
      Node insertNode = new Node(value);
      boolean wasEmpty = (head == tail); //if they are the same, the queue is empty
      tail.next = insertNode;
      tail = tail.next;
      count.incrementAndGet();
      if (wasEmpty) {
        // if the queue was empty, signal notEmpty
        deqLock.lock();
        try {
          notEmpty.signal();
        } finally {
          deqLock.unlock();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      enqLock.unlock();
    }
    return true;
  }
  
  public Integer deq() {
	// implement your deq method here
    int result = -1;
    deqLock.lock();
    try {
      while (count.get() == 0) {
        notEmpty.await();
      }
      result = head.next.value;
      head = head.next;
      count.decrementAndGet();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      deqLock.unlock();
    }
    return result;
  }
  
  protected class Node {
	  public Integer value;
	  public Node next;
		    
	  public Node(Integer x) {
		  value = x;
		  next = null;
	  }
  }
}
