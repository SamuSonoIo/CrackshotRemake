import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.instances.Weapon
import org.samu.crackshotRemake.managers.CacheManager
import org.samu.crackshotRemake.managers.projectile.LaunchProjectile

class GunManager(val crackshotRemake: CrackshotRemake) {
    fun canUseWeapon(weapon: Weapon, player: Player): Boolean {
        return player.hasPermission("crackshot.remake.use." + weapon.name) || player.hasPermission("crackshot.remake.useall")
    }

    fun getWeaponInHand(player: Player): Weapon? {
        val item: ItemStack = player.inventory.itemInHand ?: return null
        val nbtItem = NBTItem(item)
        val weaponId = nbtItem.getString("weaponId") ?: return null
        for (weapon in CacheManager.getAllWeapons()) {
            if (weapon.id.toString() == weaponId) {
                return weapon
            }
        }
        return null
    }

    fun setWeaponNBT(weapon: Weapon): ItemStack {
        val weaponItem = weapon.itemStack.clone()
        val nbtItem = NBTItem(weaponItem)
        nbtItem.setString("weaponId", weapon.id.toString())

        return nbtItem.item
    }

    fun shootGun(weapon: Weapon, e: PlayerInteractEvent) {
        val player:Player = e.player
        if (canUseWeapon(weapon, player)) {
            LaunchProjectile.shootProjectile(player, weapon)
            removeAmmo(weapon)
            for (string in weapon.soundsShoot) {
                player.playSound(player.location, string, 1.0f, 1.0f)
            }
        }

        if (scope(weapon) && (e.action.equals(Action.LEFT_CLICK_BLOCK) || e.action.equals(Action.LEFT_CLICK_AIR))) {
            if (CacheManager.scopeActive(player)) {
                player.removePotionEffect(PotionEffectType.SLOW)
                CacheManager.removeScope(player)
            } else {
                CacheManager.setScope(player)
                player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 99999, 1, true, false))
            }
        }
    }

    fun giveGun(player: Player, name: String) {
        for (weapon in CacheManager.getAllWeapons()) {
            if (weapon.name.equals(name, ignoreCase = true)) {
                player.inventory.addItem(setWeaponNBT(weapon))
                player.sendMessage("Hai ricevuto l'arma: ${weapon.name}")
                return
            }
        }
        player.sendMessage("Arma non trovata: $name")
    }

    fun removeAmmo(weapon: Weapon) {
        if (weapon.projectilsCurrentAmount > 0) {
            weapon.projectilsCurrentAmount -= 1
            println("Current Ammo: " + weapon.reloadAmount)
        } else{
            weapon.projectilsCurrentAmount = weapon.projectileAmount
        }
    }

    fun scope(weapon: Weapon):Boolean { return weapon.scopeEnabled }
    fun cancelRightClickBlockInteraction(weapon: Weapon):Boolean { return weapon.cancelRightClickInteractions }
}
