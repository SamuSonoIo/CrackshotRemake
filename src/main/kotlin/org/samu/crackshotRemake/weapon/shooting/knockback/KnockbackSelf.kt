package org.samu.crackshotRemake.weapon.shooting.knockback

import org.bukkit.entity.Player
import org.bukkit.util.Vector
import org.samu.crackshotRemake.weapon.instances.Weapon

object KnockbackSelf {
    fun giveKnockback(player: Player, weapon: Weapon) {
        if (weapon.knockBackSelf != 0) {
            val knockbackVector: Vector = player.location.direction.multiply(weapon.knockBackSelf.toDouble())
            player.velocity = player.velocity.add(knockbackVector)
        }
    }
}