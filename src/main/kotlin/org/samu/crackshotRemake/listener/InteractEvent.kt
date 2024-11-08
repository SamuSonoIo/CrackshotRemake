package org.samu.crackshotRemake.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.managers.ClassLoader
import org.samu.crackshotRemake.weapon.instances.Weapon

class InteractEvent() : Listener {
    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        val player:Player = e.player
        val weapon: Weapon? = CrackshotRemake.classLoader?.nbtCheck?.getWeaponInHand(player)
        if (weapon != null) {
            CrackshotRemake.classLoader?.gunShooting?.shootGun(weapon, e)
            if (ClassLoader?.gunManager?.cancelRightClickBlockInteraction(weapon) == true) {
                e.isCancelled = true
            }
            if (e.action.equals(Action.LEFT_CLICK_BLOCK) || e.action.equals(Action.LEFT_CLICK_AIR)) {
                ClassLoader?.gunManager?.toggleScope(player, weapon)
            }
        }
    }
}