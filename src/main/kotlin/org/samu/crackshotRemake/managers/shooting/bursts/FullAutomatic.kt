package org.samu.crackshotRemake.managers.shooting.bursts

import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.managers.SoundManager
import org.samu.crackshotRemake.managers.shooting.LaunchProjectile
import org.samu.crackshotRemake.weapon.instances.Weapon

object FullAutomatic {
    fun shootAutomaticFullAuto(crackshotRemake: CrackshotRemake, weapon: Weapon, e: PlayerInteractEvent) {
        if (weapon.fullAutomaticEnabled) {
            val player = e.player

            object : BukkitRunnable() {
                override fun run() {
                    if (CrackshotRemake.gunManager!!.canUseWeapon(weapon, player)) {
                        LaunchProjectile.shootProjectile(player, weapon)
                        crackshotRemake.ammoManager?.removeAmmo(player, weapon)
                        SoundManager.playShootSound(weapon, player)
                        object : BukkitRunnable() {
                            override fun run() {
                                if (CrackshotRemake.gunManager!!.canUseWeapon(weapon, player)) {
                                    LaunchProjectile.shootProjectile(player, weapon)
                                    crackshotRemake.ammoManager?.removeAmmo(player, weapon)
                                    SoundManager.playShootSound(weapon, player)

                                    if (weapon.projectilsCurrentAmount <= 0) {
                                        cancel()
                                    }
                                } else {
                                    cancel()
                                }
                            }
                        }.runTaskTimer(crackshotRemake, weapon.fullAutomaticFireRate.toLong(), weapon.fullAutomaticFireRate.toLong())
                    } else {
                        cancel()
                    }
                }
            }.runTaskLater(crackshotRemake, weapon.delayFullAuto.toLong())
        } else {
            crackshotRemake.gunShooting?.shootGun(crackshotRemake, weapon, e)
        }
    }

    fun fullAutoCheck(crackshotRemake: CrackshotRemake, weapon: Weapon, e: PlayerInteractEvent) {
        if (weapon.fullAutomaticEnabled) shootAutomaticFullAuto(crackshotRemake, weapon, e)
    }
}