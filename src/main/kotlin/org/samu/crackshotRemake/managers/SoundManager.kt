package org.samu.crackshotRemake.managers

import org.bukkit.entity.Player
import org.samu.crackshotRemake.weapon.instances.Weapon

object SoundManager {
    fun playShootSound(weapon: Weapon, player: Player) {
        for (string in weapon.soundsShoot) {
            player.playSound(player.location, string, 1.0f, 1.0f)
        }
    }

    fun playReloadSound(weapon: Weapon, player: Player) {
        for (string in weapon.soundsReloading) {
            player.playSound(player.location, string, 1.0f, 1.0f)
        }
    }
}