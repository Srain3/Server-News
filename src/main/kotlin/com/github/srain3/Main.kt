package com.github.srain3

import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
    override fun onEnable() {
        getCommand("oyasainews")?.setExecutor(OyasainewsCommand)
    }
}