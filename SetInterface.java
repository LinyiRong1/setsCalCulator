package assignment2;

import java.math.BigInteger;

public interface SetInterface<E extends Comparable<E>> {
	
	public SetInterface<E> copy();
	/*PRE-
	 * POST- A new setInterface that is a copy of this is returned.
	 */

	public void init();
	/* PRE-
	 * POST - The set is empty.
	 */
	
	public void add(E s);
	/* PRE -
	 * POST - s is now in the set.
	 */
	
	public SetInterface<E> calDifference(SetInterface<E> s);
	/* PRE - 
	 * POST - A new set that contains the difference between this setInterface and setInterface s is returned.
	 */
	
	public SetInterface<E> calIntersection(SetInterface<E> s);
	/* PRE - 
	 * POST -A new set that contains the intersect between this setInterface and setInterface s is returned.
	 */
	
	public SetInterface<E> calUnion(SetInterface<E> s);
	/* PRE - 
	 * POST - A new set that contains the union between this setInterface and setInterface s is returned..
	 */
	
	public SetInterface<E> calSymmetricDifference(SetInterface<E> s);
	/* PRE - 
	 * POST - A new set that contains the symmetric difference between this setInterface and setInterface s is returned.
	 */
	
	public int size();
	/* PRE -
	 * POST - The number of elements in the setInterface is returned.
	 */
	
	public E get();
	/* PRE-
	 * POST- An element is returned. Return null if the set is empty.
	 */

	public void remove(E e);
	/* PRE-
	 * POST- e is not in the set.
	 */
}