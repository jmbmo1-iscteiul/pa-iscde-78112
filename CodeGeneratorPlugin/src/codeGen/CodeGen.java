package codeGen;

import java.util.List;
import java.util.Map;

import pt.iscde.codegenerator.ext.ClassInformation;
import pt.iscde.codegenerator.ext.UserCode;
import pt.iscde.codegenerator.services.CodeGeneratorServices;


public class CodeGen implements UserCode{

	@Override
	public String generateCode(ClassInformation editor, CodeGeneratorServices codeGenServices) {
		String className = editor.getClassName();
		Map<String,String> map = editor.getfieldNameType();
		String code = "@Override\npublic boolean equals(Object obj) {\n\tif (this == obj)\n\t\t return true;\n\tif (obj == null)\n\t\t return false;"
				+ "\n\tif (getClass() != obj.getClass())\n\t\treturn false;\n\t"
				+ className + " other = (" + className + ") obj;\n\t";
		
		for(String field: map.keySet())
			if (map.get(field).equals("int"))
				code += "if (" + field + " != other." + field + ")\n\t\treturn false;\n\t";
		
		code += "return true;\n}";
		
//		return code;
		
		List<String> teste = editor.getFields();
//		teste.clear();
		return codeGenServices.addMethod("nomeFuncao", "String", "protected", editor.getfieldNameType());
	}

}
