package stack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class LockStack implements MyStack {

	ReentrantLock lock;
	int _size;
	Node sentinel;
	Node top;

	public int size() {return _size;}
	public boolean empty() {return top==sentinel;}

	public LockStack() {
		lock = new ReentrantLock();
		_size = 0;
		sentinel = new Node(-1);
		top = sentinel;
	}

	public boolean push(Integer value) {
		lock.lock();
		Node newnode = new Node(value);
		newnode.next = top;
		top = newnode;
		_size++;
		lock.unlock();
		return true;
	}

	public Integer pop() throws EmptyStack {
		Integer result = -1;
		try {
			lock.lock();
			if (this.empty()) throw new EmptyStack();
			result = top.value;
			top = top.next;
		} finally {
			lock.unlock();
			return result;
		}
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
