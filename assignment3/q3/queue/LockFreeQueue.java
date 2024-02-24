package queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue implements MyQueue {
  private AtomicReference<PointerCount> head, tail;

  public LockFreeQueue() {
    Node dummy = new Node(null);
    PointerCount sentinel = new PointerCount(dummy, 0);
    head = new AtomicReference<>(sentinel);
    tail = new AtomicReference<>(sentinel);
  }

  public boolean enq(Integer value) {
    Node node = new Node(value);
    while (true) {
      PointerCount last = tail.get();
      Node next = last.ptr.get().next.get();
      if (last == tail.get()) {
        if (next == null) {
          if (last.ptr.get().next.compareAndSet(next, node)) {
            tail.compareAndSet(last, new PointerCount(node, last.count.get() + 1));
            return true;
          }
        } else {
          tail.compareAndSet(last, new PointerCount(next, last.count.get() + 1));
        }
      }
    }
  }

  public Integer deq() {
    while (true) {
      PointerCount first = head.get();
      PointerCount last = tail.get();
      Node next = first.ptr.get().next.get();
      if (first == head.get()) {
        if (first.ptr.get() == last.ptr.get()) {
          if (next == null) {
            return null;
          }
          tail.compareAndSet(last, new PointerCount(next, last.count.get() + 1));
        } else {
          Integer value = next.value;
          if (head.compareAndSet(first, new PointerCount(next, first.count.get() + 1))) {
            return value;
          }
        }
      }
    }
  }

  protected class Node {
    public Integer value;
    public AtomicReference<Node> next;

    public Node(Integer x) {
      value = x;
      next = new AtomicReference<>(null);
    }
  }

  protected class PointerCount {
    public AtomicReference<Node> ptr;
    public AtomicInteger count;

    public PointerCount(Node node, int count) {
      this.ptr = new AtomicReference<>(node);
      this.count = new AtomicInteger(count);
    }
  }
}
