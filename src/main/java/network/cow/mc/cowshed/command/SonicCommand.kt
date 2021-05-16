package network.cow.mc.cowshed.command

import network.cow.mc.cowshed.state
import network.cow.messages.adventure.gradient
import network.cow.messages.spigot.sendError
import network.cow.spigot.extensions.ItemBuilder
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.awt.Color as AwtColor

/**
 * @author Benedikt Wüller
 */
class SonicCommand : CommandExecutor {

    companion object {
        const val DEFAULT_WALK_SPEED = 0.2F
        const val SONIC_WALK_SPEED = 0.6F
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>) : Boolean {
        if (sender !is Player) {
            sender.sendError("This command must be executed as a player.") // TODO: translate
            return true
        }

        val isSonic = sender.state.isSonic
        if (!isSonic) {
            val item = ItemBuilder(Material.LEATHER_BOOTS)
                    .name("Sonics Söckchen".gradient(AwtColor(23, 86, 155), AwtColor(32, 123, 222))) // TODO: translate
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

        sender.state.isSonic = !isSonic
        return true
    }

}
