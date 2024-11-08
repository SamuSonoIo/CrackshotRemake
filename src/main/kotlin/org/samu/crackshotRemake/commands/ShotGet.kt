package org.samu.crackshotRemake.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.samu.crackshotRemake.CrackshotRemake
import org.samu.crackshotRemake.managers.ClassLoader

class ShotGet(): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender is Player) {
            val player: Player = sender
            if (!player.hasPermission("crackshot.remake.give")) {
                player.sendMessage("Non hai permessi per usare questo comando.")
                return true
            }

            if (args?.get(0) == "reload") {
                CrackshotRemake.classLoader?.fileManager?.reloadFiles()
            }

            if (args.isNullOrEmpty() || args.size < 1) {
                player.sendMessage("Usage: /shot get <nomearma>")
                return true
            }
            ClassLoader?.gunManager?.giveGun(player, args[1])
            return true
        }
        return false
    }
}
