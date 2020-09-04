package com.github.srain3

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private lateinit var i: ItemStack

    override fun onEnable() {
        this.saveDefaultConfig()
        config
        i = config.getItemStack("data")!!
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            if(args.isNotEmpty()) {
                if(args[0].equals("add")) {
                    addItem(player = sender)
                    sender.sendMessage("お知らせを上書きしました")
                    } else {
                    sender.sendMessage("オプションが違います")
                    return false
                    }
                } else {
                setItem(player = sender)
                }
                return true
        } else{
            sender.sendMessage("プレイヤーのみ実行可能です")
        }
        return false
    }

    private fun addItem(player: Player) {
        i = player.inventory.itemInMainHand
        config.set("data", i)
        saveConfig()
    }
    private fun setItem(player: Player){
        val slot = player.inventory.itemInMainHand
        if(slot.type.isAir) {
            player.inventory.setItemInMainHand(i)
            player.sendMessage("お知らせ本を入手しました")
        } else {
            player.sendMessage("メインハンドが空いてません！入手を中止しました")
        }
    }

    override fun onDisable() {
    }
}