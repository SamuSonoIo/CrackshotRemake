package org.samu.crackshotRemake.managers.projectile

import org.bukkit.entity.*
import org.bukkit.util.Vector
import org.samu.crackshotRemake.gunsettings.enums.ProjectileTypes
import org.samu.crackshotRemake.instances.Weapon

object LaunchProjectile {
    fun shootProjectile(player: Player, weapon: Weapon) {
        val projectileType = weapon.projectileType
        val speed = weapon.projectileSpeed
        val projectilesAmount = weapon.projectileAmount

        for (i in 1..projectilesAmount) {
            val projectile = player.launchProjectile(getProjectileClass(projectileType)) as Projectile
            projectile.velocity = player.location.direction.multiply(speed)
        }
    }

    private fun getProjectileClass(projectileType: ProjectileTypes): Class<out Projectile> {
        return when (projectileType) {
            ProjectileTypes.ARROW -> Arrow::class.java
            ProjectileTypes.FIREBALL -> Fireball::class.java
            ProjectileTypes.SNOWBALL -> Snowball::class.java
            ProjectileTypes.EGG -> Egg::class.java
            ProjectileTypes.ENERGY -> Snowball::class.java
        }
    }
}
