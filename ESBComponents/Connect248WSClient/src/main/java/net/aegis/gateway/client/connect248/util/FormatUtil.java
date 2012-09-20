/*
 * AEGIS.net, Inc. (c) 2011 
 */
package net.aegis.gateway.client.connect248.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.aegis.gateway.client.exception.WSClient248RuntimeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Venkat.Keesara
 */
public class FormatUtil {
	
    static Log log = LogFactory.getLog(FormatUtil.class);
    private static final String HOME_COMMUNITY_ID_PREFIX="urn:oid:";

    public static String appendPrefixForHomeCommunityId(String communityId) {
        // Set the Audit Source Id (community id)
        if (communityId != null) {
            log.debug("communityId prior to prepfix urn:oid" + communityId);
            if (!communityId.startsWith(HOME_COMMUNITY_ID_PREFIX)) {
                communityId = HOME_COMMUNITY_ID_PREFIX + communityId ;
            }
        }
        return communityId;
    }
    
    public static String getContentsFromInputStream(InputStream is) {
		// ...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader input = new BufferedReader(reader);
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
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
			throw new WSClient248RuntimeException(ex);
		}

		return contents.toString();
	}
}
