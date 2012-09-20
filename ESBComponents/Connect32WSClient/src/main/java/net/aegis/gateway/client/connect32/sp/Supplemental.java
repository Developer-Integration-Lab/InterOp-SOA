package net.aegis.gateway.client.connect32.sp;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.aegis.gateway.client.exception.WSClient32RuntimeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Used to manage all Supplemental Test Cases
 * 
 * @author richard.ettema
 */
public class Supplemental {

	private static final Log log = LogFactory.getLog(Supplemental.class);

	/**
	 * XML to Java Class
	 * 
	 * @param f
	 * @return <code>PatientSP</code>
	 */
	public PatientSP getPatientInfo(String fileContent) {
		PatientSP patient = new PatientSP();
		try {
			Class clazz = net.aegis.gateway.client.connect32.sp.ObjectFactory.class;
			ClassLoader cl = clazz.getClassLoader();
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz.getPackage().getName(), cl);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader sr = new StringReader(fileContent);

			Object o = unmarshaller.unmarshal(sr);
			patient = (PatientSP) o;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw new WSClient32RuntimeException(ex);
		}
		return patient;
	}
}
