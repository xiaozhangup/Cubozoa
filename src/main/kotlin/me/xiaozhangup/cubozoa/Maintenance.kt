package me.xiaozhangup.cubozoa

import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.server.ServerPing
import me.xiaozhangup.cubozoa.Cubozoa.plugin
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.info

@Awake(LifeCycle.ENABLE)
object Maintenance {
    
    var maintenance: Boolean = false
    private var note: String = ""
    private val motd: Component = Component.text(
        "HAPPYLAND 团队正在对服务器进行维护操作", TextColor.color(221, 72, 43), TextDecoration.BOLD
    )
    private val dis: Component = Component.text(
        "请等待进一步的通知", TextColor.color(161, 161, 159)
    )

    @Awake(LifeCycle.ENABLE)
    fun regCommand() {
        command("maintenance") {
            dynamic(optional = true) {
                execute<ProxyCommandSender> { sender, _, arg ->
                    if (!sender.hasPermission("maintenance.control")) return@execute
                    maintenance = !maintenance
                    note = arg
                    if (maintenance) {
                        for (player in plugin.server.allPlayers) {
                            player.dis()
                        }
                    }
                    sender.sendMessage("维护模式已经变更为 $maintenance")
                }
            }

            execute<ProxyCommandSender> { sender, _, arg ->
                if (!sender.hasPermission("maintenance.control")) return@execute
                maintenance = !maintenance
                note = arg
                if (maintenance) {
                    for (player in plugin.server.allPlayers) {
                        player.dis()
                    }
                }
                sender.sendMessage("维护模式已经变更为 $maintenance")
            }
        }
    }

    @SubscribeEvent
    fun e(e: ProxyPingEvent) {
        if (maintenance) {
            val ping = e.ping.asBuilder()

            ping.maximumPlayers(-1)
            ping.onlinePlayers(-1)
            ping.version(ServerPing.Version(-1, "维护中"))
            ping.description(
                motd
                    .append(Component.newline())
                    .append(
                        Component.text(
                            note, TextColor.color(161, 161, 159)
                    ))
            )

            e.ping = ping.build()
        }
    }

    @SubscribeEvent
    fun e(e: PlayerChooseInitialServerEvent) {
        if (maintenance) {
            e.player.dis()
        }
    }

    private fun Player.dis() {
        this.disconnect(
            motd.append(Component.newline())
                .append(dis)
        )
    }
}