package me.xiaozhangup.cubozoa

import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.connection.PostLoginEvent
import me.xiaozhangup.cubozoa.Cubozoa.server
import net.kyori.adventure.text.minimessage.MiniMessage
import taboolib.common.platform.event.SubscribeEvent

object GlobalJoinQuit {

    private val miniMessage = MiniMessage.miniMessage()

    @SubscribeEvent
    fun on(e: PostLoginEvent) {
        val p = e.player
        if (p.currentServer.isPresent) return
        server.sendMessage(miniMessage.deserialize(
            "<dark_gray>[<color:#b5bfe2>玩家</color>]</dark_gray> <color:#c6d0f5>玩家 ${p.username} 加入了服务器</color>"
        ))
    }

    @SubscribeEvent
    fun on(e: DisconnectEvent) {
        val p = e.player
        if (
            e.loginStatus == DisconnectEvent.LoginStatus.CONFLICTING_LOGIN ||
            e.loginStatus == DisconnectEvent.LoginStatus.CANCELLED_BY_PROXY ||
            e.loginStatus == DisconnectEvent.LoginStatus.CANCELLED_BY_USER_BEFORE_COMPLETE
            ) return
        if (p.currentServer.isEmpty) return
        server.sendMessage(miniMessage.deserialize(
            "<dark_gray>[<color:#737994>玩家</color>]</dark_gray> <color:#949cbb>玩家 " + p.username + " 离开了服务器</color>"
        ))
    }

}