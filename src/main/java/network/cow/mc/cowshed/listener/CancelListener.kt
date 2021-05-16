package network.cow.mc.cowshed.listener

import network.cow.mc.cowshed.state
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @author Benedikt Wüller
 */
class CancelListener : Listener {

    @EventHandler private fun onBlockPlace(event: BlockPlaceEvent) = event.tryCancel(event.player)
    @EventHandler private fun onBlockBreak(event: BlockBreakEvent) = event.tryCancel(event.player)

    @EventHandler private fun onInteract(event: PlayerInteractEvent) = event.tryCancel(event.player)
    @EventHandler private fun onInteract(event: PlayerInteractAtEntityEvent) = event.tryCancel(event.player)
    @EventHandler private fun onInteract(event: PlayerInteractEntityEvent) = event.tryCancel(event.player)

    @EventHandler private fun onDamage(event: EntityDamageEvent) = event.cancel()
    @EventHandler private fun onHunger(event: FoodLevelChangeEvent) = event.cancel()

    @EventHandler private fun onDrop(event: PlayerDropItemEvent) = event.tryCancel(event.player)

    @EventHandler
    private fun onInventoryMove(event: InventoryMoveItemEvent) {
        val player = event.initiator.holder
        if (player !is Player) return
        event.tryCancel(player)
    }

    @EventHandler
    private fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked
        if (player !is Player) return
        event.tryCancel(player)
    }

    private fun Cancellable.tryCancel(player: Player) {
        if (player.state.isBuilding) return
        this.isCancelled = true
    }

    private fun Cancellable.cancel() {
        this.isCancelled = true
    }

}
