package pt.iscde.codegenerator.services;

import java.util.List;
import java.util.Map;

public interface CodeGeneratorServices {

	String gettersSetters(Map<String,String> fields);
	
	String getter(Map<String,String> fields);
	
	String setter(Map<String,String> fields);
	
	String surroundWithTryCatch();
	
	String generateConstructor(Map<String,String> fields);
	
	String generateToString(List<String> fields);
	
	String addMethod(String methodName, String returnType, String privacyModifier, Map<String,String> parameters);
}
