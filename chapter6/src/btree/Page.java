package btree;

public interface Page<TKey extends Comparable<TKey>> {
	void add(TKey key);
	void add(Page<TKey> p);
	boolean isExternal();
	void setExternal(boolean isLeaf);
	boolean isFull();
	int getOrder();
	Page<TKey> split();
	Page<TKey> next(TKey key);
	boolean contains(TKey key);
}