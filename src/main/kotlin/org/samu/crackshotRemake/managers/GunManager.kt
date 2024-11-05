import de.tr7zw.changeme.nbtapi.NBTItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.weapon.instances.Weapon
import org.samu.crackshotRemake.managers.CacheManager
import org.samu.crackshotRemake.managers.ConfigManager
import org.samu.crackshotRemake.managers.nbt.AssignNbt
import org.samu.crackshotRemake.managers.shooting.LaunchProjectile

class GunManager(val crackshotRemake: CrackshotRemake) {
    fun canUseWeapon(weapon: Weapon, player: Player): Boolean {
        return player.hasPermission("crackshot.remake.use." + weapon.name) || player.hasPermission("crackshot.remake.useall")
    }

    fun giveGun(player: Player, name: String) {
        for (weapon in CacheManager.getAllWeapons()) {
            if (weapon.name.equals(name, ignoreCase = true)) {
                player.inventory.addItem(crackshotRemake.assignNbt?.setWeaponNBT(weapon))
                player.sendMessage(ConfigManager.getMessageSuccess("obtained").replace("{name}", weapon.name).replace("{quantity}", "1"))
                return
            }
        }
        player.sendMessage("Arma non trovata: $name")
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
