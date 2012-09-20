package net.aegis.mv.mockdata;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.mv.jaxb.msg.NhinMessage;

public class TestcaseData {
	
	public Transaction getRequestTransaction() {
		return requestTransaction;
	}
	public void setRequestTransaction(Transaction requestTransaction) {
		this.requestTransaction = requestTransaction;
	}
	public NhinMessage getExpectedRequestMessage() {
		return expectedRequestMessage;
	}
	public void setExpectedRequestMessage(NhinMessage expectedRequestMessage) {
		this.expectedRequestMessage = expectedRequestMessage;
	}
	public Transaction getResponseTransaction() {
		return responseTransaction;
	}
	public void setResponseTransaction(Transaction responseTransaction) {
		this.responseTransaction = responseTransaction;
	}
	public NhinMessage getExpectedResponseMessage() {
		return expectedResponseMessage;
	}
	public void setExpectedResponseMessage(NhinMessage expectedResponseMessage) {
		this.expectedResponseMessage = expectedResponseMessage;
	}
	public TestScenario getTestcaseScenario() {
		return testcaseScenario;
	}
	public void setTestcaseScenario(TestScenario testcaseScenario) {
		this.testcaseScenario = testcaseScenario;
	}
	public String getTestcaseId() {
		return testcaseId;
	}
	public void setTestcaseId(String testcaseId) {
		this.testcaseId = testcaseId;
	}

	private String testcaseId;
	private Transaction requestTransaction;
	private NhinMessage expectedRequestMessage;
	private Transaction responseTransaction;
	private NhinMessage expectedResponseMessage;
	private TestScenario testcaseScenario;
	
}
