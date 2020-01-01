package pt.iscde.codegenerator;

import java.io.File;
import java.util.ArrayList;

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
		file = javaEditor.getOpenedFile();
		javaEditor.parseFile(file, visitor);
		String fieldName;
		String fieldType;
		String getCode;
		String setCode;


		if(!selectedFields.isEmpty()) {
			for(String s: selectedFields) {
				fieldName = s.split(" ")[1];
				fieldType = s.split(" ")[0];

				getCode = getGetterCode(fieldName, fieldType);
//				setCode = "\tpublic void set" + fieldName + "() { \n \t\tthis."+ fieldName + " = " + fieldName + "; \n \t}";

				javaEditor.insertTextAtCursor(getCode);
				javaEditor.saveFile(file);
			}
		} else {
			System.out.println("Lista Vazia");
		}

	}
	
	private String getGetterCode(String fieldName, String fieldType) {
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		if(!visitor.getMethodNames().contains(methodName))
			return "\tpublic " + fieldType + " " + methodName + "() { \n \t\treturn this."+ fieldName + "; \n \t}";
		return "";
	}
}
