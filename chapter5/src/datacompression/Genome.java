package datacompression;
import java.io.*;

import stringhelpers.*;

public class Genome {
	
	private static Alphabet sigma = new Alphabet("ACTG");
	private static int LgR = sigma.lgR();
	
	public static void compress(String inputFile, String outputFile)
	{
		try {
			FileReader reader = new FileReader(inputFile);
			FileOutputStream fos = new FileOutputStream(outputFile);
			
			BinaryOut bo = new BinaryOut(fos);
			StringBuilder sb = new StringBuilder();	
			int c = reader.read();
			while(c != -1)
			{
				sb.append((char)c);
				c = reader.read();
			}
			
			int n = sb.length();
			bo.write(n);
			
			for(int i = 0 ; i < n ; i++)			
				bo.write((char)sigma.toIndex(sb.charAt(i)), LgR);
			
			bo.flush();
			reader.close();
			fos.close();
			bo.flush();
			
		} catch (FileNotFoundException e) {
		}
		catch(IOException e){}
	}
	
	private static void expand(String inputFile, String outputFile)
	{
		try {
			//FileReader reader = new FileReader(inputFile);
			FileInputStream fis = new FileInputStream(inputFile);
			FileOutputStream fos = new FileOutputStream(outputFile);
			
			BinaryIn bin = new BinaryIn(fis);
			int n = bin.readInt();
			for(int i = 0 ; i < n ; i++)
			{
				int r = bin.readBits(LgR);
				fos.write(sigma.toChar(r));
			}
			
			fos.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
		}
		catch(IOException e){}				
	}

	
	public static void main(String[] args) {
		compress("genomeTiny.txt", "tiny.cmprss");
		expand("tiny.cmprss", "tinyOut.txt");
		compress("genomeVirus.txt", "virus.cmprss");
		expand("virus.cmprss", "virusOut.txt");
	}

}
