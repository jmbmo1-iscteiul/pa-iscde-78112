package pt.iscde.codegenerator;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.google.common.collect.Multimap;

public class WindowBuilder {

	private final static int SHELL_WIDTH = 350;

	private Multimap<String, String> map;
	ArrayList<String> selectedFields;


	public WindowBuilder() {
			this.selectedFields = new ArrayList<String>();
	}

	public void addGettersSetters(Composite viewArea, Multimap<String, String> map, GenerateCode generateCode) {
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
	
	
	public ArrayList<String> getSelectedFields(){
		return this.selectedFields;
	}
}
