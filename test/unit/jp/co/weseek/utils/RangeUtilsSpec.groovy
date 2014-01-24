package jp.co.weseek.utils

import grails.plugin.spock.UnitSpec

import org.codehaus.groovy.runtime.powerassert.PowerAssertionError

class RangeUtilsSpec extends UnitSpec {

	def "test parseLongNumericRange"() {
		when:
		def result = RangeUtils.parseLongNumericRange("1-, -100, 12 -  15,  100, 110 - 101  , 200-200")
		
		then:
		result == [
			new ObjectRange(1L, Long.MAX_VALUE),
			new ObjectRange(Long.MIN_VALUE, 100),
			new ObjectRange(12L, 15L),
			100L,
			new ObjectRange(101L, 110L),
			200L,
		]
	}
	
	def "test parseLongNumericRange when the argument contains non-number string"() {
		when:
		RangeUtils.parseLongNumericRange("1-, -100, 12 -  15,  10hhhh0")
		
		then:
		def ex = thrown IllegalArgumentException
		println ex.message
	}
	
	def "test parseLongNumericRange when assertion fails"() {
		when:
		RangeUtils.parseLongNumericRange(null)
		
		then:
		thrown PowerAssertionError
	}
}
