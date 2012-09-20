package net.aegis.lab.web.action.admin;

import java.util.List;

import net.aegis.lab.dao.pojo.Applicationproperties;
import net.aegis.lab.manager.ApplicatiopropertiesManager;
import net.aegis.lab.web.action.BaseAction;

public class ApplicationProperties extends BaseAction {

    private static final long serialVersionUID = 1L;
    private List<Applicationproperties> applicationpropertiesMapList;
    private Applicationproperties applicationproperties;
    private List<Applicationproperties> updatedApppropslist;
    private List<String> propertyvalue;
    private String changedPropertyInfo;
 
    @Override
    public String execute() throws Exception {

       log.info("ApplicationProperties.execute() - INFO");

       try {
            setApplicationpropertiesMapList(ApplicatiopropertiesManager.getInstance().getEditableKeys());
            if (null != applicationpropertiesMapList) {
                log.info("ApplicationProperties.execute() - applicationproperties " + applicationpropertiesMapList.size());
            }
            log.info("ApplicationProperties.execute() - User Option UpdateChanges/Refresh--->>>" + changedPropertyInfo);

           if(changedPropertyInfo != null) {
                // *************************************************************
                // REFRESH FUNCTIONALITY
                // *************************************************************
                if (changedPropertyInfo.equalsIgnoreCase("refresh")) {
                    log.info("ApplicationProperties.execute() - applicationproperties- refresh submit button clicked - INFO");
                    setApplicationpropertiesMapList(ApplicatiopropertiesManager.getInstance().getEditableKeys());
                }

                // *************************************************************
                // UPDATE FUNCTIONALITY
                // *************************************************************
                if (getChangedPropertyInfo().equals("update")) {
                    log.info("ApplicationProperties.execute() - Update Option Selected ");

                    if (null != applicationpropertiesMapList) {
                        log.info("ApplicationProperties.execute() - applicationpropertiesMapList.size()="+applicationpropertiesMapList.size());
                        for (Applicationproperties objTempAppprop : applicationpropertiesMapList) {
                            if (null != objTempAppprop) {
                                log.info("ApplicationProperties.execute() - objTempAppprop.getPropertykey()=" +
                                            objTempAppprop.getPropertykey() + " " +
                                            "objTempAppprop.getPropertyvalue()=" +
                                            objTempAppprop.getPropertyvalue());
                            }
                        }
                    }
                    if (null != propertyvalue) {
                        log.info("ApplicationProperties.execute() - propertyvalue.size()="+propertyvalue.size());
                    }

                    String upkey="";
                    String upvalue="";
                    String updesc="";
                    for (int i=0; i<propertyvalue.size(); i++) {    // for the current implementation, size of propertyvalue will match the total list of app properties
                        String textboxvalue = (String) propertyvalue.get(i);
                        log.info("ApplicationProperties.execute() - textboxvalue=~" + textboxvalue +"~");
                        if (textboxvalue.equals("") || !textboxvalue.isEmpty()) {
                            upkey="";
                            upvalue="";
                            updesc="";

                            upkey=applicationpropertiesMapList.get(i).getPropertykey().toString();
                            upvalue=textboxvalue;
                            updesc=applicationpropertiesMapList.get(i).getDescription().toString();

                            log.info("ApplicationProperties.execute() - New Key Value pair KEY: upkey=~"+upkey+"~ and upvalue=~"+upvalue+"~");
                            applicationproperties= new Applicationproperties();
                            applicationproperties.setPropertykey(upkey);
                            applicationproperties.setPropertyvalue(upvalue);
                            applicationproperties.setDescription(updesc);
                            ApplicatiopropertiesManager.getInstance().update(applicationproperties, "1");             // "1" is not used. Ignore.
                        }
                    }

                    setApplicationpropertiesMapList(ApplicatiopropertiesManager.getInstance().getEditableKeys());
                    this.addActionMessage("Application Properties updated successfully ");
                    return SUCCESS;
                }
            }
        }
        catch (Throwable e) {
            log.error("Throwable", e);
            return this.processException(e);
        }
        return SUCCESS;
    }
    
    public List<Applicationproperties> getUpdatedApppropslist() {
        return updatedApppropslist;
    }

    public void setUpdatedApppropslist(List<Applicationproperties> updatedApppropslist) {
        this.updatedApppropslist = updatedApppropslist;
    }
    
    public List<Applicationproperties> getApplicationpropertiesMapList() {
        return applicationpropertiesMapList;
    }

    public void setApplicationpropertiesMapList(List<Applicationproperties> applicationpropertiesMapList) {
        this.applicationpropertiesMapList = applicationpropertiesMapList;
    }

    public String getChangedPropertyInfo() {
        return changedPropertyInfo;
    }

    public void setChangedPropertyInfo(String changedPropertyInfo) {
        this.changedPropertyInfo = changedPropertyInfo;
    }
   

     public List<String> getPropertyvalue() {
        return propertyvalue;
    }

    public void setPropertyvalue(List<String> propertyvalue) {
        this.propertyvalue = propertyvalue;
    }
}