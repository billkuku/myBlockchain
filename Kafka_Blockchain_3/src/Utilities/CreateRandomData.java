package Utilities;

/** the methodes here is used for creating random data, 
 * now its only used for creating consumer id while consuming the block at verifier node.
 * 
 * @author beier
 */
import java.util.Random;

public class CreateRandomData {
	 public static String getRandomString(int length){
	     String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(62);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
	 }
}
