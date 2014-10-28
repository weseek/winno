package jp.co.weseek.utils

import groovyx.net.http.Method

import org.codehaus.groovy.runtime.powerassert.PowerAssertionError

import spock.lang.Specification
import spock.lang.Unroll

class RestApiUtilsSpec extends Specification {

	// 本体のテストは、インテグレーションテストで実施する
	@Unroll
	def "test jsonApiAccess when assertion fails"() {
		when:
		RestApiUtils.jsonApiAccess(endpointUrl, method, queryParams)

		then:
		thrown PowerAssertionError

		where:
		endpointUrl	|	method		|	queryParams
		null		|	null		|	null
		"hoge"		|	null		|	null
		"hoge"		|	Method.GET	|	null
	}
}
