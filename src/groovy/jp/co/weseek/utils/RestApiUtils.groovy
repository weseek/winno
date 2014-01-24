package jp.co.weseek.utils

import jp.co.weseek.utils.exception.RestApiAccessException

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class RestApiUtils {
	
	private static final Log LOGGER = LogFactory.getLog(RestApiUtils)
	
	/**
	 * REST APIを叩く
	 * @param endpointUrl エンドポイントURL
	 * @param method GET/POST
	 * @param queryParams リクエストに付加するクエリ
	 * @param bodyObj リクエストのボディに投入するオブジェクト
	 * @return 各種API実行時のJSONの結果がそのまま返される
	 * @throws RestApiAccessException REST API実行に失敗した場合にスローされる
	 */
	static jsonApiAccess(String endpointUrl, Method method, 
			Map<String, String> queryParams = [:], Object bodyObj = null)
			throws RestApiAccessException {
		
		LOGGER.debug("jsonAccessApi() is called, endpointUrl=${endpointUrl}")
		LOGGER.debug("method=${method}, queryParams=${queryParams}, body=${bodyObj}")
		assert endpointUrl
		assert method
		assert queryParams != null
		
		def result
		def client = new HTTPBuilder(endpointUrl)
		// ContentType.JSONを指定すると、リクエスト失敗時のレスポンスも勝手にJSONパースしようとするので、
		// テキストでやり取りさせ、リクエストが成功したときのみ自前でJSONパースするようにした
		client.request(method, ContentType.TEXT) {
			uri.query = queryParams
			requestContentType = ContentType.JSON
			if (bodyObj != null) {
				body = bodyObj
			}
			
			response.success = { resp, data ->
				log.debug("response(${resp.class}) status: ${resp.statusLine}")
				result = new JsonSlurper().parseText(data.text)
			}
			response.failure = { resp, data ->
				log.debug("API request failed: ${resp.dump()} (${resp.class})")
				throw new RestApiAccessException(resp, data.text)
			}
		}
		
		return result
	}
}
