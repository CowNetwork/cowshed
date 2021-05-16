package network.cow.mc.cowshed.command

import network.cow.mc.cowshed.state
import network.cow.messages.adventure.active
import network.cow.messages.adventure.formatToComponent
import network.cow.messages.adventure.inactive
import network.cow.messages.spigot.sendError
import network.cow.messages.spigot.sendInfo
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
                sender.sendError("This command must be executed as a player.") // TODO: translate
                return true
            }
            this.toggleBuild(sender)
            return true
        }

        if (args.size == 1) {
            val player = Bukkit.getPlayer(args.first())
            if (player == null) {
                sender.sendError("The player does not exist.") // TODO: translate
                return true
            }

            val message = "Build has been %1\$s for player %2\$s." // TODO: translate
            val result = this.toggleBuild(player)

            sender.sendInfo(message.formatToComponent(when {
                result -> "enabled".active()
                else -> "disabled".inactive()
            }, player.displayName()))

            return true
        }

        return false
    }

    private fun toggleBuild(player: Player) : Boolean {
        val state = player.state
        val shouldBuild = !state.isBuilding

        if (shouldBuild) {
            player.allowFlight = true
            player.gameMode = GameMode.CREATIVE
            state.isBuilding = true
            state.isFlying = false
            player.sendInfo("There you go little fella (%1\$s).".formatToComponent("build enabled".active())) // TODO: translate
        } else {
            player.allowFlight = state.canMultiJump
            player.gameMode = GameMode.SURVIVAL
            state.isBuilding = false
            player.sendInfo("Playing time is over (%1\$s).".formatToComponent("build disabled".inactive())) // TODO: translate
        }

        return shouldBuild
    }

}
