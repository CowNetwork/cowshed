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
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author Benedikt Wüller
 */
class BuildCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>) : Boolean {
        if (args.isEmpty()) {
            if (sender !is Player) {
                sender.sendTranslatedError(Translations.Common.Command.ERROR_REQUIRES_PLAYER)
                return true
            }
            this.toggleBuild(sender)
            return true
        }

        if (args.size == 1) {
            val player = Bukkit.getPlayer(args.first())
            if (player == null) {
                sender.sendTranslatedError(Translations.Common.Command.ERROR_PLAYER_NOT_FOUND)
                return true
            }

            val message = Translations.Minecraft.Build.MESSAGE_STATE
            val result = this.toggleBuild(player)

            sender.sendInfo(message.translateToComponent(sender, when {
                result -> Translations.Common.State.ENABLED.translate(sender).active()
                else -> Translations.Common.State.DISABLED.translate(sender).inactive()
            }, player.displayName()))

            return true
        }

        return false
    }

    private fun toggleBuild(player: Player) : Boolean {
        val state = player.state
        val shouldBuild = !state.isBuilding

        if (shouldBuild) {
            player.gameMode = GameMode.CREATIVE
            player.allowFlight = true
            state.isBuilding = true
            state.isFlying = false
            player.sendInfo(Translations.Minecraft.Build.MESSAGE_ENABLED.translateToComponent(player, Translations.Minecraft.Build.STATE_ENABLED.translate(player).active()))
        } else {
            player.gameMode = GameMode.SURVIVAL
            player.allowFlight = state.canMultiJump
            state.isBuilding = false
            player.sendInfo(Translations.Minecraft.Build.MESSAGE_DISABLED.translateToComponent(player, Translations.Minecraft.Build.STATE_DISABLED.translate(player).inactive()))
        }

        return shouldBuild
    }

}
