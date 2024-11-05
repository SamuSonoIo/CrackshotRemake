package org.samu.crackshotRemake.managers.nbt

import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.inventory.ItemStack
import org.samu.crackshotRemake.weapon.instances.Weapon

class AssignNbt {
    fun setWeaponNBT(weapon: Weapon): ItemStack {
        val weaponItem = weapon.itemStack.clone()
        val nbtItem = NBTItem(weaponItem)
        nbtItem.setString("weaponId", "nbttag.${weapon.name}.crackshot" )

        return nbtItem.item
    }
}