package pt.iscde.codegenerator;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class JavaEditorVisitor extends ASTVisitor{

	private ArrayList<FieldDeclaration> fields = new ArrayList<>();
	ArrayList<String> fieldNames = new ArrayList<String>();
	private ArrayList<MethodDeclaration> methods = new ArrayList<>();
	ArrayList<String> methodNames = new ArrayList<String>();
	
	@Override
	public boolean visit(FieldDeclaration field) {
		fields.add(field);
		return false;
	}
	
	@Override
	public boolean visit(MethodDeclaration method) {
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
		
		fields.clear();
		return fieldNames;
	}

	public ArrayList<String> getMethodNames() {
		methodNames.clear();
		
		for(MethodDeclaration m: methods) {
			methodNames.add(m.getName().toString());
		}
		
		methods.clear();
		return methodNames;
	}
}
