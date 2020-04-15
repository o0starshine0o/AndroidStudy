package com.chen

import java.lang.ref.Reference
import java.util.*

/**
 * Created by chenming on 2020-04-10
 */
class NotifyManager {

    private val notifyMap = HashMap<NotifyType, List<Reference<ECallback<Any>>>>()

    fun <T : Any> notify(type: NotifyType, data: T) {
        val callbacks = notifyMap[type]
        for (cf in callbacks!!) {
            try {
                val c = cf.get()
                c?.callback(data)
            } catch (t: Throwable) {
            }

        }
    }
}
