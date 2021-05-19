package network.cow.mc.cowshed.listener

import network.cow.mc.cowshed.command.SonicCommand
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent

/**
 * @author Benedikt Wüller
 */
class PlayerListener : Listener {

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        // Reset player stats.
        player.gameMode = GameMode.SURVIVAL
        player.walkSpeed = SonicCommand.DEFAULT_WALK_SPEED

        // TODO: read spawn location from config (?)
        player.teleport(player.world.spawnLocation)
    }

    @EventHandler
    private fun onMove(event: PlayerMoveEvent) {
        if (event.to.y > 32) return
        val player = event.player

        // TODO: read spawn location from config (?)
        player.teleport(player.world.spawnLocation)
    }

}
