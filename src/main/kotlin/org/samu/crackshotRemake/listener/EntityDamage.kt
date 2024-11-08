package org.samu.crackshotRemake.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.weapon.instances.Weapon

class EntityDamage(): Listener {
    @EventHandler
    fun onDamage(e: EntityDamageByEntityEvent) {
        if (e.damager is Player) {
            val damager = e.damager as Player
            val weapon: Weapon? = CrackshotRemake.classLoader?.nbtCheck?.getWeaponInHand(damager)

            if (weapon != null) {
                KnockbackTarget.giveTargetKnockback(weapon, e)
            }
        }
    }
}