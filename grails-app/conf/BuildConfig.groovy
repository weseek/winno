import grails.util.Metadata

def appName = Metadata.current.getApplicationName()

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
		// uncomment to disable ehcache
		// excludes 'ehcache'
	}
	log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility
	repositories {
		grailsCentral()
		mavenCentral()
		// uncomment the below to enable remote dependency resolution
		// from public Maven repositories
		//mavenLocal()
		//mavenRepo "http://snapshots.repository.codehaus.org"
		//mavenRepo "http://repository.codehaus.org"
		//mavenRepo "http://download.java.net/maven/2/"
		//mavenRepo "http://repository.jboss.com/maven2/"
	}
	dependencies {
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
		test "org.spockframework:spock-grails-support:0.7-groovy-2.0"

		// runtime 'mysql:mysql-connector-java:5.1.21'
	}

	plugins {
		build(":tomcat:$grailsVersion",
				":release:2.2.1",
				":rest-client-builder:1.0.3") {
					export = false
		}
		
		compile ":eclipse-scripts:1.0.7"
		test(":spock:0.7") {
			exclude "spock-grails-support"
		}
		test ":code-coverage:1.2.7"
		compile ":codenarc:0.20"
	}
}

// CodeNarc
codenarc.ruleSetFiles = "file:grails-app/conf/CodeNarcRuleSet.groovy"
codenarc.extraIncludeDirs = [
]
codenarc.processTestUnit = false
codenarc.processTestIntegration  = false
codenarc.reports = {
	// Each report definition is of the form:
	//    REPORT-NAME(REPORT-TYPE) {
	//        PROPERTY-NAME = PROPERTY-VALUE
	//        PROPERTY-NAME = PROPERTY-VALUE
	//    }

	MyXmlReport('xml') {
		// The report name "MyXmlReport" is user-defined; Report type is 'xml'
		outputFile = 'target/CodeNarc-Report.xml'	// Set the 'outputFile' property of the (XML) Report
		title = "Condor CodeNarc Report"			// Set the 'title' property of the (XML) Report
	}
	MyHtmlReport('html') {
		// Report type is 'html'
		outputFile = 'target/CodeNarc-Report.html'
		title = "Condor CodeNarc Report"
	}
}

// Covertura
coverage {
	xml = true
	nopost = true
	sourceInclusions = [
	]
	exclusions = [
		'ApplicationResources*',
		'CodeNarcRuleSet*',
		'gsp_*',
		'**/*Exception*',
	]
}
