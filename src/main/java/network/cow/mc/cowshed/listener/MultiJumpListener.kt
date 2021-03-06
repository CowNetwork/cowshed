package network.cow.mc.cowshed.listener

import network.cow.mc.cowshed.state
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerToggleFlightEvent

/**
 * @author Benedikt Wüller
 */
class MultiJumpListener : Listener {

    companion object {
        private const val VELOCITY_MULTIPLIER = 1.8
        private const val VELOCITY_HEIGHT = 1.1
    }

    @EventHandler
    private fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (player.hasPermission("cow.cowshed.multijump")) {
            player.state.canMultiJump = true
            player.allowFlight = true
        }
    }

    @EventHandler
    private fun onQuit(event: PlayerQuitEvent) {
        event.player.allowFlight = true
    }

    @EventHandler
    private fun onDoubleJump(event: PlayerToggleFlightEvent) {
        val player = event.player
        if (player.gameMode == GameMode.SPECTATOR || player.gameMode == GameMode.CREATIVE) return

        val state = player.state
        if (state.isFlying || state.isBuilding || !player.state.canMultiJump) return

        val vector = player.location.direction
        val velocity = vector.multiply(VELOCITY_MULTIPLIER).setY(VELOCITY_HEIGHT)

        player.velocity = velocity
        player.playSound(player.location, Sound.ENTITY_LLAMA_SPIT, 3.0F, 1.0F)

        event.isCancelled = true
    }

}
