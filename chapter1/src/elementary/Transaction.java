package elementary;


public class Transaction implements Comparable<Transaction>{

	private String who;
	private Date when;
	private double amount;
	private int hashCode = Integer.MAX_VALUE;
	
	public Transaction(String who, Date when, double amount)
	{
		this.who = who;
		this.when = when;
		this.amount = amount;
	}
	
	public Transaction(String trans)
	{
		String[] tokens = trans.split(" ");
		this.who = tokens[0];
		this.when = new Date(tokens[1]);
		this.amount = Double.parseDouble(tokens[2]);
	}
	
	@Override
	public String toString()
	{
		return who + " " + when.toString() + " " + amount;
	}
	
	@Override
	public boolean equals(Object that)
	{
		if(!(that instanceof Transaction))
			return false;
		Transaction t = (Transaction)that;
		return this.who == t.who && this.when.equals(t.when) && this.amount == t.amount;
	}
	
	@Override
	public int hashCode()
	{
		if(hashCode == Integer.MAX_VALUE)
		{
		  hashCode = (int)(
					(
							(31 * who.hashCode() + 17 * when.hashCode()) 
							^ Double.doubleToLongBits(amount)) 
							% Integer.MAX_VALUE
					);
		}
		return hashCode;
	}

	public int compareTo(Transaction o) {
		return (int)(this.amount - o.amount);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
