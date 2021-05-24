package network.cow.mc.cowshed.command

import network.cow.mc.cowshed.Translations
import network.cow.messages.spigot.sendTranslatedError
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author Benedikt Wüller
 */
class SpawnCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>) : Boolean {
        if (sender !is Player) {
            sender.sendTranslatedError(Translations.Common.Command.ERROR_REQUIRES_PLAYER)
            return true
        }

        // TODO: read spawn location from config (?)
        sender.teleport(sender.world.spawnLocation)
        return true
    }

}
