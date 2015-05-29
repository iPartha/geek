
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class index
{
	public static void main(String[] args)
    {
		String curLine;
		int score = 0;
		boolean negAdverbFind = false;
		int posWordPos = 0;
		try
		{   
			String postive = FileToString("Positive_Words.txt");
			String negative = FileToString("Negative_Words.txt");
			String negAdverb = FileToString("Negative_Adverbs.txt");
			String negInc = FileToString("Negative_Inc.txt");
			File outFile = new File("output.txt");
            FileOutputStream fs = new FileOutputStream(outFile);
            OutputStreamWriter osw = new OutputStreamWriter(fs);    
            BufferedWriter outWrite = new BufferedWriter(osw);
			
			Scanner s = new Scanner( new File("input.txt"));
			while( s.hasNext() )
		    {
				curLine = s.nextLine();
				
				score = 0;
				negAdverbFind = false;
				curLine = curLine.replaceAll("[^A-Za-z0-9 ]", "");
				String[] splitted = curLine.split("\\s+");
				
				for(String word : splitted)
				{
					
					word = word.toLowerCase();
					
					if(negAdverb.contains(word) && WordExactMatch("Negative_Adverbs.txt", word))
					{
						negAdverbFind = true;
						posWordPos = 0;
					}
					
					if (postive.contains(word)&& WordExactMatch("Positive_Words.txt", word))
					{
						if (negAdverbFind && posWordPos < 3)
						{
							score-=2;
						}
						else 
						{
							score++;
						}
						
						negAdverbFind = false;
						
					}
					
					if (negative.contains(word)&& WordExactMatch("Negative_Words.txt", word))
					{
						if (negAdverbFind && posWordPos < 3)
						{
							score+=2;
						}
						else 
						{
							score--;
						}
						
						negAdverbFind = false;
					}
					
					if (negInc.contains(word)&& WordExactMatch("Negative_Inc.txt", word))
					{
						score -= 2;
					}
					
					posWordPos++;
					
									}
				
					System.out.print("Score Value"+score);
					
					if (score > 0)
					{
						outWrite.write("Positive"+"\n");
					}
					else if (score < 0)
					{
						outWrite.write("Negative"+"\n");
					}
					else
					{
						outWrite.write("Neutral"+"\n");
					}
					outWrite.newLine();
		    }
			s.close();
			outWrite.close();
		    
			
		    
		}
		catch(IOException e)
		{	
			System.out.println("Exception");
			System.out.println( e );
		}
		
    }
	
	public static String FileToString(String fileLocation) throws IOException {

	    File file = new File(fileLocation);
	   
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    StringBuffer fileContents = new StringBuffer();
	    String line = br.readLine();
	    while (line != null) {
	    	fileContents.append(line.toLowerCase());
	        line = br.readLine();
	       }

	    br.close();

	    return  fileContents.toString();
	}


public static boolean WordExactMatch(String fileLocation, String matchWord) throws IOException {
	
	 File file = new File(fileLocation);
	 boolean retVal = false;
	    
	    BufferedReader br = new BufferedReader(new FileReader(file));
	    StringBuffer fileContents = new StringBuffer();
	    String line = br.readLine();
	    while (line != null) {
	    	line = line.toLowerCase();
	    	if (matchWord.equals(line))
	    	{
	    		retVal = true;
	    		break;
	    	}
	        line = br.readLine();
	       }

	    br.close();
	    return retVal;
	
}
}



