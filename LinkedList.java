package assignment2;

public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {
	

	private Node first;
	private Node last;
	private Node current;
	private int numberOfElements;

	private class Node {
		E data;
		Node prior, next;

		public Node(E data) {
			this(data, null, null);
		}

		public Node(E data, Node prior, Node next) {
			this.data = data == null ? null : data;
			this.prior = prior;
			this.next = next;
		}
	}

	public boolean isEmpty() {
		return numberOfElements == 0;
	}

	public LinkedList() {
		current = null;
		first = null;
		last = null;
		numberOfElements = 0;
	}

	public ListInterface<E> init() {
		first = null;
		last = null;
		current = null;
		numberOfElements = 0;
		return this;
	}

	public int size() {
		return numberOfElements;
	}

	public ListInterface<E> insert(E d) {
		Node x = new Node(d);
		if (numberOfElements == 0) {
			last = first = current = x;
			current.next = null;
			current.prior = null;
		} else if (x.data.compareTo(first.data) <= 0) {
			x.next = first;
			first.prior = x;
			first = x;
			current = x;
		} else if (x.data.compareTo(last.data) > 0) {
			last.next = x;
			x.prior = last;
			last = x;
			x.next = null;
			current = x;
		} else {
			Node k = first;
			while (x.data.compareTo(k.data) < 0) {
				k = k.next;
			}
			x.next = k.next;
			x.prior = k;
			k.next.prior = x;
			k.next = x;
			current = x;
		}
		numberOfElements = numberOfElements + 1;
		return this;
	}

	public E retrieve() {
		return current.data;
	}

	public ListInterface<E> remove() {

		if (current == null) {
			System.out.println("Element is not in the set");
			return this;
		}
		else if (numberOfElements == 1) {
			current = null;
			first = null;
			last = null;
			numberOfElements = numberOfElements - 1;
		} else if (numberOfElements != 0 && current == last) {
			current = current.prior;
			current.next = null;
			last = current;
			numberOfElements = numberOfElements - 1;
		} else if (numberOfElements != 0 && current == first) {
			current.next.prior = null;
			first = current.next;
			numberOfElements = numberOfElements - 1;
		} else if (numberOfElements == 0) {
			int x;
		} else {
			current.next.prior = current.prior;
			current = current.prior.next = current.next;
			numberOfElements = numberOfElements - 1;
		}
		return this;
	}

	public boolean find(E d) {
		current = first;
		while (current != null) {
			if (current.data.compareTo(d) == 0) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	public boolean goToFirst() {
		if (isEmpty() == true) {
			return false;
		} else {
			current = first;
			return true;
		}
	}

	public boolean goToLast() {
		if (isEmpty()) {
			return false;
		} else {
			current = last;
			return true;
		}
	}

	public boolean goToNext() {
		if (current == last || isEmpty()) {
			return false;
		} else {
			current = current.next;
			return true;
		}
	}

	public boolean goToPrevious() {
		if (current == first || isEmpty()) {
			return false;
		} else {
			current = current.prior;
			return true;
		}
	}

	public ListInterface<E> copy() {
		return this;
	}
}