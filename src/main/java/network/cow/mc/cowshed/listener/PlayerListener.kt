package network.cow.mc.cowshed.listener

import com.github.juliarn.npc.modifier.LabyModModifier
import network.cow.mc.cowshed.Config
import network.cow.mc.cowshed.CowshedPlugin
import network.cow.mc.cowshed.command.SonicCommand
import network.cow.mc.cowshed.NpcRegistry
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author Benedikt Wüller
 */
class PlayerListener(private val config: Config) : Listener {

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        // Reset player stats.
        player.gameMode = GameMode.SURVIVAL
        player.walkSpeed = SonicCommand.DEFAULT_WALK_SPEED
        player.teleport(config.spawnLocation)

        Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(CowshedPlugin::class.java), Runnable {
            // winke, winke :3
            NpcRegistry.get("gustav")?.labymod()?.queue(LabyModModifier.LabyModAction.EMOTE, 4)
        }, 40L)
    }

    @EventHandler
    private fun onMove(event: PlayerMoveEvent) {
        if (event.to.y > 32) return
        val player = event.player
        player.teleport(config.spawnLocation)
    }
}
