package jp.co.weseek.winno

import grails.web.Action

import java.lang.reflect.Method

import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass

/**
 * @see http://stackoverflow.com/questions/2956294/reading-out-all-actions-in-a-grails-controller
 * 
 * @author Yuki Takei <yuki@weseek.co.jp>
 *
 */
class ControllerService {

	def grailsApplication
	
	
	/**
	 * get all ControllerName-ActionNames Map for controllers belonging to packages that starts with 'packageStartsWith' param
	 * 	key: controller name
	 * 	value: list of action names
	 * 
	 * @param packageStartsWith
	 * @return
	 */
	Map<String, List<String>> getAllControllerActionMap(String packageStartsWith = null) {
		if (packageStartsWith == null) {
			packageStartsWith = ''		// all controllers correspond
		}
		
		// keys are logical controller names, values are list of action names
		// that belong to that controller
		Map<String, List<String>> controllerActionNames = [:]
		
		grailsApplication.controllerClasses.each { DefaultGrailsControllerClass controller ->
		
			Class controllerClass = controller.clazz
		
			// skip controllers in plugins
			if (controllerClass.name.startsWith(packageStartsWith)) {
				String logicalControllerName = controller.logicalPropertyName
		
				// get the actions defined as methods (Grails 2)
				controllerClass.methods.each { Method method ->
		
					if (method.getAnnotation(Action)) {
						def actions = controllerActionNames[logicalControllerName] ?: []
						actions << method.name
		
						controllerActionNames[logicalControllerName] = actions
					}
				}
			}
		}
		
		return controllerActionNames
	}
}