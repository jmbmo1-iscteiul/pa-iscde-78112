package pt.iscde.codegenerator.ext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class ClassInformation {
	
	private List<String> fields;
	private List<String> methods;
	
	private List<FieldDeclaration> fieldDeclarations;
	private List<MethodDeclaration> methodDeclarations;
	
	private Map<String,String> fieldNameType;
	
	private File file;
	
	public ClassInformation(List<FieldDeclaration> fieldDeclarations, List<MethodDeclaration> methodDeclarations, File file) {
		this.fieldDeclarations = fieldDeclarations;
		this.methodDeclarations = methodDeclarations;
		this.fields = new ArrayList<String>();
		this.methods = new ArrayList<String>();
		this.fieldNameType = new HashMap<String, String>();
		this.file = file;
		
		processFields();
	}
	
	public List<FieldDeclaration> getFieldDeclarations(){
		return fieldDeclarations;
	}
	
	public List<MethodDeclaration> getMethodDeclarations(){
		return methodDeclarations;	
	}

	//Devolve nomes dos fields
	public List<String> getFields(){
		processFields();
		return fields;
	}
	
	//Devolve nomes dos metodos
	public List<String> getMethods(){
		methods.clear();
		
		for(MethodDeclaration m: methodDeclarations) {

			methods.add(m.getName().toString());
		}
		
		return methods;
	}
	
	//devolve nome da classe
	public String getClassName() {
		return file.getName().replace(".java", "");
	}
	
	public Map<String, String> getfieldNameType(){
		processFields();
		return fieldNameType;
	}
 	
	
	private void processFields() {
		fields.clear();
		fieldNameType.clear();
		
		String[] type_name;
		String fieldCode, fieldName, fieldType;
		for(FieldDeclaration f: fieldDeclarations) {
			fieldCode = f.toString();
			type_name = fieldCode.split(" ");
			fieldType = type_name[0];
			fieldName = type_name[1].substring(0, type_name[1].length()-2);
			
			fields.add(fieldName);
			
			fieldNameType.put(fieldName, fieldType);
		}
	}
	
}
