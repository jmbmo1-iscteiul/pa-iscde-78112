package pt.iscde.codegenerator.ext;

import java.util.List;

import com.google.common.collect.Multimap;

import pt.iscde.codegenerator.services.CodeGeneratorServices;

public interface UserCode {
	Multimap<String,String> userCode();
	
	
	String generateCode(List<String> list, CodeGeneratorServices codeGenServices);
}
