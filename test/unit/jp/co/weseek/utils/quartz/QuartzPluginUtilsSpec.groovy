package jp.co.weseek.utils.quartz
import grails.plugins.quartz.GrailsJobClassConstants

import org.codehaus.groovy.runtime.powerassert.PowerAssertionError

import spock.lang.Specification
import spock.lang.Unroll

class QuartzPluginUtilsSpec extends Specification {

	def "test buildDateTriggerWithCustomName"() {
		setup: "prepare Date mock"
		Date dateMock = Mock()

		when:
		def result = QuartzPluginUtils.buildDateTriggerWithCustomName("hogehoge", dateMock)

		then:
		result.key.name == "hogehoge"
		result.key.group == GrailsJobClassConstants.DEFAULT_TRIGGERS_GROUP
		result.priority == 6
		result.startTime == dateMock
	}

	@Unroll("test buildDateTriggerWithCustomName when assertion fails and triggerName is #triggerName, scheduleDate is #scheduleDate")
	def "test buildDateTriggerWithCustomName when assertion fails"() {
		when:
		QuartzPluginUtils.buildDateTriggerWithCustomName(triggerName, scheduleDate)

		then:
		thrown PowerAssertionError

		where:
		triggerName		|	scheduleDate
		null			|	null
		""				|	null
		"hogehoge"		|	null
	}
}
