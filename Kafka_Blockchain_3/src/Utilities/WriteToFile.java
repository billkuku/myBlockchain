package Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

/**
 *  there are two ways to save data in file, save at first line and at last line in file
 *  the methods here are now used for saving log data for block conflict in log-file. 
 *  and hash data in visualledger-file. 
 * @author beier
 *
 */

public class WriteToFile {
	//save data to file, always write to first line
	public static void insert(String fileName, String insertContent) throws IOException {
	    File yourFile = new File(fileName);
	    yourFile.createNewFile(); // if file already exists will do nothing 
	  	LineIterator li = FileUtils.lineIterator(yourFile);
	    File tempFile = File.createTempFile("prependData", ".tmp");
	    BufferedWriter w = new BufferedWriter(new FileWriter(tempFile));
	    try {
	        w.write(insertContent+"\r\n");
	        while (li.hasNext()) {
	            w.write(li.next());
	            w.write("\n");
	        }
	    } finally {
	        IOUtils.closeQuietly(w);
	        LineIterator.closeQuietly(li);
	    }
	    FileUtils.deleteQuietly(yourFile);
	    FileUtils.moveFile(tempFile, yourFile);
	    }
	//save data to file at the last line
	public static void appendToFile(String fileName, String appendContent) throws IOException{
	    FileOutputStream o = null;
	    byte[] buff = new byte[]{};
	        buff=appendContent.getBytes();
	        o=new FileOutputStream(fileName,true);
	        o.write("\r\n".getBytes());
	        o.write(buff);
	        o.flush();
	        o.close();
	    }
	//save source data to sourcebyvalidator.txt

}