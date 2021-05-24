package network.cow.mc.cowshed.listener

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.format.NamedTextColor
import network.cow.mc.cowshed.Translations
import network.cow.messages.adventure.component
import network.cow.messages.adventure.gradient
import network.cow.messages.adventure.highlight
import network.cow.messages.adventure.info
import network.cow.messages.adventure.plus
import network.cow.messages.adventure.prefix
import network.cow.messages.adventure.translateToComponent
import network.cow.messages.core.Gradients
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author Benedikt Wüller
 */
class ChatListener : Listener {

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.displayName(player.displayName().highlight()) // TODO: use indigo to determine color

        event.joinMessage(Translations.JOIN_MESSAGES.random()
                .translateToComponent(player, player.displayName())
                .info()
                .prefix("Lobby".gradient(Gradients.CORPORATE)))
    }

    @EventHandler
    private fun onLeave(event: PlayerQuitEvent) {
        event.quitMessage(null)
    }

    @EventHandler
    private fun onChat(event: AsyncChatEvent) {
        event.composer { _, _, _ ->
            val name = event.player.displayName()
            val message = event.message()
            return@composer name + ": ".component(NamedTextColor.WHITE) + message
        }
    }

}
