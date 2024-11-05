package org.samu.crackshotRemake

import GunManager
import de.tr7zw.changeme.nbtapi.NBT
import org.bukkit.plugin.java.JavaPlugin
import org.samu.crackshotRemake.commands.ShotGet
import org.samu.crackshotRemake.listener.EntityDamage
import org.samu.crackshotRemake.listener.InteractEvent
import org.samu.crackshotRemake.managers.CacheManager
import org.samu.crackshotRemake.managers.ConfigManager
import org.samu.crackshotRemake.managers.FileManager

class CrackshotRemake : JavaPlugin() {
    override fun onEnable() {
        if (!NBT.preloadApi()) {
            logger.warning("NBT-API wasn't initialized properly, disabling the plugin")
            pluginLoader.disablePlugin(this)
            return
        }
        ConfigManager.setupConfig(this)
        FileManager.setupFiles(this)
        FileManager.createWeapons()
        gunManager = GunManager(this)
        getCommand("shot").executor = ShotGet()
        server.pluginManager.registerEvents(InteractEvent(this), this)
        server.pluginManager.registerEvents(EntityDamage(this), this)

        for (weapon in CacheManager.getAllWeapons()) {
            logger.warning(weapon.name)
        }
    }

    companion object {
        var gunManager:GunManager ?= null
    }
}
