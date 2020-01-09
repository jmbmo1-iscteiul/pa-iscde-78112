package pt.iscde.codegenerator.services;

import java.util.List;
import java.util.Map;

public interface CodeGeneratorServices {
	
	/**
	 * Generates both Getters and Setters for the fields given as parameter
	 * 
	 * @param fields A map containing the association between the field name and type:
	 * <br>
	 * <i>Map Keys:</i> field name
	 * <br>
	 * <i>Map Values:</i> field type (int, boolean, List<String>, ...)
	 * 
	 * @return A string with both getter and setter methods code, or an empty String (= "") if all of the given fields already have both get and set methods implemented
	 */
	String gettersSetters(Map<String,String> fields);
	
	/**
	 * Generates Getters for the fields given as parameter
	 * 
	 * @param fields A map containing the association between the field name and type:
	 * <br>
	 * <i>Map Keys:</i> field name
	 * <br>
	 * <i>Map Values:</i> field type (int, boolean, List<String>, ...)
	 * 
	 * @return A string with the getter method code, or an empty String (= "") if all of the given fields already have a get method implemented
	 */
	String getter(Map<String,String> fields);
	
	/**
	 * Generates Setters for the fields given as parameter
	 * 
	 * @param fields A map containing the association between the field name and type:
	 * <br>
	 * <i>Map Keys:</i> field name
	 * <br>
	 * <i>Map Values:</i> field type (int, boolean, List<String>, ...)
	 * 
	 * @return A string with the setter method code, or an empty String (= "") if all of the given fields already have a set method implemented
	 */
	String setter(Map<String,String> fields);
	
	/**
	 * Surrounds the piece of code which is selected in the JavaTextEditor with a Try/Catch
	 * 
	 * @return A String with the code selected already surrounded by the Try/Catch
	 */
	String surroundWithTryCatch();
	
	/**
	 * Generates the code to the constructor method with the fields given as parameter
	 * 
	 * @param fields A map containing the association between the field name and type:
	 * <br>
	 * <i>Map Keys:</i> field name
	 * <br>
	 * <i>Map Values:</i> field type (int, boolean, List<String>, ...)
	 * 
	 * @return A String with the constructor code, or an empty String (= "") if it already exists
	 */
	String generateConstructor(Map<String,String> fields);
	
	/**
	 * Generates the code for the toString method
	 *
	 * @param fields list of field names (non-null) to use in the toString method
	 * @return A String with the toString method code, or an empty String (= "") if it already exists
	 */
	String generateToString(List<String> fields);
	
	/**
	 * Generates a method code structure
	 * 
	 * @param methodName The name of the method 
	 * @param returnType The return type of the method (void, int, String, ...)
	 * @param privacyModifier The modifier which concerns the method privacy (private, protected, ...)
	 * @param parameters A map containing the association between the parameter name and type:
	 * <br>
	 * <i>Map Keys:</i> parameter name
	 * <br>
	 * <i>Map Values:</i> parameter type (int, boolean, List<String>, ...)
	 * 
	 * @return A String with the generated method code,  or an empty String (= "") if it already exists
	 */
	String addMethod(String methodName, String returnType, String privacyModifier, Map<String,String> parameters);
}
