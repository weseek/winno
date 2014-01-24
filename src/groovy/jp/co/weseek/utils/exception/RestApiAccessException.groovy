package jp.co.weseek.utils.exception

/**
 * REST APIアクセス時、エラーが発生した場合にスローされる例外
 * @author Syunsuke Komma<syunsuke@vvv6.jp>
 *
 */
class RestApiAccessException extends RuntimeException {
	
	int responseCode
	String message
	def response
	def body
	
	RestApiAccessException(def response, def body) {
		// APIエラーメッセージをそのまま渡す
		super("API access failed: ${body}")
		this.response = response
		this.body = body
		this.responseCode = response.status
	}
}
