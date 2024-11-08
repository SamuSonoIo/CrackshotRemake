package org.samu.crackshotRemake

import org.bukkit.plugin.java.JavaPlugin
import org.samu.crackshotRemake.managers.ClassLoader

class CrackshotRemake : JavaPlugin() {
    override fun onEnable() {
        CrackshotRemake.classLoader = ClassLoader(this)
    }

    companion object {
        var classLoader: ClassLoader? = null
    }
}
