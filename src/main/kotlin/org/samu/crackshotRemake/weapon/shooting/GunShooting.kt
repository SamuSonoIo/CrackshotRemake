package org.samu.crackshotRemake.weapon.shooting

import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.managers.ClassLoader
import org.samu.crackshotRemake.weapon.shooting.knockback.KnockbackSelf
import org.samu.crackshotRemake.util.SoundUtil
import org.samu.crackshotRemake.weapon.enums.ShotKey
import org.samu.crackshotRemake.weapon.instances.Weapon

class GunShooting() {
    fun shootGun(weapon: Weapon, e: PlayerInteractEvent) {
        if (rightInteraction(weapon, e)) {
            val player: Player = e.player
            if (ClassLoader.gunManager!!.canUseWeapon(weapon, player)) {
                LaunchProjectile.shootProjectile(player, weapon)

                CrackshotRemake.classLoader?.ammoManager?.removeAmmo(player, weapon)
                SoundUtil.playShootSound(weapon, player)

                CrackshotRemake.classLoader?.fullAutomatic?.shootAutomaticFullAuto(weapon, e)
                KnockbackSelf.giveKnockback(player, weapon)
            }

            if (e.action == Action.LEFT_CLICK_BLOCK || e.action == Action.LEFT_CLICK_AIR) {
                ClassLoader.gunManager!!.toggleScope(player, weapon)
            }
        }
    }

    fun rightInteraction(weapon: Weapon, e: PlayerInteractEvent): Boolean {
        val shotKey: ShotKey = weapon.shotKey
        return shotKey.matches(e.action)
    }
}
