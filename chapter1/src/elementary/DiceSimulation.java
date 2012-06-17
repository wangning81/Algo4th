package elementary;

public class DiceSimulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int SIDES = 6;
		double[] diceSum = new double[2 * SIDES + 1];
		for(int i = 1 ; i <= SIDES ; i++)
		{
			for(int j = 1; j <= SIDES ; j++)
			{
				diceSum[i + j] += 1;
			}
		}
		
		for(int k = 2 ; k <= 2 * SIDES ; k++)
		{
			diceSum[k] /= 36.0;
			System.out.println(diceSum[k]);
		}
		
		System.out.println("=================================================");
		
		int N = 1000000;
		
		double[] simDiceSum = new double[2 * SIDES + 1];
		for(int i = 0 ; i < N ; i++)
		{
			simDiceSum[StdRandom.uniform(1, 7) + StdRandom.uniform(1, 7)] += 1; 			
		}
		
		for(int k = 2 ; k <= 2 * SIDES ; k++)
		{
			simDiceSum[k] /= N ;
			System.out.println(simDiceSum[k]);
		}
	}

}
