package pt.iscde.codegenerator;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class ButtonGenerator {

	private JavaEditorServices javaEditor;
	private WindowBuilder windowBuilder;
	private GenerateCode generateCode;
	private JavaEditorVisitor visitor;

	public ButtonGenerator(Composite viewArea, JavaEditorServices javaEditor) {
		this.javaEditor = javaEditor;
		this.visitor = new JavaEditorVisitor();
		this.generateCode = new GenerateCode(javaEditor, this.visitor);
		this.windowBuilder = new WindowBuilder(generateCode);
	}

	public void addGettersSetters(String name, Composite viewArea, JavaEditorServices javaEditor) {
		Button button = new Button(viewArea, SWT.VERTICAL);
		button.setText(name);
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Multimap<String,String> map = ArrayListMultimap.create();
				File file = javaEditor.getOpenedFile();
				javaEditor.parseFile(file, visitor);				

				if(!visitor.getFields().isEmpty()) {

					for(String s: visitor.getFieldNames()) {
						String[] type_name = s.split(" ");

						map.put(type_name[0], type_name[1].substring(0, type_name[1].length()-2));
					}

					windowBuilder.addGettersSetters(viewArea, map);
					

				} else {
					System.out.println("There are no fields");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void surroundWithTryCatch(String name, Composite viewArea, JavaEditorServices javaEditor) {
		Button button = new Button(viewArea, SWT.VERTICAL);
		button.setText(name);
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File file = javaEditor.getOpenedFile();
				ITextSelection textSelected = javaEditor.getTextSelected(file);
				
				String text = "/*" + textSelected.getText() + "*/\n\n";
				javaEditor.insertText(file, text, textSelected.getOffset(), textSelected.getLength() + 4);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	
}
