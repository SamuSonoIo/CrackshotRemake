package org.samu.crackshotRemake.managers

import GunManager
import de.tr7zw.changeme.nbtapi.NBT
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.commands.ShotGet
import org.samu.crackshotRemake.listener.EntityDamage
import org.samu.crackshotRemake.listener.InteractEvent
import org.samu.crackshotRemake.weapon.nbt.AssignNBT
import org.samu.crackshotRemake.weapon.nbt.CheckNBT
import org.samu.crackshotRemake.weapon.shooting.GunShooting
import org.samu.crackshotRemake.weapon.shooting.ammo.AmmoManager
import org.samu.crackshotRemake.weapon.shooting.bursts.FullAutomatic

class ClassLoader(val crackshotRemake: CrackshotRemake) {
    var fileManager: FileManager? = null
    var ammoManager: AmmoManager? = null
    var assignNbt: AssignNBT? = null
    var nbtCheck: CheckNBT? = null
    var gunShooting: GunShooting? = null
    var fullAutomatic: FullAutomatic? = null

    init {
        ClassLoader.crackshotRemake = crackshotRemake
        if (!NBT.preloadApi()) {
            crackshotRemake.logger.warning("NBT-API wasn't initialized properly. Plugin disabling.")
            crackshotRemake.pluginLoader.disablePlugin(crackshotRemake)
        } else {
            ConfigManager.setupConfig(crackshotRemake)
            fileManager = FileManager(crackshotRemake).apply {
                setupFiles()
                createWeapons()
            }
            ammoManager = AmmoManager(crackshotRemake)
            assignNbt = AssignNBT()
            nbtCheck = CheckNBT()
            gunShooting = GunShooting()
            gunManager = GunManager()
            fullAutomatic = FullAutomatic(crackshotRemake)

            crackshotRemake.getCommand("shot").executor = ShotGet()
            crackshotRemake.server.pluginManager.registerEvents(InteractEvent(), crackshotRemake)
            crackshotRemake.server.pluginManager.registerEvents(EntityDamage(), crackshotRemake)

            crackshotRemake.logger.warning("CLASSLOADER LOADED SUCCESSFULLY!")
        }
    }

    companion object {
        var gunManager: GunManager? = null
        var crackshotRemake: CrackshotRemake? = null
    }
}
