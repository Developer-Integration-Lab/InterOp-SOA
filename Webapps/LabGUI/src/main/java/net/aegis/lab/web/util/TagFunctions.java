package net.aegis.lab.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * This class provides support for custom tld functions.  We could break this class up into separate specialized classes in the future.
 *
 * @author Tareq.Nabeel
 */
public class TagFunctions {

    private static final Log log = LogFactory.getLog(TagFunctions.class);

    private static SimpleDateFormat dateFormat;

    public static String formatDate(Date value) {
        if (value!=null) {
            return getDateFormat().format(value);
        } else {
            return null;
        }
    }
    
    private static SimpleDateFormat getDateFormat() {
        if (dateFormat==null) {
            ActionSupport baseAction = (ActionSupport)ServletActionContext.getContext().getActionInvocation().getAction();
            dateFormat = new SimpleDateFormat(baseAction.getText("dateFormatPattern"));
        }
        return dateFormat;
    }
    

}
