package org.samu.crackshotRemake.weapon.nbt

import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.samu.crackshotRemake.managers.CacheManager
import org.samu.crackshotRemake.weapon.instances.Weapon

class CheckNBT {
    fun getWeaponInHand(player: Player): Weapon? {
        val item: ItemStack = player.inventory.itemInHand ?: return null
        val nbtItem = NBTItem(item)
        val weaponId = nbtItem.getString("weaponId") ?: return null
        for (weapon in CacheManager.getAllWeapons()) {
            if (weapon.id == weaponId) {
                return weapon
            }
        }
        return null
    }
}