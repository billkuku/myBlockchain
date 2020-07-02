package myBlockchain;
/** this class is used for verifying block from distributed-block-topic by verifier-nodes.
 * get block from distributed-block-topic and verify it, save new hash locally 
 * in visualledger-file, if validating fail, log the block in log-file
 * 
 * @beier
 */
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Utilities.WriteToFile;

public class VerifyBlock {
	public static String blockVerify(String block, String lprevioushash) throws IOException, NoSuchAlgorithmException{
		//distruct block in hash, prehash,ts, and transaction
		System.out.println("Block to Verify is: " + block);
		String bhash = block.substring(0,64);
		String bpreviousHash = block.substring(64,128);
		String btimeStamp = block.substring(128,141);
		String bsourceData = block.substring(141);
		
		//initiat a new calculated prehash in verify-process
		String lhash = null;

		/** prepare the data and calculate hash
		*/
        	String dataToHash = lprevioushash + btimeStamp + bsourceData;
        	//hash data use SHA256
		byte[] byteSource= dataToHash.getBytes();
    		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedhash = digest.digest(byteSource);
		
		/** transfer hash from byte[] to String
		*/
		StringBuilder sb = new StringBuilder();
		for(int i=0; i< encodedhash.length ;i++){
		    sb.append(Integer.toString((encodedhash[i] & 0xff) + 0x100, 16).substring(1));
		}
		lhash = sb.toString();
		
		/**verify hash through comparing lhash and bhash, if faild block will be add to log-file.
		*/
		if(!lhash.equals(bhash)){
			WriteToFile.appendToFile("log", block + " verify failed!");
			System.out.println("verify failed! Related Block data was saved in log-file");
		}
		else {
			System.out.println("verify succeed!!");
		}
		//block from validator will anywhere regard as valid and be returned, just for temp use
		return bhash;
			}
}
