package network.cow.mc.cowshed.listener

import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import network.cow.mc.cowshed.NpcRegistry
import network.cow.messages.adventure.error
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class NpcListener : Listener{

    @EventHandler
    private fun onNPCInteract(event: PlayerNPCInteractEvent) {
        if (event.npc.profile.name == "Gustav") return // FIXME: temp
        event.player.sendMessage(":(".error())
    }
}