package binraySearchTree;

import elementary.StdRandom;

public class TreeHeightTestClient {

	public static void main(String[] args) {
		for(int i = 0 ; i < 2; i++)
		{
			RedBlackTree<Integer, Integer> rbt = new RedBlackTree<Integer, Integer>();
			BinarySearchTreeST<Integer, Integer> bst = new BinarySearchTreeST<Integer, Integer>();
			for(int k = 0 ; k < 16 ; k++)
			{
				int r = StdRandom.uniform(0, 100000);
				rbt.put(r, r);
				bst.put(r, r);
			}
			System.out.println("Round: " + i);
			System.out.println("RedBlackTree height:" + rbt.height());
			System.out.println("BinarySearchTree height:" + bst.height());
		}

	}

}
