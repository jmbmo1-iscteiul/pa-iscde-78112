package pt.iscde.codegenerator.services;

import java.util.List;
import java.util.Map;

public interface CodeGeneratorServices {

	String gettersSetters(Map<String,String> fields, String filePath);
	
	String surroundWithTryCatch(String filePath);
	
	String generateConstructor(Map<String,String> fields, String filePath);
	
	String generateToString(List<String> fields, String filePath);
}
