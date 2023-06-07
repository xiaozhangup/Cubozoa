package me.xiaozhangup.cubozoa

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.ConnectionHandshakeEvent
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.ServerPing
import com.velocitypowered.api.util.Favicon
import me.xiaozhangup.cubozoa.Maintenance.maintenance
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Plugin
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.common.platform.function.info
import taboolib.common5.cbyte
import taboolib.platform.VelocityPlugin
import java.nio.charset.StandardCharsets


object Cubozoa : Plugin() {

    val plugin by lazy { VelocityPlugin.getInstance() }

    override fun onEnable() {
        info("Maintenance mode: $maintenance")
    }

}