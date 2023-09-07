package assignment2;

import java.math.BigInteger;

public class Set<E extends Comparable<E>> implements SetInterface<E> {
	
	public ListInterface<E> list = new LinkedList<E>();

	public Set() {
		list = new LinkedList<E>();
	}

	public SetInterface<E> copy() {
		Set<E> newSet = new Set<>();
		if (list.size() == 0)
			return newSet;
		list.goToFirst();
		do {
			newSet.add(list.retrieve());
		} while (list.goToNext());
		return newSet;
	}

	public void init() {
		list = list.init();
	}

	public void add(E data) {
		if (!list.find(data)) {
			list.insert(data);
		}
	}

	public SetInterface<E> calDifference(SetInterface<E> s) {
		Set<E> set = (Set<E>) s;
		Set<E> results = new Set<>();
		if (list.goToFirst()) {
			do {
				if (!set.list.find(list.retrieve())) {
					results.add(list.retrieve());
				}
			} while (list.goToNext());
		}
		return results;
	}

	public SetInterface<E> calIntersection(SetInterface s) {
		Set<E> set = (Set<E>) s;
		Set<E> results = new Set<>();
		if (list.goToFirst()) {
			do {
				if (set.list.find(list.retrieve())) {
					results.add(list.retrieve());
				}
			} while (list.goToNext());
		}
		return results;
	}

	public SetInterface<E> calUnion(SetInterface s) {
		Set<E> set = (Set<E>) s;
		Set<E> results = new Set<>();
		if (list.goToFirst()) {
			do {
				results.add(list.retrieve());
			} while (list.goToNext());
		}
		if (set.list.goToFirst()) {
			do {
				results.add(set.list.retrieve());
			} while (set.list.goToNext());
		}
		return results;
	}

	public SetInterface<E> calSymmetricDifference(SetInterface s) {
		Set<E> set = (Set<E>) s;
		Set<E> results = new Set<>();
		if (set.list.goToFirst()) {
			do {
				if (!list.find(set.list.retrieve())) {
					results.add(set.list.retrieve());
				}
			} while (set.list.goToNext());
		}
		if (list.goToFirst()) {
			do {
				if (!set.list.find(list.retrieve())) {
					results.add(list.retrieve());
				}
			} while (list.goToNext());
		}
		return results;
	}

	public int size() {
		return list.size();
	}

	public E get() {
		list.goToFirst();
		return list.retrieve();
	}

	public void remove(E e) {
		if (list.find(e))
			list.remove();
	}
}