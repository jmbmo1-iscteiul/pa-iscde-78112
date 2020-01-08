package pt.iscde.codegenerator.ext;

import pt.iscde.codegenerator.services.CodeGeneratorServices;

public interface UserCode {
	
	String generateCode(ClassInformation editor, CodeGeneratorServices codeGenServices);
}
