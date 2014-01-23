// configuration for plugin testing - will not be included in the plugin zip
grails.project.groupId = "jp.co.weseek.winno"

log4j = {
	// Example of changing the log pattern for the default console
	// appender:
	//
	appenders {
		console name: 'stdout', layout: new org.apache.log4j.EnhancedPatternLayout(conversionPattern: '%d{HH:mm:ss,SSS} [%t] %-5p %-34c{3~.5~.1} - %m%n%throwable{short}')
	}

	error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
			'org.codehaus.groovy.grails.web.pages', //  GSP
			'org.codehaus.groovy.grails.web.sitemesh', //  layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping', // URL mapping
			'org.codehaus.groovy.grails.commons', // core / classloading
			'org.codehaus.groovy.grails.plugins', // plugins
			'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate'

	warn   'org.mortbay.log'

	debug 'jp.co.weseek', 'grails'
}
