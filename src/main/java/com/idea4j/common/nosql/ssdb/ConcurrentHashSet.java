package com.idea4j.common.nosql.ssdb;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copy from jbosscache2.0.0GA.
 * A simple Set implementation backed by a
 * {@link ConcurrentHashMap} to deal with the fact that the
 * JDK does not have a proper concurrent Set implementation that uses efficient
 * lock striping. <p/> Note that values are stored as keys in the underlying
 * Map, with a static dummy object as value.
 * 
 * @author <a href="mailto:manik@jboss.org">Manik Surtani</a>
 * @since 2.0.0
 * 
 * @param <E>
 *            element type
 * @date 2023-09-28 14:29
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> {
	/**
	 * back map.
	 */
	private ConcurrentHashMap<E, Object> map;

	private static final Object DUMMY = new Object();

	/**
	 * ����.
	 */
	public ConcurrentHashSet() {
		map = new ConcurrentHashMap<E, Object>();
	}

	/**
	 * size of this set.
	 * 
	 * @return size
	 */
	@Override
	public int size() {
		return map.size();
	}

	/**
	 * is empty.
	 * 
	 * @return map.isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * ��.
	 * 
	 * @param o
	 *            object
	 * @return map.containsKey();
	 */
	@Override
	public boolean contains(final Object o) {
		return map.containsKey(o);
	}

	/**
	 * iterator().
	 * 
	 * @return map.iterator();
	 */
	@Override
	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}

	/**
	 * toArray.
	 * 
	 * @return map.keySet().toArray();
	 */
	@Override
	public Object[] toArray() {
		return map.keySet().toArray();
	}

	/**
	 * map.keySet().toArray().
	 * 
	 * 
	 * @param <T>
	 *            �������
	 * @param a
	 *            ������͵�����
	 * @return map.keySet().toArray(a);
	 */
	@Override
	public <T> T[] toArray(final T[] a) {
		return map.keySet().toArray(a);
	}

	/**
	 * ����.
	 * 
	 * @param o
	 *            object
	 * 
	 * @return true ������Ŀ, false �滻
	 */
	@Override
	public boolean add(final E o) {
		Object v = map.put(o, DUMMY);
		return v == null;
	}

	/**
	 * remove().
	 * 
	 * @param o
	 *            element to remove
	 * @return true �ҵ���ɾ��; ����false
	 */
	@Override
	public boolean remove(final Object o) {
		Object v = map.remove(o);
		return v != null;
	}

	/**
	 * containsAll.
	 * 
	 * @param c
	 *            collections
	 * @return true : ����; false: ������
	 */
	@Override
	public boolean containsAll(final Collection<?> c) {
		return map.keySet().containsAll(c);
	}

	/**
	 * ��ԭ�Ӳ���.
	 * 
	 * @param c
	 *            ����
	 * @return always true
	 */
	@Override
	public boolean addAll(final Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return true;
	}

	/**
	 * retainAll.
	 * 
	 * @param c
	 *            collections
	 * @return flag
	 */
	@Override
	public boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException("Not supported in this implementation.");
	}

	/**
	 * ��ԭ�Ӳ���.
	 * 
	 * @param c
	 *            ����
	 * 
	 * @return always true
	 */
	@Override
	public boolean removeAll(final Collection<?> c) {
		for (Object e : c) {
			remove(e);
		}
		return true;
	}

	/**
	 * clear().
	 */
	@Override
	public void clear() {
		map.clear();
	}

}
