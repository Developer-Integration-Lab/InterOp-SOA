/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aegis.lab.parser.message;

import net.aegis.lab.util.LabConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Sunil.Bhaskarla
 */
public class MessageHandler extends DefaultHandler {

    public static final String CLASSCODE_PRIVACYPOLICY = "57017-6";
    public static final String CLASSCODE_PRIVACYPOLICY_ACK = "57016-8";
    private static final String CODINGSCHEME_RF = "2.16.840.1.113883.6.1";
    private static final String CLASSCODE_URNUUID = "urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a";
    private static final String CLASSIFICATION = ":Classification";
    private static final String CLASSIFICATIONSCHEME = "classificationScheme";
    private static final String NODEREPRESENTATION = "nodeRepresentation";
    private static final String VALUE = ":Value";
    private static final String NAME = ":Name";
    private static final String EXTERNALIDENTIFIER = ":ExternalIdentifier";
    private static final String LOCALIZEDSTRING = ":LocalizedString";
    private static final String DOCIDENTIFICATION_URNUUID = "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab";
    private static final String IDENTIFICATIONSCHEME = "identificationScheme";
    private static final String DOCIDVALUE = "value";
    private static final String DOCUNIQUEID = "XDSDocumentEntry.uniqueId";

    public static enum MessageType {
        /* add others here as needed */

        QDR
    }
    private static Log log = LogFactory.getLog(MessageHandler.class);
    private String classCodeList = "";
    private String classCodeDocIDMap = "";
    private int policyDocCt = 0;
    private int actualDocCt = 0;
    private boolean hotRead = false;
    private int nodeRepIdx = -1;
    private String codingScheme = "";
    private String tempCharVal = "";
    private String tempClassCode = "";
    private int nodeRepDocIdx = -1;
    private int nodeRepUniqueIdx = -1;
    private boolean hotDocRead = false;
    private String tempDocUniqueId = "";
    private boolean hotUniqeDocId = false;
    private MessageType messageType = MessageType.QDR;

    public MessageHandler(MessageType messageType) {
        super();
        setMessageType(messageType);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (attributes.getLength() == 0) {
            //logMsg("no-op");
            } else {
            if (MessageType.QDR.equals(getMessageType())) {
                qdr_start(uri, localName, qName, attributes);
            }
            tempCharVal = "";
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempCharVal = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (MessageType.QDR.equals(getMessageType())) {
            qdr_end(qName);
        }
    }

    private void logMsg(String msg) {
        log.info(msg);
    }

    @SuppressWarnings("empty-statement")
    private void qdr_start(String uri, String localName, String qName, Attributes attributes) {
        /* begin QDR classcode check **********************************************************************/
        //logMsg(" ");
        if (qName.endsWith(CLASSIFICATION)) {
            //logMsg("match1");
            for (int index = 0; index < attributes.getLength(); index++) {
                //logMsg(                attributes.getQName(index) + " => " + attributes.getValue(index));
                if (NODEREPRESENTATION.equals(attributes.getQName(index))) {
                    nodeRepIdx = index;
                }
            }
            for (int index = 0; index < attributes.getLength(); index++) {
                logMsg("\nattrib dump: " + attributes.getQName(index) + " value:" + attributes.getValue(index) + " len:" + attributes.getLength());
                if (CLASSIFICATIONSCHEME.equals(attributes.getQName(index)) && CLASSCODE_URNUUID.equalsIgnoreCase(attributes.getValue(index))) {

                    if (nodeRepIdx > -1) {
                        tempClassCode = attributes.getValue(nodeRepIdx);
                    }
                    hotRead = true;
                }
            }
            /* end QDR classcode check  **********************************************************************/
        }
        /*    Start extracting doc ids for ILT-226 */
        if (qName.endsWith(EXTERNALIDENTIFIER)) {
            //logMsg("match1");
            for (int docIndex = 0; docIndex < attributes.getLength(); docIndex++) {
                //logMsg(                attributes.getQName(index) + " => " + attributes.getValue(index));
                if (DOCIDVALUE.equals(attributes.getQName(docIndex))) {
                    nodeRepDocIdx = docIndex;
                }
            }
            for (int docIndex = 0; docIndex < attributes.getLength(); docIndex++) {
                logMsg("\nattrib dump: " + attributes.getQName(docIndex) + " value:" + attributes.getValue(docIndex) + " len:" + attributes.getLength());
                if (IDENTIFICATIONSCHEME.equals(attributes.getQName(docIndex)) && DOCIDENTIFICATION_URNUUID.equalsIgnoreCase(attributes.getValue(docIndex))) {
                    if (nodeRepDocIdx > -1) {
                        tempDocUniqueId = attributes.getValue(nodeRepDocIdx);
                    }
                    hotDocRead = true;
                }
            }


        }

        if (qName.endsWith(LOCALIZEDSTRING)) {
            //logMsg("match1");
            for (int docIndex = 0; docIndex < attributes.getLength(); docIndex++) {
                //logMsg(                attributes.getQName(index) + " => " + attributes.getValue(index));
                if (DOCIDVALUE.equals(attributes.getQName(docIndex))) {
                    nodeRepUniqueIdx = docIndex;
                }
            }
            for (int docIndex = 0; docIndex < attributes.getLength(); docIndex++) {
                logMsg("\nattrib dump: " + attributes.getQName(docIndex) + " value:" + attributes.getValue(docIndex) + " len:" + attributes.getLength());
                if (DOCUNIQUEID.equals(attributes.getValue(docIndex))) {
                    if (nodeRepUniqueIdx > -1) {
                        hotUniqeDocId = true;
                    }
                }
            }

            /* End extracting doc ids for ILT-226 */
        }

        if (qName.indexOf(VALUE) > -1) {
            
        }
    }

    private void qdr_end(String qName) {
        /* begin QDR classcode check **********************************************************************/
        if (hotRead && qName.endsWith(VALUE)) {
            if (CODINGSCHEME_RF.equals(tempCharVal)) {
                logMsg("finalizing tmp here...." + tempCharVal);

                String classCodeValue = "";
                if (tempClassCode.contains(LabConstants.SPLITTER_CARET)) {
                    String classCodeValues[] = tempClassCode.split(LabConstants.SPLITTER);
                    classCodeValue = classCodeValues[0];
                } else {
                    classCodeValue = tempClassCode;
                }
                if (CLASSCODE_PRIVACYPOLICY.equals(classCodeValue.trim()) || CLASSCODE_PRIVACYPOLICY_ACK.equals(classCodeValue.trim())) {
                    this.policyDocCt++;
                    logMsg("found a privay class code: " + classCodeValue + " policyDocCt=" + policyDocCt);
                } else {
                    this.actualDocCt++;
                    logMsg("found a class code: " + classCodeValue + " actualDocCt=" + actualDocCt);

                }

                classCodeList += ((classCodeList.isEmpty()) ? "" : ",") + classCodeValue;
                logMsg("adding to classCodeList here...." + classCodeList);
                hotRead = false;
            }
        }
        /* end QDR classcode check  **********************************************************************/
        // Appending docids and class code for ILT-226
        if (hotUniqeDocId) {
            log.info("tempCharVal*******" + tempDocUniqueId + tempClassCode);
            classCodeDocIDMap += ((classCodeDocIDMap.isEmpty()) ? "" : ",") + tempDocUniqueId+"~~"+tempClassCode;
            hotUniqeDocId = false;            
        }
    }

    public int getActualDocCt() {
        return actualDocCt;
    }

    //ILT-226 method to return classcode and docids map as a string
    public String getClassCodeDocIdMap() {
        return classCodeDocIDMap;
    }

    public String getClassCodeList() {
        return classCodeList;
    }

    public void setClassCodeList(String classCodeList) {
        this.classCodeList = classCodeList;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getPolicyDocCt() {
        return policyDocCt;
    }
}

