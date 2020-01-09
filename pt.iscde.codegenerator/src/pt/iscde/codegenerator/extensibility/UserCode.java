package pt.iscde.codegenerator.extensibility;

import pt.iscde.codegenerator.services.CodeGeneratorServices;

public interface UserCode {
	
	/**
	 * Generates the code to be inserted in the JavaTextEditor upon pressing the respective button
	 * 
	 * @param editor ClassInformation object which contains methods that support the generation of new code
	 * @param codeGenServices CodeGeneratorServices object that provides the functionalities of all the buttons present in the CodeGenerator interface and extras
	 * 
	 * @return A String with the code generated
	 */
	String generateCode(ClassInformation editor, CodeGeneratorServices codeGenServices);
}
