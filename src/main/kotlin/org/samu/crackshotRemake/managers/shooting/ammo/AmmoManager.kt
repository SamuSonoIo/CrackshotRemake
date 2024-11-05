package org.samu.crackshotRemake.managers.shooting.ammo

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.managers.SoundManager
import org.samu.crackshotRemake.weapon.instances.Weapon

class AmmoManager(val crackshotRemake: CrackshotRemake) {
    fun removeAmmo(player: Player, weapon: Weapon) {
        if (weapon.projectilsCurrentAmount > 0) {
            weapon.projectilsCurrentAmount -= 1
            player.sendMessage("Current ammo: " + weapon.projectilsCurrentAmount.toString())
        } else{
            startReloading(player, weapon)
        }
    }

    fun startReloading(player: Player, weapon: Weapon) {
        val reloadTicks = weapon.reloadDuration * 20L
        SoundManager.playReloadSound(weapon, player)
        object : BukkitRunnable() {
            var ticks = 0L

            override fun run() {
                if (ticks >= reloadTicks) {
                    weapon.projectilsCurrentAmount = weapon.projectileAmount
                    player.sendMessage("Ricarica completata!")
                    cancel()
                }
                ticks += 20L
            }
        }.runTaskTimer(crackshotRemake, 0L, 20L)
    }

}