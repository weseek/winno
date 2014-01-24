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

        // runtime 'mysql:mysql-connector-java:5.1.21'
    }

    plugins {
        build(":tomcat:$grailsVersion",
              ":release:2.2.1",
              ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}

// loading external Maven repository configuration
// code based on https://gist.github.com/beckje01/3894523
//println "${userHome}/.grails/${appName}-mavenInfo.groovy"
def mavenConfigFile = new File("${userHome}/.grails/${appName}-mavenInfo.groovy")
if (mavenConfigFile.exists()) {
	def slurpedMavenInfo = new ConfigSlurper().parse(mavenConfigFile.toURL())
	slurpedMavenInfo.grails.project.repos.each {k, v ->
		println "Adding maven info for repo $k"
		grails.project.repos."$k" = v
	}
}
else {
	println "No mavenInfo file found."
}