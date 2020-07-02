package myBlockchain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/** As Kafka is used in this case, this class is deprecated. some get-data-function from the file, 
 * getSourceData(), getPreviousHash(), getLocalPreviousHash(), getBlock(), getBlock() 
 * 
 * @author beier
 */
public class GetData {
	public static String getSourceData() throws IOException{
        //the first line of the file to be the sourceData
		FileInputStream inputStreamSource = new FileInputStream("sourceFile.txt");
		BufferedReader bufferedReaderSource = new BufferedReader(new InputStreamReader(inputStreamSource));
		String sourceData = null;
		sourceData = bufferedReaderSource.readLine();
		bufferedReaderSource.close();
		
		if(sourceData == null) 
			return null;
		else 
			return sourceData;
	}
	/*
	 * param: String a
	 * output: int b
	 * description: ein Text, der beschreibt, was passiert
	 */
	//just for simulating
	public static String getSourceDatabyValidator() throws IOException{
        //the first line of the file to be the sourceData
		FileInputStream inputStreamSource = new FileInputStream("sourceByValidator.txt");
		BufferedReader bufferedReaderSource = new BufferedReader(new InputStreamReader(inputStreamSource));
		String sourceData = null;
		sourceData = bufferedReaderSource.readLine();
		bufferedReaderSource.close();
		
		if(sourceData == null) 
			return null;
		else 
			return sourceData;
	}
	
	public static String getPreviousHash() throws IOException{
        //the first line of the file to be the actual previoushash
		FileInputStream inputStreamPrevHash = new FileInputStream("bcNode.txt");
		BufferedReader bufferedReaderPrevHash = new BufferedReader(new InputStreamReader(inputStreamPrevHash));
		String previousHash = null;
		previousHash = bufferedReaderPrevHash.readLine();
		bufferedReaderPrevHash.close();
		if(previousHash == null) { previousHash = "firstblock";
			return previousHash;}
		else 
			return previousHash;
	}
	
	
	/*public static String getLocalPreviousHash() throws IOException{
        //the first line of the file to be the actual previoushash
		FileInputStream inputStreamPrevHash = new FileInputStream("bcNode.txt");
		BufferedReader bufferedReaderPrevHash = new BufferedReader(new InputStreamReader(inputStreamPrevHash));
		String lpreviousHash = null;
		lpreviousHash = bufferedReaderPrevHash.readLine();
		bufferedReaderPrevHash.close();
		if(lpreviousHash == null){ lpreviousHash = "nothing";
			return null;}
		else 
			return lpreviousHash;
	}*/
	
	public static List<String> getBlock() throws IOException{
        FileInputStream in = new FileInputStream("blockbyvalidator.txt");  
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
       	
		List<String> block = new ArrayList<String>();
		String bhash = null;
        String bsourceData = null;
        String bpreviousHash = null;
        String bstrTimeStamp = null;
        
        bhash = br.readLine();
        bsourceData = br.readLine();
        bpreviousHash = br.readLine();
        bstrTimeStamp = br.readLine();
        br.close();
        
        block.add(bhash);
        block.add(bsourceData);
        block.add(bpreviousHash);
        block.add(bstrTimeStamp);
        
        return block;
    }
	
	
	public static List<String> getBlockByMember() throws IOException{
        FileInputStream in = new FileInputStream("blockbyverifier.txt");  
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
       	
		List<String> block = new ArrayList<String>();
		String bhash = null;
        String bsourceData = null;
        String bpreviousHash = null;
        String bstrTimeStamp = null;
        
        bhash = br.readLine();
        bsourceData = br.readLine();
        bpreviousHash = br.readLine();
        bstrTimeStamp = br.readLine();
        br.close();
        
        block.add(bhash);
        block.add(bsourceData);
        block.add(bpreviousHash);
        block.add(bstrTimeStamp);
        
        return block;
    }
	
}
