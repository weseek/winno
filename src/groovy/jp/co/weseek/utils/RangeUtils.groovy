package jp.co.weseek.utils

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

class RangeUtils {

	private final static Log LOGGER = LogFactory.getLog(RangeUtils)

	/**
	 * 数値の指定範囲をパースして、 {@link Long} or {@link ObjectRange} のリストを返す
	 * (0以上の数値指定のみに対応)
	 * ex.) 引数が 1-,-10,12-15,100,110-101,200-200 の場合、以下のリストが返る
	 *   [
	 *   	ObjectRange(1L, Long.MAX_VALUE),
	 *   	ObjectRange(Long.MIN_VALUE, 10L),
	 *   	ObjectRange(12L, 15L),
	 *   	100L,
	 *   	ObjectRange(101L, 110L),
	 *   	200L
	 *   ]
	 * @param range 数値の範囲指定が記述された文字列
	 * @throws IllegalArgumentException 数字以外の文字列が指定されていた場合
	 * @return {@link Long} or {@link ObjectRange} のリスト
	 */
	static List<Object> parseLongNumericRange(String range) {
		assert range != null

		def result = []

		def ranges = range.split(",")
		ranges*.trim().each {
			def matcher = it.trim() =~ /^(\d+)?(\s*-\s*(\d+)?)?$/
			if (matcher) {
				LOGGER.debug("match: ${it} / ${matcher[0]}")
				def from = matcher[0][1] ? matcher[0][1].toLong() : Long.MIN_VALUE
				def to = matcher[0][3] ? matcher[0][3].toLong() : (matcher[0][2] ? Long.MAX_VALUE : null)
				if (to) {
					if (from < to) {
						result << new ObjectRange(from, to)
					}
					// ハイフンの左右が同じ値だったら、単なる値にする
					else if (from == to) {
						result << from
					}
					// ハイフンの左側の値が右側の値より大きければ、大小を入れ替えてRangeを作る
					else {
						result << new ObjectRange(to, from)
					}
				} else {
					result << from
				}
			} else {
				throw new IllegalArgumentException("${it} is not a number.")
			}
		}

		return result
	}

	/**
	 * 数値の指定範囲をパースし、その中の最小値と最大値を返す
	 * (0以上の数値指定のみに対応)
	 * ex.) 引数が 1-10,100,110-101,200- の場合、以下のリストが返る
	 *   [1, Long.MAX_VALUE]
	 *
	 * @param range 数値の範囲指定が記述された文字列
	 * @throws IllegalArgumentException 数字以外の文字列が指定されていた場合
	 * @return {@link ObjectRange}
	 */
	static ObjectRange getMinMaxOfLongNumericRange(String range) {
		assert range != null

		long from = Long.MAX_VALUE
		long to = Long.MIN_VALUE

		// parse
		List rangeObjs = RangeUtils.parseLongNumericRange(range)

		rangeObjs.each {
			if (it instanceof Long) {
				from = Math.min(from, it)
				to = Math.max(to, it)
			}
			else if (it instanceof ObjectRange) {
				from = Math.min(from, (it as ObjectRange).from)
				to = Math.max(to, (it as ObjectRange).to)
			}
		}

		return new ObjectRange(from, to)
	}

	/**
	 * 数値の指定範囲をパースし、その中の最小値と最大値を返す
	 * (0以上の数値指定のみに対応)
	 * ex.) 引数が ['1-10,100','110-101,200-'] の場合、以下のリストが返る
	 *   [1, Long.MAX_VALUE]
	 *
	 * @param range 数値の範囲指定が記述された文字列
	 * @throws IllegalArgumentException 数字以外の文字列が指定されていた場合
	 * @return {@link ObjectRange}
	 */
	static ObjectRange getMinMaxOfLongNumericRange(Collection<String> ranges) {
		assert ranges != null
		assert ranges.size() > 0

		long from = Long.MAX_VALUE
		long to = Long.MIN_VALUE

		ranges.each { String range ->
			ObjectRange minAndMax = getMinMaxOfLongNumericRange(range)

			from = Math.min(from, minAndMax.from)
			to = Math.max(to, minAndMax.to)
		}

		return new ObjectRange(from, to)
	}
}
