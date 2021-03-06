package pt.iscde.codegenerator.ext;

import java.util.Map;

import pt.iscde.codegenerator.extensibility.ClassInformation;
import pt.iscde.codegenerator.extensibility.UserCode;
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
		
		return code;
	}

}
