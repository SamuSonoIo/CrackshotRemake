import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.util.Vector
import org.samu.crackshotRemake.weapon.instances.Weapon

object KnockbackTarget {
    fun giveTargetKnockback(weapon: Weapon, e: EntityDamageByEntityEvent) {
        if (e.damager is Player) {
            val entity = e.entity
            val damager = e.damager as Player

            if (weapon.knockBackTarget != 0) {
                val knockbackVector: Vector = entity.location.toVector()
                    .subtract(damager.location.toVector())
                    .normalize()
                    .multiply(weapon.knockBackTarget.toDouble())
                entity.velocity = entity.velocity.add(knockbackVector)
            }
        }
    }
}
