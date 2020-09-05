package com.github.srain3

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.ItemStack

object JoinOpenBook : Listener{
    private lateinit var book: ItemStack
    fun getBook(main: Main){
        book = main.i
    }
    @EventHandler
    fun onJoinEvent(event: PlayerJoinEvent) {
        event.player.openBook(book)
    }
}