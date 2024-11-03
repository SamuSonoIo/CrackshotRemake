package org.samu.crackshotRemake.managers

import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.instances.Weapon
import java.util.UUID

object CacheManager {
    private var weapons:MutableSet<Weapon> = mutableSetOf()

    fun addWeapon(weapon: Weapon) { weapons.add(weapon) }
    fun getWeapon(uuid: UUID): Weapon? { return weapons.find { it.id == uuid } }
    fun getAllWeapons(): List<Weapon> { return weapons.toList() }
}