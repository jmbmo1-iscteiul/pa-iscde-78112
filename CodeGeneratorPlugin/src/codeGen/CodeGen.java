package codeGen;

import com.google.common.collect.Multimap;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscde.codegenerator.ext.UserCode;
import pt.iscde.codegenerator.services.CodeGeneratorServices;


public class CodeGen implements UserCode{

	@Override
	public Multimap<String, String> userCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateCode(List<String> list, CodeGeneratorServices codeGenServices) {
//		BundleContext context = Activator.getContext();
//
//		ServiceReference<CodeGeneratorServices> codeGenRef = context.getServiceReference(CodeGeneratorServices.class);
//
//		CodeGeneratorServices codeGenServices = context.getService(codeGenRef);
		
		return codeGenServices.gettersSetters();
	}

}
