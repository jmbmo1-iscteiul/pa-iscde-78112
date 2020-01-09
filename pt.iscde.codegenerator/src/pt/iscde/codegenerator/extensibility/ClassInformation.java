package pt.iscde.codegenerator.extensibility;

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
	
	/**
	 * Gets a list with the FieldDeclarations present in the current JavaTextEditor file
	 * 
	 * @return A list of FieldDeclaration objects
	 */
	public List<FieldDeclaration> getFieldDeclarations(){
		return fieldDeclarations;
	}
	
	/**
	 * Gets a list with the MethodDeclarations present in the current JavaTextEditor file
	 * 
	 * @return A list of MethodDeclaration objects
	 */
	public List<MethodDeclaration> getMethodDeclarations(){
		return methodDeclarations;	
	}

	/**
	 * Gets a list with the name of the fields present in the current JavaTextEditor file
	 * 
	 * @return A list of String with the name of the fields
	 */
	public List<String> getFields(){
		processFields();
		return fields;
	}
	
	/**
	 * Gets a list with the name of the methods present in the current JavaTextEditor file
	 * 
	 * @return A list of String with the name of the methods
	 */
	public List<String> getMethods(){
		methods.clear();
		
		for(MethodDeclaration m: methodDeclarations) {

			methods.add(m.getName().toString());
		}
		
		return methods;
	}
	
	/**
	 * Gets the name of the class present in the JavaTextEditor
	 * @return A String with the class name
	 */
	public String getClassName() {
		return file.getName().replace(".java", "");
	}
	
	/**
	 * Gets a map with the name and type association of the fields present in the current JavaTextEditor file
	 * 
	 * @return A map containing the association between the field name and type:
	 * <br>
	 * <i>Map Keys:</i> field name
	 * <br>
	 * <i>Map Values:</i> field type (int, boolean, List<String>, ...)
	 */
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
