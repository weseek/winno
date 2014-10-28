import grails.util.Metadata

def appName = Metadata.current.getApplicationName()

grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.dependency.resolver = "maven" // or ivy
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
		mavenLocal()
		mavenCentral()
		// uncomment the below to enable remote dependency resolution
		// from public Maven repositories
		//mavenRepo "http://snapshots.repository.codehaus.org"
		//mavenRepo "http://repository.codehaus.org"
		//mavenRepo "http://download.java.net/maven/2/"
		//mavenRepo "http://repository.jboss.com/maven2/"
	}
	dependencies {
		// http://jira.grails.org/browse/GRAILS-9990
		// があるため、excludesしておく
		// TODO: 0.7.2にupdateするためには、既存コードの修正が必要
		// MbgaApiServiceIntegrationSpecがうまくいかなくなる
		compile('org.codehaus.groovy.modules.http-builder:http-builder:0.6') { excludes 'groovy' }
	}

	plugins {
		build(":release:3.0.1", ":rest-client-builder:1.0.3") { export = false }

		// plugins for the build system only
		build ':tomcat:7.0.54'

		compile ":quartz:1.0.1"

		test ":code-coverage:1.2.7"
		compile ":codenarc:0.21"
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
