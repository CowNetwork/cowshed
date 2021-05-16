package network.cow.mc.cowshed.listener

import network.cow.mc.cowshed.command.SonicCommand
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @author Benedikt Wüller
 */
class JoinListener : Listener {

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        player.gameMode = GameMode.SURVIVAL
        player.walkSpeed = SonicCommand.DEFAULT_WALK_SPEED
    }

}
