package pt.iscde.codegenerator.services;

public interface CodeGeneratorServices {

	String gettersSetters();
	
	String surroundWithTryCatch();
	
	String generateConstructor();
	
	String generateToString();
}
