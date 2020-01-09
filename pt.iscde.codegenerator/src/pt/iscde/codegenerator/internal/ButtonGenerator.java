package pt.iscde.codegenerator.internal;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import pt.iscde.codegenerator.extensibility.ClassInformation;
import pt.iscde.codegenerator.extensibility.UserCode;
import pt.iscde.codegenerator.services.CodeGeneratorServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class ButtonGenerator {

	private JavaEditorServices javaEditor;
	private CodeGeneratorServices codeGenServices;
	
	private GenerateCode generateCode;
	private JavaEditorVisitor visitor;
	private Composite viewArea;
	private ClassInformation editor;

	public ButtonGenerator(Composite viewArea, JavaEditorServices javaEditor, CodeGeneratorServices codeGenServices) {
		this.javaEditor = javaEditor;
		this.codeGenServices = codeGenServices;
		
		this.visitor = new JavaEditorVisitor();
		this.generateCode = new GenerateCode(javaEditor, this.visitor);
		this.viewArea = viewArea;
	}

	public void addGettersSetters(String name) {
		Button button = new Button(viewArea, SWT.VERTICAL);
		button.setText(name);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.setSize(300, 100);
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				visitor.clear();
				Multimap<String,String> map = ArrayListMultimap.create();
				File file = javaEditor.getOpenedFile();
				javaEditor.parseFile(file, visitor);				

				if(!visitor.getFields().isEmpty()) {

					for(String s: visitor.getFieldNames()) {
						String[] type_name = s.split(" ");

						map.put(type_name[0], type_name[1].substring(0, type_name[1].length()-2));
					}

					createInterface(map, "Add Getters and Setters", "Choose attributes to add getters/setters:",2);

				} else {
					System.out.println("There are no fields");
					
				}
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void addGetter(String name) {
		Button button = new Button(viewArea, SWT.VERTICAL);
		button.setText(name);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.setSize(300, 100);
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				visitor.clear();
				Multimap<String,String> map = ArrayListMultimap.create();
				File file = javaEditor.getOpenedFile();
				javaEditor.parseFile(file, visitor);				

				if(!visitor.getFields().isEmpty()) {

					for(String s: visitor.getFieldNames()) {
						String[] type_name = s.split(" ");

						map.put(type_name[0], type_name[1].substring(0, type_name[1].length()-2));
					}

					createInterface(map, "Add Getters", "Choose attributes to add getters",3);

				} else {
					System.out.println("There are no fields");
					
				}
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
	}

	public void addSetter(String name) {
		Button button = new Button(viewArea, SWT.VERTICAL);
		button.setText(name);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.setSize(300, 100);
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				visitor.clear();
				Multimap<String,String> map = ArrayListMultimap.create();
				File file = javaEditor.getOpenedFile();
				javaEditor.parseFile(file, visitor);				

				if(!visitor.getFields().isEmpty()) {

					for(String s: visitor.getFieldNames()) {
						String[] type_name = s.split(" ");

						map.put(type_name[0], type_name[1].substring(0, type_name[1].length()-2));
					}

					createInterface(map, "Add Setters", "Choose attributes to add setters:",4);

				} else {
					System.out.println("There are no fields");
					
				}
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
	}

	public void surroundWithTryCatch(String name) {
		Button button = new Button(viewArea, SWT.VERTICAL);
		button.setText(name);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				File file = javaEditor.getOpenedFile();
				ITextSelection textSelected = javaEditor.getTextSelected(file);

				generateCode.surroundWithTryCatch(textSelected);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	public void generateConstructorUsingFields(String name) {
		Button button = new Button(viewArea, SWT.VERTICAL);
		button.setText(name);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				visitor.clear();
				Multimap<String,String> map = ArrayListMultimap.create();
				File file = javaEditor.getOpenedFile();
				javaEditor.parseFile(file, visitor);

				if(!visitor.getFields().isEmpty()) {

					for(String s: visitor.getFieldNames()) {
						String[] type_name = s.split(" ");

						map.put(type_name[0], type_name[1].substring(0, type_name[1].length()-2));
					}

//					createConstructorInterface(map);
					createInterface(map, "Generate Constructor Using Fields", "Choose the fields to add into the constructor:",1);

				} else {
					generateCode.generateConstructor(new ArrayList<String>());
				}
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

	}
	
	public void generateToString(String name) {
		Button button = new Button(viewArea, SWT.VERTICAL);
		button.setText(name);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				visitor.clear();
				Multimap<String,String> map = ArrayListMultimap.create();
				File file = javaEditor.getOpenedFile();
				javaEditor.parseFile(file, visitor);
				
				if(!visitor.getFields().isEmpty()) {

					for(String s: visitor.getFieldNames()) {
						String[] type_name = s.split(" ");

						map.put(type_name[0], type_name[1].substring(0, type_name[1].length()-2));
					}
					
					createInterface(map, "Generate To String method", "Choose the fields to add into the toString method:",0);

				} else {
					System.out.println("There are no fields");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
	}
		
	public void generateUserCode(String name, UserCode o) {
		Button button = new Button(viewArea, SWT.VERTICAL);
		button.setText(name);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				visitor.clear();
				File file = javaEditor.getOpenedFile();
				javaEditor.parseFile(file, visitor);
				editor = new ClassInformation(visitor.getFields(), visitor.getMethods(),file);
				
				javaEditor.insertTextAtCursor(o.generateCode(editor, codeGenServices));
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
	}

	/* -------------------------------------------------------------------------------------------------- */
	
	private void createInterface(Multimap<String, String> map, String title, String desc, int genCode) {
		ArrayList<String> selectedFields = new ArrayList<String>();
		Shell shell = new Shell();
		shell.setText(title);
		shell.setLayout(new GridLayout(1, false));
		
		Rectangle screenSize = shell.getDisplay().getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2, (screenSize.height - shell.getBounds().height) / 2);
		shell.setSize(300, 300);
		

		Text t = new Text(shell, SWT.SINGLE);
		t.setBackground(shell.getBackground());
		t.setText(desc);

		Composite buttons = new Composite(shell, SWT.NONE);
		buttons.setLayout(new RowLayout(SWT.VERTICAL));
		buttons.setSize(300,400);

		for (String key : map.keySet()) {
			for (String value : map.get(key)) {
				Button button = new Button(buttons, SWT.CHECK);
				button.setText(key + " " + value);
				button.setSize(300, 50);
				button.setVisible(true);
			}
		}
		buttons.setVisible(true);

		Button submit = new Button(shell, SWT.PUSH);
		submit.setText("Sumbit");
		submit.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, false));

		submit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedFields.clear();

				for(Control control : buttons.getChildren()) {
					Button button = (Button) control;

					if(button.getSelection()) {
						selectedFields.add(button.getText());
					}
				}
				
				switch(genCode) {
					
					case 0:
						generateCode.generateToString(selectedFields);
						break;
					case 1:
						generateCode.generateConstructor(selectedFields);
						break;
					case 2:
						generateCode.generateGettersSetters(selectedFields);
						break;
					case 3:
						generateCode.generateGetters(selectedFields);
						break;
					case 4:
						generateCode.generateSetters(selectedFields);
						break;
				}
				shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		shell.pack();
		shell.setVisible(true);	
	}

	
	
}

