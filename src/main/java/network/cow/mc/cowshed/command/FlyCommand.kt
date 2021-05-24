package network.cow.mc.cowshed.command

import network.cow.mc.cowshed.Translations
import network.cow.mc.cowshed.state
import network.cow.messages.adventure.active
import network.cow.messages.adventure.inactive
import network.cow.messages.adventure.translate
import network.cow.messages.adventure.translateToComponent
import network.cow.messages.spigot.sendInfo
import network.cow.messages.spigot.sendTranslatedError
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author Benedikt Wüller
 */
class FlyCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>) : Boolean {
        if (args.isEmpty()) {
            if (sender !is Player) {
                sender.sendTranslatedError(Translations.Common.Command.ERROR_REQUIRES_PLAYER)
                return true
            }
            this.toggleFlight(sender)
            return true
        }

        if (args.size == 1) {
            val player = Bukkit.getPlayer(args.first())
            if (player == null) {
                sender.sendTranslatedError(Translations.Common.Command.ERROR_PLAYER_NOT_FOUND)
                return true
            }

            val message = Translations.Minecraft.Fly.MESSAGE_STATE
            val result = this.toggleFlight(player)

            sender.sendInfo(message.translateToComponent(sender, when {
                result -> Translations.Common.State.ENABLED.translate(sender).active()
                else -> Translations.Common.State.DISABLED.translate(sender).inactive()
            }, player.displayName()))

            return true
        }

        return false
    }

    private fun toggleFlight(player: Player) : Boolean {
        val state = player.state
        val shouldFly = !state.isFlying

        if (shouldFly) {
            player.allowFlight = true
            state.isBuilding = false
            state.isFlying = true
            player.sendInfo(Translations.Minecraft.Fly.MESSAGE_ENABLED.translateToComponent(player, Translations.Minecraft.Fly.STATE_ENABLED.translate(player).active()))
        } else {
            player.allowFlight = state.canMultiJump
            state.isFlying = false
            player.sendInfo(Translations.Minecraft.Fly.MESSAGE_DISABLED.translateToComponent(player, Translations.Minecraft.Fly.STATE_DISABLED.translate(player).inactive()))
        }

        return shouldFly
    }

}
