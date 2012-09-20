package net.aegis.gateway.client.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		// TODO add activation code here
		PD32TestData pdtestdata = new PD32TestData();
		pdtestdata.testPD();
		pdtestdata.testPD();
	}

	public void stop(BundleContext context) throws Exception {
		// TODO add deactivation code here
	}

}
