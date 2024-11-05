package org.samu.crackshotRemake.managers.shooting

import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.managers.SoundManager
import org.samu.crackshotRemake.managers.shooting.bursts.FullAutomatic
import org.samu.crackshotRemake.managers.shooting.knockback.KnockbackSelf
import org.samu.crackshotRemake.weapon.enums.ShotKey
import org.samu.crackshotRemake.weapon.instances.Weapon

object GunShooting {
    fun shootGun(crackshotRemake: CrackshotRemake, weapon: Weapon, e: PlayerInteractEvent) {
        if (rightInteraction(weapon, e)) {
            val player: Player = e.player
            if (CrackshotRemake.gunManager!!.canUseWeapon(weapon, player)) {
                LaunchProjectile.shootProjectile(player, weapon)

                CrackshotRemake.gunManager!!.removeAmmo(player, weapon)
                SoundManager.playShootSound(weapon, player)

                FullAutomatic.fullAutoCheck(crackshotRemake, weapon, e)
                KnockbackSelf.giveKnockback(player, weapon)
            }

            if (e.action == Action.LEFT_CLICK_BLOCK || e.action == Action.LEFT_CLICK_AIR) {
                CrackshotRemake.gunManager?.toggleScope(player, weapon)
            }
        }
    }

    fun rightInteraction(weapon: Weapon, e: PlayerInteractEvent): Boolean {
        val shotKey: ShotKey = weapon.shotKey
        return shotKey.matches(e.action)
    }
}
