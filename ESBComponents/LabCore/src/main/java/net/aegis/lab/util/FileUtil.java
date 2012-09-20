package net.aegis.lab.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author ram.ghattu
 */
public class FileUtil {

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        // Get the size of the file
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large to upload "+file.getName());
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
