package network.cow.mc.cowshed.command

import network.cow.mc.cowshed.CowshedPlugin
import network.cow.messages.adventure.gradient
import network.cow.messages.core.Gradients
import network.cow.messages.spigot.sendError
import network.cow.spigot.extensions.ItemBuilder
import network.cow.spigot.extensions.state.getState
import network.cow.spigot.extensions.state.setState
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.LeatherArmorMeta

/**
 * @author Benedikt Wüller
 */
class SonicCommand : CommandExecutor {

    companion object {
        private const val STATE_KEY = "cow.cowshed.sonic"
        private const val DEFAULT_WALK_SPEED = 0.2F
        private const val SONIC_WALK_SPEED = 0.6F
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>) : Boolean {
        if (sender !is Player) {
            sender.sendError("Du bist kein Spieler!")
            return true
        }

        val isSonic = sender.getState(CowshedPlugin::class.java, STATE_KEY) ?: false

        if (!isSonic) {
            val item = ItemBuilder(Material.LEATHER_BOOTS)
                    .name("Sonics Söckchen".gradient(Gradients.CORPORATE))
                    .meta<LeatherArmorMeta> {
                        setColor(Color.fromRGB(255, 151, 216))
                    }
                    .glow()
                    .build()

            sender.inventory.boots = item
            sender.walkSpeed = SONIC_WALK_SPEED
        } else {
            sender.inventory.boots = null
            sender.walkSpeed = DEFAULT_WALK_SPEED
        }

        sender.setState(CowshedPlugin::class.java, STATE_KEY, !isSonic)
        return true
    }

}
