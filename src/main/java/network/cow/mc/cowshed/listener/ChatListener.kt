package network.cow.mc.cowshed.listener

import io.papermc.paper.event.player.AsyncChatEvent
import network.cow.messages.adventure.formatToComponent
import network.cow.messages.adventure.gradient
import network.cow.messages.adventure.highlight
import network.cow.messages.adventure.info
import network.cow.messages.adventure.plus
import network.cow.messages.adventure.prefix
import network.cow.messages.adventure.separator
import network.cow.messages.core.Gradients
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author Benedikt Wüller
 */
class ChatListener : Listener {

    // TODO: translate
    private val joinMessages = listOf(
            "%1\$s just joined the server.",
            "%1\$s just joined. Everyone, look busy!",
            "%1\$s just joined. Hide your bananas.",
            "%1\$s just slid into the server.",
            "A wild %1\$s appeared.",
            "Brace yourselves. %1\$s just joined the server.",
            "A %1\$s has spawned in the server.",
            "%1\$s just showed up. Hold my beer.",
            "%1\$s is here, as the prophecy foretold.",
            "%1\$s has arrived. Party's over.",
            "%1\$s is here.",
            "Welcome, %1\$s. We hope you brought pizza.",
            "Welcome %1\$s. Say hi!",
            "Good to see you, %1\$s."
    )

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.displayName(player.displayName().highlight()) // TODO: use indigo to determine color

        event.joinMessage(this.joinMessages.random()
                .formatToComponent(player.displayName())
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
            return@composer name + " : ".separator() + message
        }
    }

}
