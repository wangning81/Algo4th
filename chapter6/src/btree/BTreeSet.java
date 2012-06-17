package btree;

public class BTreeSet<TKey extends Comparable<TKey>> {
	
	private Page<TKey> root;
	private PageCreator<TKey, Page<TKey>> creator;
	
	public BTreeSet(PageCreator<TKey, Page<TKey>> creator, TKey sentinel)
	{
		this.creator = creator;
		root = creator.create(true);
		root.add(sentinel);
	}
	
	public boolean contains(TKey key)
	{
		return contains(root, key);
	}
	
	protected boolean contains(Page<TKey> page, TKey key)
	{
		if(page.isExternal())
		{
			return page.contains(key);
		}
		
		Page<TKey> next = page.next(key);
		return contains(next, key);
	}
	
	public void add(TKey key)
	{
		add(root, key);
		if(root.isFull())
		{
			Page<TKey> left = root;
			Page<TKey> right = root.split();
			root = creator.create(false);
			root.add(left);
			root.add(right);
		}
	}
	
	protected void add(Page<TKey> p, TKey key)
	{
		if(p.isExternal())
		{
			p.add(key);
			return;
		}
		Page<TKey> next = p.next(key);
		add(next, key);
		if(next.isFull())
			p.add(next.split());
		return;
	}
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
