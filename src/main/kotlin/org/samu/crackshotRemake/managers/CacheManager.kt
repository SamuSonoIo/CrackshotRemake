package org.samu.crackshotRemake.managers

import org.bukkit.entity.Player
import org.samu.crackshotRemake.weapon.instances.Weapon
import java.util.UUID

object CacheManager {
    private var weapons:MutableSet<Weapon> = mutableSetOf()
    private var scope:MutableSet<UUID> = mutableSetOf()

    fun addWeapon(weapon: Weapon) { weapons.add(weapon) }
    fun getWeapon(id: String): Weapon? { return weapons.find { it.id == id } }
    fun getAllWeapons(): List<Weapon> { return weapons.toList() }

    fun setScope(player: Player) { scope.add(player.uniqueId) }
    fun removeScope(player: Player) { scope.remove(player.uniqueId) }
    fun scopeActive(player: Player):Boolean { return scope.contains(player.uniqueId) }
}