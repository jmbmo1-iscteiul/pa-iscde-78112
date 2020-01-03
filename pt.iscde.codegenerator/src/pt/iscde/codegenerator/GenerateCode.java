package pt.iscde.codegenerator;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.widgets.Composite;

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
		String fieldName, fieldType, code;
		
		if(!selectedFields.isEmpty()) {
			for(String s: selectedFields) {
				
				fieldType = s.split(" ")[0];
				fieldName = s.split(" ")[1];
				
				code = getterSettersCode(visitor.getMethodNames(), fieldName, fieldType);

				javaEditor.insertTextAtCursor(code);
				javaEditor.saveFile(file);
			}
		} else {
			System.out.println("Lista Vazia");
		}
	}
	
	
	private String getterSettersCode(ArrayList<String> methodNames, String fieldName, String fieldType) {
		String getMethodCode = "", setMethodCode = "";
		String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		if(!methodNames.contains(getMethodName)) {
			getMethodCode =  "public " + fieldType + " " + getMethodName + "() { \n \t\treturn this."+ fieldName + "; \n \t}\n\n";
		}
		
		if(!methodNames.contains(setMethodName)){
			setMethodCode = "\tpublic void " + setMethodName + "(" + fieldType + " " + fieldName +") { \n \t\tthis."+ fieldName + " = " + fieldName + "; \n \t}\n\n";
		}
		
		return getMethodCode + setMethodCode;
	}


	public void surroundWithTryCatch() {
		
		File file = javaEditor.getOpenedFile();
		ITextSelection textSelected = javaEditor.getTextSelected(file);
		
		String code = "try {\n\t\t" + textSelected.getText().toString() +"\n\t}catch (Exception e) {\n\t\te.printStackTrace();\n\t}";
		
		javaEditor.insertText(file, code, textSelected.getOffset(), textSelected.getLength());

	}


	public void generateConstructor(ArrayList<String> selectedFields) {
		// TODO Auto-generated method stub
		
	}
}
