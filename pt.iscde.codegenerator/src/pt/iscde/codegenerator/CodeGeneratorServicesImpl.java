package pt.iscde.codegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.text.ITextSelection;

import pt.iscde.codegenerator.services.CodeGeneratorServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class CodeGeneratorServicesImpl implements CodeGeneratorServices {

	@Override
	public String gettersSetters(Map<String,String> fields, String filePath) {
		JavaEditorServices javaEditor = Activator.getActivator().getJavaEditor();
		JavaEditorVisitor visitor = new JavaEditorVisitor();
		visitor.clear();
		File file = javaEditor.getOpenedFile();
		javaEditor.parseFile(file, visitor);
		
		String fieldName, fieldType, getMethodName, setMethodName;
		String getMethodCode = "", setMethodCode = "", code = "";
		
		if(!fields.isEmpty()) {
			for(String s: fields.keySet()) {

				fieldType = fields.get(s);
				fieldName = s;


				getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

				if(!visitor.getMethodNames().contains(getMethodName)) {
					getMethodCode +=  "public " + fieldType + " " + getMethodName + "() { \n \t\treturn this."+ fieldName + "; \n \t}\n\n";
				}

				if(!visitor.getMethodNames().contains(setMethodName)){
					setMethodCode += "\tpublic void " + setMethodName + "(" + fieldType + " " + fieldName +") { \n \t\tthis."+ fieldName + " = " + fieldName + "; \n \t}\n\n";
				}


			}
			
			code = getMethodCode + setMethodCode;
			return code;
		} else {
			System.out.println("Lista Vazia");
		}

		return "";
	}

	@Override
	public String surroundWithTryCatch(String filePath) {
		JavaEditorServices javaEditor = Activator.getActivator().getJavaEditor();
		File file = javaEditor.getOpenedFile();
		
		ITextSelection textSelected = javaEditor.getTextSelected(file);
		
		String code = "try {\n\t\t" + textSelected.getText().toString() +"\n\t}catch (Exception e) {\n\t\te.printStackTrace();\n\t}";
		
		return code;
	}

	@Override
	public String generateConstructor(Map<String,String> fields, String filePath) {
		JavaEditorServices javaEditor = Activator.getActivator().getJavaEditor();
		JavaEditorVisitor visitor = new JavaEditorVisitor();
		visitor.clear();
		File file = javaEditor.getOpenedFile();
		javaEditor.parseFile(file, visitor);
		
		String top = "", bottom = "", code, className, aux = "";
		boolean exists = false;
		
		className = file.getName().replace(".java", "");
		
		if(!fields.isEmpty()) {
			for(String s: fields.keySet()) {
				top += fields.get(s) + " " + s + ", ";
				bottom += "this." + s + " = " + s + ";\n\t\t";
			}
			top = top.substring(0, top.length()-3);
			code = "public " + className + "(" + top + "){\n\t\t" + bottom + "}";

			ArrayList<String> constructorParameters = visitor.getConstructorParameters();
			
			for(String s: constructorParameters) {
				if(s.equals(aux)) {
					exists = true;
				}
			}
			
			if(!exists)
				return code;

		} else {
			System.out.println("Lista Vazia");
		}
		
		// FALTA RETORNAR CONSTRUTOR VAZIO
		return "";
	}

	@Override
	public String generateToString(List<String> fields, String filePath) {
		JavaEditorServices javaEditor = Activator.getActivator().getJavaEditor();
		JavaEditorVisitor visitor = new JavaEditorVisitor();
		visitor.clear();
		File file = javaEditor.getOpenedFile();
		javaEditor.parseFile(file, visitor);

		String fieldName, className, s1, s2 = "", s3, code, functionCode;
		boolean exists = false;

		className = file.getName().replace(".java", "");
		s1 = "\"" + className + " [";
		s3 = " + \"]\";";

		for(String method: visitor.getMethodNames()) {
			if(method.equals("toString"))
				exists = true;
		}
		if(!exists) {
			if(!fields.isEmpty()) {
				for(int i = 0; i < fields.size(); i++) {
					fieldName = fields.get(i).split(" ")[1];
					s2 += fieldName + "=\" + " + fieldName;

					if(i != fields.size() - 1)
						s2 += " + \", ";

				}

				code = s1 + s2 + s3;

				functionCode = "@Override\n" + "public String toString() {\n\t" + "return " + code + "\n}\n";

				return functionCode;
			} else {
				System.out.println("Lista Vazia");
			}
			
			return "@Override\n" + "public String toString() {\n\t" + "return \"" + className + " []\";\n}\n";
		} else {
			return "";
		}
	}

}
