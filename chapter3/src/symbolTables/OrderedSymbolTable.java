package symbolTables;



public interface OrderedSymbolTable <TKey extends Comparable<TKey>, TValue> 
	extends SymbolTable<TKey, TValue>{
	
	TKey min();
	TKey max();
	TKey floor(TKey key);
	TKey ceiling(TKey key);
	TKey select(int i);
	int rank(TKey key);
	void deleteMin();
	void deleteMax();

}
