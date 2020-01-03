package pt.iscde.codegenerator;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class CodeGeneratorView implements PidescoView {

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		BundleContext context = Activator.getContext();
		
		ServiceReference<JavaEditorServices> javaEditorRef = context.getServiceReference(JavaEditorServices.class);

		JavaEditorServices javaEditor = context.getService(javaEditorRef);
		
		ButtonGenerator buttonGenerator = new ButtonGenerator(viewArea, javaEditor);


		viewArea.setLayout(new RowLayout(SWT.VERTICAL));

		buttonGenerator.addGettersSetters("Add Getters/Setters");
		buttonGenerator.surroundWithTryCatch("Surround with Try/Catch");
		buttonGenerator.generateConstructorUsingFields("Generate Constructor using Fields");

	}

	
}
