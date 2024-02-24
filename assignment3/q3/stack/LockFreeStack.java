package stack;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack implements MyStack {
// you are free to add members
    AtomicReference<Node> top;
  public LockFreeStack() {
	  // implement your constructor here
      top = new AtomicReference<Node>(null);
  }
	
  public boolean push(Integer value) {
	  // implement your push method here
      Node node = new Node(value);
      while (true) {
          Node oldTop = top.get();
          node.next = oldTop;
          if (top.compareAndSet(oldTop, node)) {
              return true;
          }
          else {
              Thread.yield();
          }
      }
      // return false;
  }
  
  public Integer pop() throws EmptyStack {
	  // implement your pop method here
      while (true) {
          Node oldTop = top.get();
          if (oldTop == null) {
              throw new EmptyStack();
          }
          Node newTop = oldTop.next;
          if (top.compareAndSet(oldTop, newTop)) {
              return oldTop.value;
          }
          else {
              Thread.yield();
          }
      }
	  // return null;
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
