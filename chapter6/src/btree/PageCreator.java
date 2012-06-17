package btree;

public interface PageCreator<TKey extends Comparable<TKey>, TPage extends Page<TKey>>{
	TPage create(boolean isLeaf);
}
