/**
 * Copyright (c) 2011 - AEGIS.net, Inc. - All Rights Reserved
 */

package net.aegis.labcommons.util;

/**
 *
 * Venkat.Keesara
 * Feb 22, 2012
 */
public class StringUtil {

	/**
	 * Method to get the substring of length n from the end of a string.
	 * 
	 * @param inputString
	 *            - String from which substring to be extracted.
	 * @param subStringLength
	 *            - int Desired length of the substring.
	 * @return lastCharacters- String
	 * @author Zaheer Paracha
	 */
	public static String getLastnCharacters(String inputString, int subStringLength) {
		int length = inputString.length();
		if (length <= subStringLength) {
			return inputString;
		}
		int startIndex = length - subStringLength;
		return inputString.substring(startIndex);
	}
	
}
