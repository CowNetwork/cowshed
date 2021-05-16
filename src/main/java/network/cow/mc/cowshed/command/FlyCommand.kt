package network.cow.mc.cowshed.command

import network.cow.mc.cowshed.state
import network.cow.messages.adventure.active
import network.cow.messages.adventure.formatToComponent
import network.cow.messages.adventure.inactive
import network.cow.messages.spigot.sendError
import network.cow.messages.spigot.sendInfo
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
                sender.sendError("This command must be executed as a player.") // TODO: translate
                return true
            }
            this.toggleFlight(sender)
            return true
        }

        if (args.size == 1) {
            val player = Bukkit.getPlayer(args.first())
            if (player == null) {
                sender.sendError("The player does not exist.") // TODO: translate
                return true
            }

            val message = "Flight has been %1\$s for player %2\$s." // TODO: translate
            val result = this.toggleFlight(player)

            sender.sendInfo(message.formatToComponent(when {
                result -> "enabled".active()
                else -> "disabled".inactive()
            }))

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
            player.sendInfo("I believe you can fly now (%1\$s).".formatToComponent("flight enabled".active())) // TODO: translate
        } else {
            player.allowFlight = state.canMultiJump
            state.isFlying = false
            player.sendInfo("Whoops, seems like your wings are broken (%1\$s).".formatToComponent("flight disabled".inactive())) // TODO: translate
        }

        return shouldFly
    }

}
