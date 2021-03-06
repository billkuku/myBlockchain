package myBlockchain;

/** this class is used for validating transaction from transaction-topic by validate-node.
 * it is integrated in AppValidator.
 * 
 * @author beier
 */
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import Utilities.WriteToFile;
//official vision of block creater, source and previoushash get from topic
public class ValidateTransaction {
		public static String createBlock(String source, String previousHash) throws NoSuchAlgorithmException, IOException {			
				/** initialize the block, create the var in block.
				*/
				String hash = null;
				long timeStamp = new Date().getTime();
				String strTimeStamp = Long.toString(timeStamp); 
		        	String dataToHash = previousHash + strTimeStamp + source;
		        
		       		/**The SHA-256 algorithm generates 256-bit hash. This is a one-way function, 
				 * so the result cannot be decrypted back to the original value.
				 */
				byte[] byteSource= dataToHash.getBytes();
		    		MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] encodedhash = digest.digest(byteSource);
			
				/** transfer hashcode to String
				*/
		      		StringBuilder sb = new StringBuilder();
				for(int i=0; i< encodedhash.length ;i++)
				{
				    sb.append(Integer.toString((encodedhash[i] & 0xff) + 0x100, 16).substring(1));
				}
				hash = sb.toString();
				
				/** save the hash to visualledger-file and return the block
				*/
				WriteToFile.insert("visualledger", hash);
				String block = hash + dataToHash;
				//block sequence is: hash0-64, previousHash65-128,Timestamp129-141,source142-
			        //Acceptable data types from kafka is: bool,string,int,bytes,double, need a customer serder
				System.out.println(block);
				return block;
    }
}
