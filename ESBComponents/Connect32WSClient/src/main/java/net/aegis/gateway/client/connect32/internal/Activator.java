package net.aegis.gateway.client.connect32.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final Log log = LogFactory.getLog(Activator.class);

	public void start(BundleContext context) throws Exception {
		log.debug("Activator Started");
		log.debug("BundleContext=== " + context.toString());
		/*ServiceReference webContext = context
				.getAllServiceReferences(
						"org.springframework.context.ApplicationContext",
						"(org.springframework.context.service.name=Connect248WSClient)")[0];
		log.debug("ServiceReference for webContext=" + webContext.toString());

		ApplicationContext ac = (ApplicationContext) context
				.getService(webContext);
		ApplicationConfigurationContext.initialize(ac);
*/
	}

	public void stop(BundleContext context) throws Exception {
		log.debug("Activator Stoped");
	}

}
