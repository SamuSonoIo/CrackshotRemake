package org.samu.crackshotRemake.managers

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.gunsettings.enums.ProjectileTypes
import org.samu.crackshotRemake.gunsettings.enums.ShotKey
import org.samu.crackshotRemake.instances.Weapon
import java.io.File
import java.util.*

object FileManager {
    var weaponsfile: File? = null
    var weapons: YamlConfiguration? = null
    var explosivesfile: File? = null
    var explosives: YamlConfiguration? = null

    fun setupFiles(crackshotRemake: CrackshotRemake) {
        if (!crackshotRemake.dataFolder.exists()) {
            crackshotRemake.dataFolder.mkdir()
        }

        weaponsfile = File(crackshotRemake.dataFolder, "weapons.yml")
        explosivesfile = File(crackshotRemake.dataFolder, "explosives.yml")

        if (!weaponsfile!!.exists()) {
            crackshotRemake.saveResource("weapons.yml", false)
        }
        if (!explosivesfile!!.exists()) {
            crackshotRemake.saveResource("explosives.yml", false)
        }

        weapons = YamlConfiguration.loadConfiguration(weaponsfile)
        explosives = YamlConfiguration.loadConfiguration(explosivesfile)
    }

    fun reloadFiles() {
        weapons?.load(weaponsfile)
        explosives?.load(explosivesfile)
    }

    fun createWeapons() {
        for (weaponKey in weapons!!.getKeys(false)) {
            val weaponSection = weapons!!.getConfigurationSection(weaponKey) ?: continue

            val displayName = weaponSection.getString("Item_Information.DisplayName") ?: continue
            val name = weaponKey
            val itemTypeString = weaponSection.getString("Item_Information.Item_Type") ?: continue
            val itemType = Material.valueOf(itemTypeString)
            val lore = weaponSection.getString("Item_Information.Item_Lore") ?: ""
            val soundAcquired = Sound.valueOf(weaponSection.getString("Item_Information.Sounds_Acquired")!!.split("-")[0])
            val shotKey:ShotKey = ShotKey.valueOf(weaponSection.getString("Shooting.Shot_KEY"))
            val itemStack = ItemStack(itemType)
            val meta: ItemMeta = itemStack.itemMeta ?: continue
            meta.displayName = displayName
            meta.lore = ArrayList<String>().apply { lore }

            itemStack.itemMeta = meta
            val cancelLeftClickBlockDamage = weaponSection.getBoolean("Shooting.Cancel_Left_Click_Block_Damage")
            val cancelRightClickInteractions = weaponSection.getBoolean("Shooting.Cancel_Right_Click_Interactions")
            val projectileAmount = weaponSection.getInt("Shooting.Projectile_Amount")
            val projectileType = ProjectileTypes.valueOf(weaponSection.getString("Shooting.Projectile_Type") ?: "ARROW")
            val projectileSubtype = weaponSection.getString("Shooting.Projectile_Subtype") ?: ""
            val projectileDamage = weaponSection.getDouble("Shooting.Projectile_Damage")
            val projectileSpeed = weaponSection.getDouble("Shooting.Projectile_Speed")
            val soundsShoot = weaponSection.getString("Shooting.Sounds_Shoot")?.split(",")?.map { Sound.valueOf(it.split("-")[0]) } ?: emptyList()

            val reloadEnable = weaponSection.getBoolean("Reload.Enable")
            val reloadAmount = weaponSection.getInt("Reload.Reload_Amount")
            val reloadBulletsIndividually = weaponSection.getBoolean("Reload.Reload_Bullets_Individually")
            val reloadDuration = weaponSection.getInt("Reload.Reload_Duration")
            val soundsReloading = Sound.valueOf(weaponSection.getString("Reload.Sounds_Reloading")!!.split("-")[0])

            val openDuration = weaponSection.getInt("Firearm_Action.Open_Duration")
            val closeDuration = weaponSection.getInt("Firearm_Action.Close_Duration")
            val soundOpen = Sound.valueOf(weaponSection.getString("Firearm_Action.Sound_Open")!!.split("-")[0])
            val soundClose = Sound.valueOf(weaponSection.getString("Firearm_Action.Sound_Close")!!.split("-")[0])

            val resetHitCooldown = weaponSection.getBoolean("Abilities.Reset_Hit_Cooldown")
            val superEffectiveString = weaponSection.getString("Abilities.Super_Effective") ?: ""
            val superEffective = if (superEffectiveString.isNotEmpty()) {
                val parts = superEffectiveString.split("-")
                Pair(EntityType.valueOf(parts[0]), parts[1].toDouble())
            } else {
                Pair(EntityType.UNKNOWN, 0.0)
            }

            val particlesEnabled = weaponSection.getBoolean("Particles.Enable")
            val particlePlayerShoot = weaponSection.getString("Particles.Particle_Player_Shoot") ?: ""

            val hitEventsEnabled = weaponSection.getBoolean("Hit_Events.Enable")
            val soundsShooter = Sound.valueOf(weaponSection.getString("Hit_Events.Sounds_Shooter")!!.split("-")[0])

            val scopeEnabled = weaponSection.getBoolean("Scope.Enable")
            val scopeSound = Sound.valueOf(weaponSection.getString("Scope.Sound")!!.split("-")[0])

            val weapon = Weapon(
                id = UUID.randomUUID(),
                itemStack = itemStack,
                name = name,
                displayName = displayName,
                type = itemType,
                soundAcquired = soundAcquired,
                description = lore,
                shotKey = shotKey,
                projectileType = projectileType,
                projectileSpeed = projectileSpeed,
                projectileDamage = projectileDamage,
                projectileAmount = projectileAmount,
                projectileSubtype = projectileSubtype,
                cancelLeftClickBlockDamage = cancelLeftClickBlockDamage,
                cancelRightClickInteractions = cancelRightClickInteractions,
                reloadEnabled = reloadEnable,
                reloadAmount = reloadAmount,
                reloadBulletsIndividually = reloadBulletsIndividually,
                reloadDuration = reloadDuration,
                soundsReloading = soundsReloading,
                soundsShoot = soundsShoot,
                openDuration = openDuration,
                closeDuration = closeDuration,
                soundOpen = soundOpen,
                soundClose = soundClose,
                resetHitCooldown = resetHitCooldown,
                superEffective = superEffective,
                particlesEnabled = particlesEnabled,
                particlePlayerShoot = particlePlayerShoot,
                hitEventsEnabled = hitEventsEnabled,
                soundsShooter = soundsShooter,
                scopeEnabled = scopeEnabled,
                scopeSound = scopeSound
            )
            CacheManager.addWeapon(weapon)
        }
    }
}
