package pt.iscde.codegenerator;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class GenerateCode {

	private JavaEditorServices javaEditor;
	private File file;
	private JavaEditorVisitor visitor;

	public GenerateCode(JavaEditorServices javaEditor, JavaEditorVisitor visitor) {
		this.javaEditor = javaEditor;
		this.visitor = new JavaEditorVisitor();

	}


	public void generateGettersSetters(ArrayList<String> selectedFields) {
		visitor.clear();
		file = javaEditor.getOpenedFile();
		javaEditor.saveFile(file);
		javaEditor.parseFile(file, visitor);
		String fieldName, fieldType;

		if(!selectedFields.isEmpty()) {
			for(String s: selectedFields) {

				fieldType = s.split(" ")[0];
				fieldName = s.split(" ")[1];


				String getMethodCode = "", setMethodCode = "", code = "";
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

				if(!visitor.getMethodNames().contains(getMethodName)) {
					getMethodCode =  "public " + fieldType + " " + getMethodName + "() { \n \t\treturn this."+ fieldName + "; \n \t}\n\n";
				}

				if(!visitor.getMethodNames().contains(setMethodName)){
					setMethodCode = "\tpublic void " + setMethodName + "(" + fieldType + " " + fieldName +") { \n \t\tthis."+ fieldName + " = " + fieldName + "; \n \t}\n\n";
				}

				code = getMethodCode + setMethodCode;


				javaEditor.insertTextAtCursor(code);
				javaEditor.saveFile(file);
			}
		} else {
			
			System.out.println("Lista Vazia");
		}
	}

	public void generateConstructor(ArrayList<String> selectedFields) {
		visitor.clear();
		file = javaEditor.getOpenedFile();
		javaEditor.saveFile(file);
		javaEditor.parseFile(file, visitor);
		String fieldName, fieldType, top, bottom = "", code = "", className, aux = "";
		
		boolean exists = false;
		className = file.getName().replace(".java", "");
		top = selectedFields.toString().substring(1, selectedFields.toString().length()-1);
		
		if(!selectedFields.isEmpty()) {
			for(String s: selectedFields) {
				fieldType = s.split(" ")[0];
				fieldName = s.split(" ")[1];
				aux += fieldType;
				
				bottom += "this." +fieldName + " = " + fieldName + ";\n\t\t";
			}

			code = "public " + className + "(" + top + "){\n\t\t" + bottom + "}";

			ArrayList<String> constructorParameters = visitor.getConstructorParameters();
			
			for(String s: constructorParameters) {
				if(s.equals(aux)) {
					exists = true;
				}
			}

		} else {
			code = "public " + className + "(" + top + "){\n\t\t\n}";
		}
		
		if(!exists) {
			javaEditor.insertTextAtCursor(code);
			javaEditor.saveFile(file);
			
		} else {
			generateErrorMessage("A constructor with the same argument types already exists");
		}
	}

	public void surroundWithTryCatch(ITextSelection textSelected) {
		visitor.clear();
		file = javaEditor.getOpenedFile();
		javaEditor.saveFile(file);
		javaEditor.parseFile(file, visitor);
		
		String code = "try {\n\t\t" + textSelected.getText().toString() +"\n\t}catch (Exception e) {\n\t\te.printStackTrace();\n\t}";
		javaEditor.insertText(file, code, textSelected.getOffset(), textSelected.getLength());
	}

	public void generateToString(ArrayList<String> selectedFields) {
		visitor.clear();
		file = javaEditor.getOpenedFile();
		javaEditor.saveFile(file);
		javaEditor.parseFile(file, visitor);
		String fieldName, className, s1, s2 = "", s3, code, functionCode = "";
		boolean exists = false;
		
		className = file.getName().replace(".java", "");
		s1 = "\"" + className + " [";
		s3 = " + \"]\";";
		
		if(!selectedFields.isEmpty()) {
			for(int i = 0; i < selectedFields.size(); i++) {
				fieldName = selectedFields.get(i).split(" ")[1];
				s2 += fieldName + "=\" + " + fieldName;
				
				if(i != selectedFields.size() - 1)
					s2 += " + \", ";
				
			}
			
			code = s1 + s2 + s3;
			
			functionCode = "@Override\n" + "public String toString() {\n\t" + "return " + code + "\n}\n";
			
//			for(String method: visitor.getMethodNames()) {
//				if(method.equals("toString"))
//					exists = true;
//			}
//			if(!exists)
//				javaEditor.insertTextAtCursor(functionCode);
//				javaEditor.saveFile(file);

		} else {
			functionCode = "@Override\n" + "public String toString() {\n\t" + "return \"" + className + " []\";\n}\n";
		}
		
		for(String method: visitor.getMethodNames()) {
			if(method.equals("toString"))
				exists = true;
		}
		
		if(!exists) {
			javaEditor.insertTextAtCursor(functionCode);
			javaEditor.saveFile(file);
		} else {
			generateErrorMessage("A To String method already exists.");
		}

	}


	
	
	private void generateErrorMessage(String message) {
		Shell s = new Shell();
		MessageBox messageBox = new MessageBox(s, SWT.ICON_ERROR);
		messageBox.setText("Error Message");
		messageBox.setMessage(message);
		messageBox.open();
	}

}
