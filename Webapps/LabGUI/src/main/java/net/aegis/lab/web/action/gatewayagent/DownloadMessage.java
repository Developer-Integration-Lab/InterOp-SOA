package net.aegis.lab.web.action.gatewayagent;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import net.aegis.gateway.agent.dao.pojo.Transaction;
import net.aegis.gateway.agent.manager.GatewayAgentManager;
import net.aegis.lab.web.action.BaseAction;



/**
 * ILT-300
 * @author Sunil.Bhaskarla
 */
public class DownloadMessage extends BaseAction {

    private static final long serialVersionUID = 1L;

    private String filename;
    private String contentType;    
    private InputStream fileStream;
    private Transaction transaction;
    private Integer transactionId;

    @Override
    public String execute() throws Exception {

        log.info("DownloadMessage.execute() - INFO"+filename);
        
        transaction = GatewayAgentManager.getInstance().findById(transactionId);
        if (transaction!=null && transaction.getMessage()!=null) {
            fileStream = new ByteArrayInputStream(transaction.getMessage());
        }
        return SUCCESS;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    public void setFileStream(InputStream fileStream) {
        this.fileStream = fileStream;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

}
