package org.samu.crackshotRemake.util

import org.samu.crackshotRemake.managers.ConfigManager.config

object ConfigUtils {
    fun getMessageError(path:String):String {
        return config!!.getString("Prefix") + config!!.getString("Messages.error.$path")
    }
    fun getMessageSuccess(path:String):String {
        return config!!.getString("Prefix") + config!!.getString("Messages.success.$path")
    }
}