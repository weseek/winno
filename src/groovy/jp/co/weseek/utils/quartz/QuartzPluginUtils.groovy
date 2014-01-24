package jp.co.weseek.utils.quartz

import grails.plugins.quartz.GrailsJobClassConstants
import grails.plugins.quartz.TriggerUtils

import org.quartz.Trigger
import org.quartz.TriggerBuilder

class QuartzPluginUtils {
	
	/**
	 * トリガ名を指定した {@link Trigger} オブジェクトを生成する
	 * (～Job.schedule(Trigger)用のTriggerを生成する
	 * @param triggerName トリガ名
	 * @param scheduleDate ジョブを実行する日時
	 * @return {@link Trigger} オブジェクト
	 * @see TriggerUtils#buildDateTrigger(String, String, java.util.Date)
	 */
	static Trigger buildDateTriggerWithCustomName(String triggerName, Date scheduleDate) {
		assert triggerName
		assert scheduleDate != null
		
		// jobName/jobGroupはschedule()内で設定してもらえるため、ここでは指定不要
		return TriggerBuilder.newTrigger()
			.withIdentity(triggerName, GrailsJobClassConstants.DEFAULT_TRIGGERS_GROUP)
			.withPriority(6)
			.startAt(scheduleDate)
			.build()
	}
}
