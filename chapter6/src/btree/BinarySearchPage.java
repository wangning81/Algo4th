package btree;

import elementaryST.BinarySearchST;

public class BinarySearchPage<TKey extends Comparable<TKey>> implements Page<TKey> {
	private final int ORDER = 6;
	private BinarySearchST<TKey, BinarySearchPage<TKey>> st = new BinarySearchST<TKey, BinarySearchPage<TKey>>();
	private boolean isLeaf;	

	public BinarySearchPage(boolean isLeaf)
	{
		this.isLeaf = isLeaf;
	}
	
	public TKey getMinKey()
	{
		if(st.size() == 0) return null;
		return st.min();
	}
	
	public void add(TKey key) {
		if(!st.contains(key))
			st.put(key, null);
	}

	public void add(Page<TKey> p) {
		BinarySearchPage<TKey> bsp = (BinarySearchPage<TKey>)p;
		st.put(bsp.getMinKey(), bsp);
	}

	public boolean isExternal() {
		return isLeaf;
	}

	public boolean isFull() {
		return st.size() == ORDER;
	}

	public int getOrder() {
		return ORDER;
	}

	public Page<TKey> split() {
		int size = st.size();
		BinarySearchPage<TKey> right = new BinarySearchPage<TKey>(true);
		for(int i = size / 2 ; i < size ; i++)
			right.add(st.select(i));
		for(int i = 0 ; i < size / 2; i++)
			st.deleteMax();
		return right;
	}

	public Page<TKey> next(TKey key) {
		return st.get(st.floor(key));
	}

	public boolean contains(TKey key) {
		return st.contains(key);
	}

	public void setExternal(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

}
