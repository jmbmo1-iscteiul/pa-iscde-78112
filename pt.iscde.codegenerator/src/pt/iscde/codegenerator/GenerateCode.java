package pt.iscde.codegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.widgets.Composite;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
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
		javaEditor.parseFile(file, visitor);
		String fieldName, fieldType, top, bottom = "", code, className, aux = "";
		
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
				System.out.println("parameters: " + s);
				System.out.println("aux: " + aux);
				if(s.equals(aux)) {
					exists = true;
				}
			}
			

			if(!exists)
				javaEditor.insertTextAtCursor(code);
				javaEditor.saveFile(file);

		} else {
			System.out.println("Lista Vazia");
		}

	}



	public void surroundWithTryCatch(ITextSelection textSelected) {
		String code = "try {\n\t\t" + textSelected.getText().toString() +"\n\t}catch (Exception e) {\n\t\te.printStackTrace();\n\t}";
		javaEditor.insertText(file, code, textSelected.getOffset(), textSelected.getLength());
	}


}
