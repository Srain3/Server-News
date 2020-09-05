package com.github.srain3

import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.context.ContextManager
import net.luckperms.api.query.QueryOptions
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    lateinit var i: ItemStack

    override fun onEnable() {
        this.saveDefaultConfig()
        config
        i = config.getItemStack("data")!!
        server.pluginManager.registerEvents(JoinOpenBook, this)
        JoinOpenBook.getBook(this)
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            val luckperms = LuckPermsProvider.get()
            fun hasPerm(p: Player, permission: String): Boolean {
                if (!p.isOnline) throw IllegalArgumentException("Player is Offile")
                val user = luckperms.userManager.getUser(p.uniqueId)!!
                val contextManager: ContextManager = luckperms.contextManager
                val contextSet = contextManager.getContext(user).orElseGet { contextManager.staticContext }
                val permissionData = user.cachedData.getPermissionData(QueryOptions.contextual(contextSet))
                return permissionData.checkPermission(permission).asBoolean()
            }
            if(args.isNotEmpty()) {
                    if (args[0].equals("add")) {
                        if(hasPerm(p = sender,permission = cmd.permission.toString()+".add")) {
                            addItem(player = sender)
                        } else {
                            sender.sendMessage("オプションが違います")
                            return false
                        }
                    } else {
                        sender.sendMessage("オプションが違います")
                        return false
                    }
                } else {
                if (hasPerm(p = sender, permission = cmd.permission.toString())) {
                    setItem(player = sender)
                }
            }
                return true

        } else{
            sender.sendMessage("プレイヤーのみ実行可能です")
        }
        return false
    }

    private fun addItem(player: Player) {
        i = player.inventory.itemInMainHand
        if(i.type.toString().equals("WRITTEN_BOOK")) {
            config.set("data", i)
            saveConfig()
            player.sendMessage("お知らせを上書きしました")
            JoinOpenBook.getBook(this)
        } else {
            i = config.getItemStack("data")!!
            player.sendMessage("本じゃないアイテムです！上書きを中止しました")
        }
    }
    private fun setItem(player: Player) {
        player.openBook(i)
    }

    override fun onDisable() {
    }
}