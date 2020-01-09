package pt.iscde.codegenerator.ext;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscde.codegenerator.services.CodeGeneratorServices;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;

	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
