package elementaryST;

public class InterpolationSearchST<TValue> extends BinarySearchST<Double, TValue> {

	@SuppressWarnings("unchecked")
	public InterpolationSearchST()
	{
		keys = new Double[capicity];
		values = (TValue[])new Object[capicity];
	}
	
	@Override
	public int rank(Double d)
	{
		return rank(d, 0, this.size() - 1);
	}
	
	private int rank(Double d, int low, int high)
	{
		if(low <= high)
		{
			double r = 0;
			if(low < high)
				r = (d - keys[low])/(keys[high] - keys[low]);
			
			if(r > 1) return high + 1;
			if(r < 0) return low;
			
			int p = low + (int)((high - low) * r);
			int cmp = d.compareTo(keys[p]);
			if(cmp < 0)
				return rank(d, low, p - 1);
			else if(cmp > 0)
				return rank(d, p + 1, high);
			else 
				return p;
		}
		return low;
	}
	
	public static void main(String[] args) {

		InterpolationSearchST<String> st = new InterpolationSearchST<String>();
		st.put(1.0, "1");
		st.put(2.0, "2");
		st.put(3.0, "3");
		st.put(4.0, "4");
		st.put(5.0, "5");
		st.put(2.5, "5");
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(10.0, "5");
		System.out.println(InterpolationSearchST.isValid(st));
		
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(4.33, "A+");
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(4.00, "A");
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(3.67, "A-");
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(3.33,"B+");
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(3.00, "B");
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(2.67, "B-");
		System.out.println(InterpolationSearchST.isValid(st));
		st.delete(2.67);
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(2.33, "C+");
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(2.00, "C");
		System.out.println(InterpolationSearchST.isValid(st));
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(1.67, "C-");
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(1.00, "D");
		System.out.println(InterpolationSearchST.isValid(st));
		st.delete(3.67);
		System.out.println(InterpolationSearchST.isValid(st));
		st.put(0.00, "F");
		
		System.out.println(InterpolationSearchST.isValid(st));
	
	}

}
