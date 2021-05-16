package network.cow.mc.cowshed.listener

import network.cow.mc.cowshed.command.SonicCommand.Companion.DEFAULT_WALK_SPEED
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author Benedikt Wüller
 */
class SonicListener : Listener {

    @EventHandler
    private fun onQuit(event: PlayerQuitEvent) {
        event.player.walkSpeed = DEFAULT_WALK_SPEED
    }

}
