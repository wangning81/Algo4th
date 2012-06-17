package elementary;

public class Date implements Comparable<Date> {
	private int year;
	private int month;
	private int day;
	
	public Date(int month, int day, int year)
	{
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public Date(String date)
	{
		String[] tokens = date.split(date);
		month = Integer.parseInt(tokens[0]);
		day = Integer.parseInt(tokens[1]);
		year = Integer.parseInt(tokens[2]);	
	}
	
	@Override
	public String toString()
	{
		return  month + "/" + day + "/" + year; 
	}
	
	@Override
	public boolean equals(Object that)
	{
		if(!(that instanceof Date))
			return false;
		Date lhs = (Date)that;
		return year == lhs.year && month == lhs.month && day == lhs.day;
	}
	
	@Override
	public int hashCode()
	{
		return (31 * year + 17 * month + day) % Integer.MAX_VALUE;
	}
	
	public int compareTo(Date that) {
		if(year != that.year) return year - that.year;
		if(month != that.month) return month - that.month;
		if(day != that.day)	return day - that.day;
		return 0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
