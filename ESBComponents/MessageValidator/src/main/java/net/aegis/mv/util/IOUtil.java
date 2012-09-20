/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */
package net.aegis.mv.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Venkat.Keesara
 * Apr 20, 2012
 **/

public class IOUtil {

	public static String getContentsFromInputStream(InputStream is) {
	    //...checks on aFile are elided
	    StringBuilder contents = new StringBuilder();
	
	    try {
	        //use buffering, reading one line at a time
	        //FileReader always assumes default encoding is OK
	        InputStreamReader reader = new InputStreamReader(is);
	        BufferedReader input = new BufferedReader(reader);
	        try {
	            String line = null; //not declared within while loop
	    /*
	             * readLine is a bit quirky :
	             * it returns the content of a line MINUS the newline.
	             * it returns null only for the END of the stream.
	             * it returns an empty String if two newlines appear in a row.
	             */
	            while ((line = input.readLine()) != null) {
	                contents.append(line);
	                contents.append(System.getProperty("line.separator"));
	            }
	        } finally {
	            input.close();
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	
	    return contents.toString();
	}

}
