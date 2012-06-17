package symbolTables;

public interface SymbolTable <TKey, TValue> {
	void put(TKey key, TValue value);
	TValue get(TKey key);
	void delete(TKey key);
	boolean contains(TKey key);
	int size();
	Iterable<TKey> keys();
	void clear();
}
