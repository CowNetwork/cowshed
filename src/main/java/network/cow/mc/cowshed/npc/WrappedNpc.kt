package network.cow.mc.cowshed.npc

import com.github.juliarn.npc.NPC
import com.github.juliarn.npc.modifier.LabyModModifier
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player

class WrappedNpc(val handle: NPC, val lines: List<ArmorStand>) {

    fun doEmote(player: Player, emoteId: Int) {
        this.handle.labymod().queue(LabyModModifier.LabyModAction.EMOTE, emoteId).send(player)
    }
}