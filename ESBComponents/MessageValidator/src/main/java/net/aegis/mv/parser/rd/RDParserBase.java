package net.aegis.mv.parser.rd;

import net.aegis.mv.parser.ParserHelper;
import net.aegis.mv.util.MVConstants;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

abstract class RDParserBase {
	protected ParserHelper parserHelper = ParserHelper.getInstance();
	
	protected String getMessageId(Node node) {
		Element msgIdElement = parserHelper.getElementByTagNameDirect(node, MVConstants.MESSAGE_ID);
		return parserHelper.getTextValue(msgIdElement);
	}
	
	protected String getHomeCommunityId(Node node) {
		Element hcIdElement = parserHelper.getElementByTagNameDirect(node, MVConstants.RD_HCID);
		return parserHelper.getTextValue(hcIdElement);
	}
	
	protected String getRepositoryId(Node node) {
		Element repoIdElement = parserHelper.getElementByTagNameDirect(node, MVConstants.RD_REPO_ID);
		return parserHelper.getTextValue(repoIdElement);
	}
	
	protected String getDocuemntId(Node node) {
		Element docIdElement = parserHelper.getElementByTagNameDirect(node, MVConstants.RD_DOCUMENT_ID);
		return parserHelper.getTextValue(docIdElement);
	}	
}
