package network.cow.mc.cowshed

import network.cow.spigot.extensions.state.getState
import network.cow.spigot.extensions.state.setState
import org.bukkit.entity.Player

/**
 * @author Benedikt Wüller
 */
data class PlayerState(
        var isSonic: Boolean = false,
        var isFlying: Boolean = false,
        var isBuilding: Boolean = false,
)

private const val STATE_KEY = "cow.cowshed"

val Player.state: PlayerState; get() {
    var state = this.getState<PlayerState>(CowshedPlugin::class.java, STATE_KEY)
    if (state != null) return state
    state = PlayerState()
    this.setState(CowshedPlugin::class.java, STATE_KEY, state)
    return state
}
