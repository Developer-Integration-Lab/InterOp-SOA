package net.aegis.gateway.client.internal;
import net.aegis.labcommons.service.PatientDiscovery;
import net.aegis.labcommons.service.QueryDocument;
import net.aegis.labcommons.service.RetrieveDocument;

public class ServiceFactory {

	private static ServiceFactory instance;
	private static final String PD_SERVICE_IMPL_FOR_248 = "net.aegis.gateway.client.connect248.pd.PatientDiscoveryImpl";
	private static final String QD_SERVICE_IMPL_FOR_248 = "net.aegis.gateway.client.connect248.qd.QueryDocumentImpl";
	private static final String RD_SERVICE_IMPL_FOR_248 = "net.aegis.gateway.client.connect248.rd.RetrieveDocumentImpl";
	
	private ServiceFactory() {
		
	}

	/**
	 * @return ServiceFactory
	 */
	public static ServiceFactory getInstance() {
		if (instance == null) {
			instance = new ServiceFactory();
		}
		return instance;
	}
	
	public PatientDiscovery getPDServiceFor248() {
		
		PatientDiscovery patientDiscovery = null; 
		Object instance = getServiceObject(PD_SERVICE_IMPL_FOR_248);
		if(instance!=null && instance instanceof PatientDiscovery){
			patientDiscovery = (PatientDiscovery)instance;
		}
		
	/*	patientDiscovery =   
			new net.aegis.gateway.client.connect248.pd.PatientDiscoveryImpl();*/
		return patientDiscovery;
	}
	public QueryDocument getQDServiceFor248(){
		QueryDocument queryDocument = null;
		
		Object instance = getServiceObject(QD_SERVICE_IMPL_FOR_248);
		if(instance!=null && instance instanceof QueryDocument){
			queryDocument = (QueryDocument)instance;
		}
		/*queryDocument = new net.aegis.gateway.client.connect248.qd.QueryDocumentImpl();*/
		return queryDocument;
	}
	public RetrieveDocument getRDServiceFor248(){
		RetrieveDocument retrieveDocument = null;
		
		Object instance = getServiceObject(RD_SERVICE_IMPL_FOR_248);
		if(instance!=null && instance instanceof RetrieveDocument){
			retrieveDocument = (RetrieveDocument)instance;
		}
		/*retrieveDocument = new net.aegis.gateway.client.connect248.rd.RetrieveDocumentImpl();*/
		return retrieveDocument;
	}
	/*public PatientDiscovery getPDServiceFor32(){
		PatientDiscovery patientDiscovery =  new net.aegis.gateway.client.connect32.pd.PatientDiscoveryImpl();
		return patientDiscovery;
	}*/
	
	private Object getServiceObject(String className){
        try {
            Class clazz = Class.forName(className);
            if(clazz!=null){
            	
            	Object instanceObj =  clazz.newInstance();
            	return instanceObj;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Service impl. not found:"
                    + className, e);
        } catch (InstantiationException e) {
            throw new RuntimeException(
                    "Service impl.:" + className, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Service impl.:" + className, e);
        }
        return null;
	}
}
