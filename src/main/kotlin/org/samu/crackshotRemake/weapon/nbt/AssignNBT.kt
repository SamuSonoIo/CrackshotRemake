package org.samu.crackshotRemake.weapon.nbt

import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.inventory.ItemStack
import org.samu.crackshotRemake.weapon.instances.Weapon

class AssignNBT {
    fun setWeaponNBT(weapon: Weapon): ItemStack {
        val weaponItem = weapon.itemStack.clone()
        val nbtItem = NBTItem(weaponItem)
        nbtItem.setString("weaponId", "nbttag.${weapon.name}.crackshot" )

        return nbtItem.item
    }
}