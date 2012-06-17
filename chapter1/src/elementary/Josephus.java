package elementary;

public class Josephus {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int M = 2, N = 7;
		
		ArrayQueue<Integer> queue = new ArrayQueue<Integer>(N);
		int counter = 1;
		for(int i = 0 ; i < N ; i++)
		{
			queue.enqueue(i);
		}
		while(queue.size() > 0)
		{
			Integer people = queue.dequeue();
			if(counter == M)
			{
				counter = 1;
				System.out.print(people + " ");
			}
			else
			{
				queue.enqueue(people);
				counter++;
			}
		}

	}

}
