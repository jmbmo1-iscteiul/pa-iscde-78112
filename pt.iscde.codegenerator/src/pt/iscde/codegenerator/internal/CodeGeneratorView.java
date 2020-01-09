package pt.iscde.codegenerator.internal;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscde.codegenerator.extensibility.UserCode;
import pt.iscde.codegenerator.internal.Activator;
import pt.iscde.codegenerator.services.CodeGeneratorServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class CodeGeneratorView implements PidescoView {

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		BundleContext context = Activator.getContext();

		ServiceReference<JavaEditorServices> javaEditorRef = context.getServiceReference(JavaEditorServices.class);

		JavaEditorServices javaEditor = context.getService(javaEditorRef);
		
		context.registerService(CodeGeneratorServices.class, new CodeGeneratorServicesImpl(), null);
		
		ServiceReference<CodeGeneratorServices> codeGenRef = context.getServiceReference(CodeGeneratorServices.class);
		CodeGeneratorServices codeGenServices = context.getService(codeGenRef);

		
		viewArea.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		viewArea.setLayout(new GridLayout());
		
		ButtonGenerator buttonGenerator = new ButtonGenerator(viewArea, javaEditor, codeGenServices);

		buttonGenerator.addGettersSetters("Add Getters/Setters");
		buttonGenerator.addGetter("Add Getters");
		buttonGenerator.addSetter("Add Setters");
		buttonGenerator.surroundWithTryCatch("Surround with Try/Catch");
		buttonGenerator.generateConstructorUsingFields("Generate Constructor using Fields");
		buttonGenerator.generateToString("Generate toString Method");

		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("pt.iscde.codegenerator.userCode");

		IExtension[] extensions = extensionPoint.getExtensions();
		for(IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for(IConfigurationElement c : confElements) {
				String s = c.getAttribute("name");
				try {
					UserCode o = (UserCode) c.createExecutableExtension("class");
					buttonGenerator.generateUserCode(c.getAttribute("buttonText"), o);
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}


		}

	}
}
