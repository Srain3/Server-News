package com.github.srain3

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object JoinOpenBook: Listener {
    private lateinit var main1: Main //この中でMainクラスを使うための(空の)変数設定
    fun joinEv(main: Main){ //Mainから読み出されてMainクラスを参照する箱
        main1 = main //main1にMainを入れる
        return
    }
    @EventHandler
    fun onJoinEvent(e: PlayerJoinEvent) { //プレイヤーjoin時に動く
        //config内のプレイヤーの読んだmainbookのverと現在のmainbookのverが一致するか？データがない場合は0を返す
        if(main1.config.getInt("playerdata.${e.player.name}", 0) != main1.mainver) {
            //verが違う場合(読んでいない場合)
            e.player.openBook(main1.mainbook) //メイン本を開く
            main1.config.set("playerdata.${e.player.name}",main1.mainver) ; main1.saveConfig() // configへセーブ
        }//verが同じ場合(読んでいる場合)
        return
    }
}