package org.samu.crackshotRemake.managers

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.weapon.enums.ProjectileTypes
import org.samu.crackshotRemake.weapon.enums.ShotKey
import org.samu.crackshotRemake.weapon.instances.Weapon
import java.io.File
import java.util.*

class FileManager() {
    lateinit var crackshotRemake: CrackshotRemake
    var weaponsfile: File? = null
    var weapons: YamlConfiguration? = null
    var explosivesfile: File? = null
    var explosives: YamlConfiguration? = null

    fun setupFiles() {
        crackshotRemake = ClassLoader.crackshotRemake!!
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
            val shotKey: ShotKey = ShotKey.valueOf(weaponSection.getString("Shooting.Shot_KEY"))
            val itemStack = ItemStack(itemType)
            val meta: ItemMeta = itemStack.itemMeta ?: continue
            meta.displayName = displayName
            meta.lore = ArrayList()
            meta.lore.add(lore)

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
            val soundsReloading = weaponSection.getString("Reload.Sounds_Reloading")?.split(",")?.map { Sound.valueOf(it.split("-")[0]) } ?: emptyList()

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
            val knockBackTarget : Int = weaponSection.getInt("Abilities.KnockbackTarget")
            val knockBackSelf : Int = weaponSection.getInt("Abilities.KnockbackSelf")

            val scopeEnabled = weaponSection.getBoolean("Scope.Enable")
            val scopeSound = Sound.valueOf(weaponSection.getString("Scope.Sound")!!.split("-")[0])

            val fullAutomaticEnabled = weaponSection.getBoolean("Full_Automatic.Enable")
            val fullAutomaticFireRate = weaponSection.getInt("Full_Automatic.Fire_Rate")
            val delayFullAuto = weaponSection.getInt("Full_Automatic.Delay")

            val weapon = Weapon(
                id = "nbttag.$name.crackshot",
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
                resetHitCooldown = resetHitCooldown,
                knockBackSelf = knockBackSelf,
                knockBackTarget = knockBackTarget,
                superEffective = superEffective,
                particlesEnabled = particlesEnabled,
                particlePlayerShoot = particlePlayerShoot,
                hitEventsEnabled = hitEventsEnabled,
                soundsShooter = soundsShooter,
                scopeEnabled = scopeEnabled,
                scopeSound = scopeSound,
                fullAutomaticEnabled = fullAutomaticEnabled,
                fullAutomaticFireRate = fullAutomaticFireRate,
                delayFullAuto = delayFullAuto
            )
            CacheManager.addWeapon(weapon)

            for (weapon in CacheManager.getAllWeapons()) {
                println("${weapon.name}-PROJECTILES: ${weapon.projectileAmount}")
                println("${weapon.name}-DISPLAYNAME: \"${weapon.displayName}\"")
                println("${weapon.name}-ITEM_TYPE: ${weapon.type}")
                println("${weapon.name}-SOUND_ACQUIRED: ${weapon.soundAcquired}")
                println("${weapon.name}-LORE: \"${weapon.description}\"")
                println("${weapon.name}-SHOT_KEY: ${weapon.shotKey}")
                println("${weapon.name}-PROJECTILE_TYPE: ${weapon.projectileType}")
                println("${weapon.name}-PROJECTILE_SPEED: ${weapon.projectileSpeed}")
                println("${weapon.name}-PROJECTILE_DAMAGE: ${weapon.projectileDamage}")
                println("${weapon.name}-CANCEL_LEFT_CLICK_BLOCK_DAMAGE: ${weapon.cancelLeftClickBlockDamage}")
                println("${weapon.name}-CANCEL_RIGHT_CLICK_INTERACTIONS: ${weapon.cancelRightClickInteractions}")
                println("${weapon.name}-RELOAD_ENABLED: ${weapon.reloadEnabled}")
                println("${weapon.name}-RELOAD_AMOUNT: ${weapon.reloadAmount}")
                println("${weapon.name}-RELOAD_BULLETS_INDIVIDUALLY: ${weapon.reloadBulletsIndividually}")
                println("${weapon.name}-RELOAD_DURATION: ${weapon.reloadDuration}")
                println("${weapon.name}-SOUNDS_RELOADING: ${weapon.soundsReloading}")
                println("${weapon.name}-SOUNDS_SHOOT: ${weapon.soundsShoot}")
                println("${weapon.name}-RESET_HIT_COOLDOWN: ${weapon.resetHitCooldown}")
                println("${weapon.name}-KNOCKBACK_SELF: ${weapon.knockBackSelf}")
                println("${weapon.name}-KNOCKBACK_TARGET: ${weapon.knockBackTarget}")
                println("${weapon.name}-SUPER_EFFECTIVE_ENTITY: ${weapon.superEffective.first}")
                println("${weapon.name}-SUPER_EFFECTIVE_DAMAGE: ${weapon.superEffective.second}")
                println("${weapon.name}-PARTICLES_ENABLED: ${weapon.particlesEnabled}")
                println("${weapon.name}-PARTICLE_PLAYER_SHOOT: ${weapon.particlePlayerShoot}")
                println("${weapon.name}-HIT_EVENTS_ENABLED: ${weapon.hitEventsEnabled}")
                println("${weapon.name}-SOUNDS_SHOOTER: ${weapon.soundsShooter}")
                println("${weapon.name}-SCOPE_ENABLED: ${weapon.scopeEnabled}")
                println("${weapon.name}-SCOPE_SOUND: ${weapon.scopeSound}")
                println("${weapon.name}-FULL_AUTOMATIC_ENABLED: ${weapon.fullAutomaticEnabled}")
                println("${weapon.name}-FULL_AUTOMATIC_FIRE_RATE: ${weapon.fullAutomaticFireRate}")
                println("${weapon.name}-DELAY_FULL_AUTO: ${weapon.delayFullAuto}")
            }

        }
    }
}
