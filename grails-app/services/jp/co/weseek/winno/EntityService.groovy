package jp.co.weseek.winno

import org.grails.datastore.gorm.events.AutoTimestampEventListener

/**
 * Service for entities
 *
 * @author Yuki Takei <yuki@weseek.co.jp>
 *
 */
class EntityService {

	/**
	 * DI
	 */
	def grailsApplication

	/**
	 * enable or disable 'autoTimestamp' of specified entity
	 * @param entityClass
	 * @param enabled
	 */
	void toggleAutoTimestamp(Class entityClass, boolean enabled) {
		// scan all application listeners and find the AutoTimestampEventListener instance
		AutoTimestampEventListener autoTseListener
		for (def listener in grailsApplication.mainContext.getApplicationListeners()) {
			if (listener instanceof AutoTimestampEventListener) {
				autoTseListener = listener
				break
			}
		}

		// get PersistentEntity
		def entity = autoTseListener.datastore.getMappingContext().getPersistentEntity(entityClass.name)

		// change
		autoTseListener.entitiesWithLastUpdated.put(entity, enabled)
	}

}
