package pt.iscde.codegenerator.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static Activator instance;
	
	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		this.instance = this;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		this.instance = null;
	}

	public static Activator getActivator() {
		return instance;
	}
	
	public JavaEditorServices getJavaEditor() {
		ServiceReference<JavaEditorServices> javaEditorRef = context.getServiceReference(JavaEditorServices.class);
		return context.getService(javaEditorRef);
	}
}
