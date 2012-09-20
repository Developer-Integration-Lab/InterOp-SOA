package net.aegis.lab.web.action.participant;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Abhay.Bakshi
 */
public class DocumentContent implements Serializable {

    protected static final Log log = LogFactory.getLog(DocumentContent.class);
    private String patientInfo;     // for ex - xml document <ClinicalDocument> â€¦ </ClinicalDocument>

    public DocumentContent() {
        // empty constructor
        log.info("DocumentContent() - empty constructor - INFO");
    }

    public DocumentContent(String patientInfo) {
        log.info("DocumentContent(String) - constructor - INFO");
        this.patientInfo = patientInfo;
    }

    public String getPatientInfo() {
        log.info("DocumentContent.getPatientInfo() - INFO");

        String specialHTMLCharPatientInfo = this.patientInfo;
        String regularHTMLPatientInfo = null;

        regularHTMLPatientInfo = replace(specialHTMLCharPatientInfo, "&amp;", "&");
        regularHTMLPatientInfo = replace(regularHTMLPatientInfo, "&lt;", "<");
        regularHTMLPatientInfo = replace(regularHTMLPatientInfo, "&gt;", ">");
        regularHTMLPatientInfo = replace(regularHTMLPatientInfo, "&apos;", "\'");
        regularHTMLPatientInfo = replace(regularHTMLPatientInfo, "&quot;", "\"");

        this.patientInfo = regularHTMLPatientInfo;
        return this.patientInfo;
    }

    public void setPatientInfo(String patientInfo) {
        log.info("DocumentContent.setPatientInfo(String) - INFO");
        this.patientInfo = patientInfo;
    }

    /**
     * Utility method.  Can get refactored into a util package when codebase increases.
     *
     * @param text  source object to work on.
     * @param repl  substring to search for in the source object and be replaced.
     * @param with  substring that replaces 'repl' substring object in the source object.
     * @return String source object which is potentially modified.
     */
    private static String replace(String text, String repl, String with) {

        log.info("DocumentContent.replace() - INFO");

        if (text == null || repl == null || with == null || repl.length() == 0) {
            return text;
        }

        StringBuffer buf = new StringBuffer (text.length());
        int searchFrom = 0;
        while (true) {
            int foundAt = text.indexOf(repl, searchFrom);
            if (foundAt == -1) {
                break;
            }

            buf.append(text.substring(searchFrom, foundAt)).append(with);
            searchFrom = foundAt + repl.length();
        }
        buf.append(text.substring(searchFrom));

        return buf.toString();
    }
}
