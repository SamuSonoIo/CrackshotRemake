package org.samu.crackshotRemake.weapon.instances

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import org.samu.crackshotRemake.weapon.enums.ProjectileTypes
import org.samu.crackshotRemake.weapon.enums.ShotKey
import java.util.UUID

class Weapon(
    val id: String,
    val itemStack: ItemStack,
    val name: String,
    val displayName: String,
    var type: Material = Material.AIR,
    val soundAcquired: Sound,
    val description: String,
    val shotKey: ShotKey,
    val projectileType: ProjectileTypes,
    val projectileSubtype: String,
    val projectileDamage: Double,
    val projectileSpeed: Double,
    val soundsShoot: List<Sound>,
    val cancelLeftClickBlockDamage: Boolean,
    val cancelRightClickInteractions: Boolean,
    val projectileAmount: Int,
    var projectilsCurrentAmount: Int = projectileAmount,
    val reloadEnabled: Boolean,
    var reloadAmount: Int,
    val reloadBulletsIndividually: Boolean,
    val reloadDuration: Int,
    val soundsReloading: List<Sound>,
    val resetHitCooldown: Boolean,
    val knockBackTarget: Int = 1,
    val knockBackSelf: Int = 0,
    val superEffective: Pair<EntityType, Double>,
    val particlesEnabled: Boolean,
    val particlePlayerShoot: String,
    val hitEventsEnabled: Boolean,
    val soundsShooter: Sound,
    val scopeEnabled: Boolean,
    val scopeSound: Sound,
    val fullAutomaticEnabled: Boolean,
    var fullAutomaticFireRate: Int = 8,
    var delayFullAuto:Int = 3
)
