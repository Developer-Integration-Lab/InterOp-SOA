package net.aegis.mv.parser;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.msg.NhinMessage;

public interface INhinMsgParser {
	
	public NhinMessage parse(Transaction transaction);

}
