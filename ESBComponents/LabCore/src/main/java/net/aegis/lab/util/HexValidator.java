/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sunil.Bhaskarla
 */


public class HexValidator{
 
   private Pattern pattern;
   private Matcher matcher;

   /* Implement others as needed later on */
   public static enum ValidatorPatternType {
       SHA1
      ,UNDEFINED
   }

 
   private static final String HEX_PATTERN = "^([A-Fa-f0-9]{40})$";
 
   public HexValidator(){
	  pattern = Pattern.compile(HEX_PATTERN);
   }
 
   /**
   * Validate hex with regular expression
   * @param hex hex for validation
   * @return true valid hex, false invalid hex
   */
   public boolean validate(final String hex){

          if (hex != null && !"".equals(hex)) {
              matcher = pattern.matcher(hex);
              return matcher.matches();
          }
        return false;
   }
}
