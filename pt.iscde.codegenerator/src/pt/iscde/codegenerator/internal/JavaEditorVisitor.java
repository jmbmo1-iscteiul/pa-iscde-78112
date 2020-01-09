package pt.iscde.codegenerator.internal;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class JavaEditorVisitor extends ASTVisitor{

	private ArrayList<FieldDeclaration> fields = new ArrayList<>();
	private ArrayList<String> fieldNames = new ArrayList<String>();
	private ArrayList<MethodDeclaration> methods = new ArrayList<>();
	private ArrayList<String> methodNames = new ArrayList<String>();
	private ArrayList<String> constructorParameters = new ArrayList<String>();
	
	@Override
	public boolean visit(FieldDeclaration field) {
		if(!getFieldNames().contains(field.toString()))
			fields.add(field);
		return false;
	}
	
	@Override
	public boolean visit(MethodDeclaration method) {
		if(method.isConstructor() || !getMethodNames().contains(method.getName().toString()))
			methods.add(method);
		return false;
	}
	
	public ArrayList<FieldDeclaration> getFields(){
		return fields;
	}
	
	public ArrayList<MethodDeclaration> getMethods(){
		return methods;
	}
	
	public ArrayList<String> getFieldNames() {
		fieldNames.clear();
		
		for(FieldDeclaration f: fields) {
			fieldNames.add(f.toString());
		}
		
		return fieldNames;
	}

	public ArrayList<String> getMethodNames() {
		methodNames.clear();
		
		for(MethodDeclaration m: methods) {
			methodNames.add(m.getName().toString());
		}
		
		return methodNames;
	}
	
	public ArrayList<String> getConstructorParameters() {
		constructorParameters.clear();
		String s = "";
		
		for(MethodDeclaration m: methods) {
			if(m.isConstructor()) {
				for(int i = 0; i < m.parameters().size(); i++) {
					s += m.parameters().get(i).toString().split(" ")[0].toString();
				}
				constructorParameters.add(s);
				s = "";
			}
		}
		
		return constructorParameters;
	}
	
	public void clear() {
		fields.clear();
		methods.clear();
	}


}
