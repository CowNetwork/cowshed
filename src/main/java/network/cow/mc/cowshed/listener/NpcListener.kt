package network.cow.mc.cowshed.listener

import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import net.kyori.adventure.text.format.TextDecoration
import network.cow.messages.adventure.error
import network.cow.messages.adventure.highlight
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class NpcListener : Listener{

    @EventHandler
    private fun onNPCInteract(event: PlayerNPCInteractEvent) {
        if (event.npc.profile.name == "Gustav") {
            event.player.sendMessage("Gustav » Hallo, mein Name ist Gustav. Mir gehört der Laden.".highlight().decorate(TextDecoration.BOLD))
            return
        }
        event.player.sendMessage("» Schade :(".error().decorate(TextDecoration.BOLD))
    }
}