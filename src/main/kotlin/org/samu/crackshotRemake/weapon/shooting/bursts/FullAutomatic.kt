package org.samu.crackshotRemake.weapon.shooting.bursts

import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.weapon.instances.Weapon

class FullAutomatic(val crackshotRemake: CrackshotRemake) {
    fun shootAutomaticFullAuto(weapon: Weapon, e: PlayerInteractEvent) {
        if (weapon.fullAutomaticEnabled) {
            val fireRate = weapon.fullAutomaticFireRate
            val delayTicks = (20L / fireRate).coerceAtLeast(1L)
            val player = e.player
            object : BukkitRunnable() {
                var shotsFired = 0
                override fun run() {
                    if (shotsFired >= fireRate || !hasAmmo(weapon) || !e.player.isSneaking) {
                        cancel()
                    } else {
                        CrackshotRemake.classLoader?.gunShooting?.shootGun(weapon, e)
                        weapon.projectilsCurrentAmount--
                        shotsFired++
                        player.sendMessage("Full Auto caleed, current shots $shotsFired, Current Projectiles: ${weapon.projectilsCurrentAmount}")
                    }
                }
            }.runTaskTimer(crackshotRemake, 53L, 53L)
        }
    }

    private fun hasAmmo(weapon: Weapon): Boolean {
        return weapon.projectilsCurrentAmount > 0
    }
}

