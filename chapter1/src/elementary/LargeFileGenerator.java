package elementary;
import java.io.*;
import java.util.Random;


public class LargeFileGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File f = new File("300T.txt");
			FileWriter fr = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fr);
			Random rd = new Random();
			
			for(int i = 0 ; i < 300 ; i++)
			{
				bw.write(String.valueOf(rd.nextInt(100)));
				bw.newLine();
			}
			
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
