package pt.iscde.codegenerator;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class ButtonGenerator {

	private JavaEditorServices javaEditor;
	private GenerateCode generateCode;
	private JavaEditorVisitor visitor;
	private Composite viewArea;

	public ButtonGenerator(Composite viewArea, JavaEditorServices javaEditor) {
		this.javaEditor = javaEditor;
		this.visitor = new JavaEditorVisitor();
		this.generateCode = new GenerateCode(javaEditor, this.visitor);
		this.viewArea = viewArea;
	}

	public void addGettersSetters(String name) {
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
					
					createGettersSettersInterface(map);

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
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				generateCode.surroundWithTryCatch();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	
	public void generateConstructorUsingFields(String name) {
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
					
					createConstructorInterface(map);

				} else {
					System.out.println("There are no fields");
				}
			}


			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
	}
	/* -------------------------------------------------------------------------------------------------- */
	
	private void createGettersSettersInterface(Multimap<String,String> map) {
		ArrayList<String> selectedFields = new ArrayList<String>();

		Shell shell = new Shell();
		shell.setText("Add Getters and Setters");
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		shell.setSize(300, 500);

		Text title = new Text(shell, SWT.SINGLE);
		title.setBackground(shell.getBackground());
		title.setText("Choose attributes to add getters/setters:");

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
				
				generateCode.generateGettersSetters(selectedFields);
				shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		
		shell.setVisible(true);	
}

	
	private void createConstructorInterface(Multimap<String,String> map) {
		ArrayList<String> selectedFields = new ArrayList<String>();

		Shell shell = new Shell();
		shell.setText("Generate Constructor Using Fields");
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		shell.setSize(300, 500);

		Text title = new Text(shell, SWT.SINGLE);
		title.setBackground(shell.getBackground());
		title.setText("Choose the fields to add into the constructor:");

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
				
				generateCode.generateConstructor(selectedFields);
				shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});

		
		shell.setVisible(true);	
}
}
