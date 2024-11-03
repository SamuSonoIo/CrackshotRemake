import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
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

    fun shootGun(player: Player, weapon: Weapon) {
        LaunchProjectile.shootProjectile(player, weapon)
        CacheManager.getWeapon(weapon.id)?.reloadAmount = CacheManager.getWeapon(weapon.id)?.reloadAmount?.minus(1)!!
        for (string in weapon.soundsShoot) {
            player.playSound(player.getLocation(), string, 1.0f, 1.0f)
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
}
