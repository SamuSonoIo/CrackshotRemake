import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.weapon.instances.Weapon
import org.samu.crackshotRemake.managers.CacheManager
import org.samu.crackshotRemake.managers.shooting.LaunchProjectile

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

    fun removeAmmo(player: Player, weapon: Weapon) {
        if (weapon.projectilsCurrentAmount > 0) {
            weapon.projectilsCurrentAmount -= 1

        } else{
            weapon.projectilsCurrentAmount = weapon.projectileAmount
        }
    }

    fun toggleScope(player: Player, weapon: Weapon) {
        if (scope(weapon)) {
            if (CacheManager.scopeActive(player)) {
                player.removePotionEffect(PotionEffectType.SLOW)
                CacheManager.removeScope(player)
            } else {
                CacheManager.setScope(player)
                player.addPotionEffect(PotionEffect(PotionEffectType.SLOW, -1, 1, true, false))
            }
        }
    }

    fun scope(weapon: Weapon):Boolean { return weapon.scopeEnabled }
    fun cancelRightClickBlockInteraction(weapon: Weapon):Boolean { return weapon.cancelRightClickInteractions }
}
