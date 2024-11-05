package org.samu.crackshotRemake.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.managers.nbt.NbtCheck
import org.samu.crackshotRemake.managers.shooting.GunShooting
import org.samu.crackshotRemake.weapon.instances.Weapon

class InteractEvent(val crackshotRemake: CrackshotRemake) : Listener {
    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        val player:Player = e.player
        val weapon: Weapon? = crackshotRemake.nbtCheck?.getWeaponInHand(player)
        if (weapon != null) {
            crackshotRemake.gunShooting?.shootGun(crackshotRemake, weapon, e)
            if (CrackshotRemake.gunManager?.cancelRightClickBlockInteraction(weapon) == true) {
                e.isCancelled = true
            }
        }
    }
}