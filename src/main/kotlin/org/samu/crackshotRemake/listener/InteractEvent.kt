package org.samu.crackshotRemake.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.instances.Weapon

class InteractEvent : Listener {
    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        val player:Player = e.player
        val weapon: Weapon? = CrackshotRemake.gunManager?.getWeaponInHand(player)
        if (weapon != null) {
            CrackshotRemake.gunManager?.shootGun(weapon, e)
            if (CrackshotRemake.gunManager?.cancelRightClickBlockInteraction(weapon) == true) {
                e.isCancelled = true
            }
        }
    }
}