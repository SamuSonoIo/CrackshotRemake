package org.samu.crackshotRemake.managers

import org.bukkit.configuration.file.FileConfiguration
import org.samu.crackshotRemake.CrackshotRemake

object ConfigManager {

    var config : FileConfiguration ?= null

    fun setupConfig(crackshotRemake: CrackshotRemake) {
        config = crackshotRemake.config
        config!!.options().copyDefaults(true)
        crackshotRemake.saveDefaultConfig()
    }

    fun getMessageError(path:String):String {
        return config!!.getString("Prefix") + config!!.getString("Messages.error.$path")
    }
    fun getMessageSuccess(path:String):String {
        return config!!.getString("Prefix") + config!!.getString("Messages.success.$path")
    }
}