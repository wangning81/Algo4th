package binraySearchTree;

public class KeyValuePair<TKey extends Comparable<TKey>, TValue> 
					implements Comparable<KeyValuePair<TKey, TValue>>{
	
	public TKey Key;
	public TValue Value;
	
	public KeyValuePair(TKey key, TValue value)
	{
		this.Key = key;
		this.Value = value;
	}
	
	public int compareTo(KeyValuePair<TKey, TValue> rhs) {
		return this.Key.compareTo(rhs.Key);		
	}

}
