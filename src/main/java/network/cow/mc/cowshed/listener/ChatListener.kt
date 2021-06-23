package network.cow.mc.cowshed.listener

import io.papermc.paper.chat.ChatRenderer
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.format.NamedTextColor
import network.cow.grape.Grape
import network.cow.indigo.client.spigot.api.IndigoService
import network.cow.mc.cowshed.CowshedPlugin
import network.cow.mc.cowshed.Translations
import network.cow.messages.adventure.component
import network.cow.messages.adventure.highlight
import network.cow.messages.adventure.plus
import network.cow.messages.adventure.toTextColor
import network.cow.messages.core.Colors
import network.cow.messages.spigot.broadcastTranslatedInfo
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import java.awt.Color

/**
 * @author Benedikt Wüller
 */
class ChatListener : Listener {

    private val chatRenderer = ChatRenderer.viewerUnaware { _, name, message -> name + ": ".component(NamedTextColor.WHITE) + message }

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.displayName(player.displayName().highlight())

        Grape.getInstance()[IndigoService::class.java]
            .thenCompose { it.getUserAsync(player.uniqueId) }
            .thenAccept { user ->
                val color = when (val role = user?.getTopRole()) {
                    null -> Colors.HIGHLIGHT
                    else -> Color.decode(role.color)
                }

                // Send the join message delayed after the role color has been determined.
                Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(CowshedPlugin::class.java), Runnable {
                    player.displayName(player.displayName().color(color.toTextColor()))
                    Bukkit.getServer().broadcastTranslatedInfo(Translations.JOIN_MESSAGES.random(), player.displayName())
                })
            }

        event.joinMessage(null)
    }

    @EventHandler
    private fun onLeave(event: PlayerQuitEvent) {
        event.quitMessage(null)
    }

    @EventHandler
    private fun onChat(event: AsyncChatEvent) {
        event.renderer(this.chatRenderer)
    }

}
