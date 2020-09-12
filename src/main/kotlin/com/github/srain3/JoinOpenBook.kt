<<<<<<< Updated upstream
package com.github.srain3

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object JoinOpenBook: Listener {
    private lateinit var main1: Main //この中でMainクラスを使うための(空の)変数設定
    fun joinEv(main: Main){ //Mainから読み出されてMainクラスを参照する箱
        main1 = main //main1にMainを入れる
        return
    }
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onJoinEvent(e: PlayerJoinEvent) { //プレイヤーjoin時に動く
        if(main1.config.get("spawnlocation",false) == false){ //spawnlocationがない場合
            main1.config.set("spawnlocation",e.player.location) ; main1.saveConfig() //configにjoinしたプレイヤーのlocation登録して処理を止める
            return
        } //ある場合スルー
        /*
        動作テスト用
         */
        //e.player.sendMessage("あなたのWorld名は「${e.player.world.name}」で設定のSpawnWorld名は${main1.config.getLocation("spawnlocation")?.world?.name}です")
        if (e.player.world.name != main1.config.getLocation("spawnlocation")?.world?.name) { //worldはスポーンするワールドと一緒か？
            //一緒じゃない場合
            //config内のプレイヤーの読んだmainbookのverと現在のmainbookのverが一致するか？データがない場合は0を返す
            if (main1.config.getInt("playerdata.${e.player.name}", 0) != main1.mainver) {
                //verが違う場合(読んでいない場合)
                e.player.openBook(main1.mainbook) //メイン本を開く
                main1.config.set("playerdata.${e.player.name}", main1.mainver); main1.saveConfig() // configへセーブ
            }//verが同じ場合(読んでいる場合)
            return
        } //一緒の場合は座標で検知する(完全一致するとxzが0に)
        val px = e.player.location.x.toInt() ; val pz = e.player.location.z.toInt()
        val spawnx = main1.config.getLocation("spawnlocation")?.x?.toInt() ?: return
        val spawnz = main1.config.getLocation("spawnlocation")?.z?.toInt() ?: return
        val xz = px.minus(spawnx).plus(pz).minus(spawnz)
        /*
        動作テスト用
         */
        //e.player.sendMessage("あなたの座標はx${px},z${pz}でspawnの座標はx${spawnx},z${spawnz}で、結果は${xz}でした")
        if (xz == 0){ //完全一致(0)の場合何もしないで終える
            return
        }//一致しない(0以外)の場合
        //config内のプレイヤーの読んだmainbookのverと現在のmainbookのverが一致するか？データがない場合は0を返す
        if (main1.config.getInt("playerdata.${e.player.name}", 0) != main1.mainver) {
            //verが違う場合(読んでいない場合)
            e.player.openBook(main1.mainbook) //メイン本を開く
            main1.config.set("playerdata.${e.player.name}", main1.mainver); main1.saveConfig() // configへセーブ
        }//verが同じ場合(読んでいる場合)
        return
    }
=======
package com.github.srain3

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

object JoinOpenBook: Listener {
    private lateinit var main1: Main //この中でMainクラスを使うための(空の)変数設定
    fun joinEv(main: Main){ //Mainから読み出されてMainクラスを参照する箱
        main1 = main //main1にMainを入れる
        return
    }
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onJoinEvent(e: PlayerJoinEvent) { //プレイヤーjoin時に動く
        if(main1.config.get("spawnlocation",false) == false){ //spawnlocationがない場合
            main1.config.set("spawnlocation",e.player.location) ; main1.saveConfig() //configにjoinしたプレイヤーのlocation登録して処理を止める
            return
        } //ある場合スルー
        /*
        動作テスト用
         */
        //e.player.sendMessage("あなたのWorld名は「${e.player.world.name}」で設定のSpawnWorld名は${main1.config.getLocation("spawnlocation")?.world?.name}です")
        if (e.player.world.name != main1.config.getLocation("spawnlocation")?.world?.name) { //worldはスポーンするワールドと一緒か？
            //一緒じゃない場合
            //config内のプレイヤーの読んだmainbookのverと現在のmainbookのverが一致するか？データがない場合は0を返す
            if (main1.config.getInt("playerdata.${e.player.name}", 0) != main1.mainver) {
                //verが違う場合(読んでいない場合)
                main1.bookcash["main"]?.let { e.player.openBook(it) } //メイン本を開く
                main1.config.set("playerdata.${e.player.name}", main1.mainver); main1.saveConfig() // configへセーブ
            }//verが同じ場合(読んでいる場合)
            return
        } //一緒の場合は座標で検知する(完全一致するとxzが0に)
        val px = e.player.location.x.toInt() ; val pz = e.player.location.z.toInt()
        val spawnx = main1.config.getLocation("spawnlocation")?.x?.toInt() ?: return
        val spawnz = main1.config.getLocation("spawnlocation")?.z?.toInt() ?: return
        val xz = px.minus(spawnx).plus(pz).minus(spawnz)
        /*
        動作テスト用
         */
        //e.player.sendMessage("あなたの座標はx${px},z${pz}でspawnの座標はx${spawnx},z${spawnz}で、結果は${xz}でした")
        if (xz == 0){ //完全一致(0)の場合何もしないで終える
            return
        }//一致しない(0以外)の場合
        //config内のプレイヤーの読んだmainbookのverと現在のmainbookのverが一致するか？データがない場合は0を返す
        if (main1.config.getInt("playerdata.${e.player.name}", 0) != main1.mainver) {
            //verが違う場合(読んでいない場合)
            main1.bookcash["main"]?.let { e.player.openBook(it) } //メイン本を開く
            main1.config.set("playerdata.${e.player.name}", main1.mainver); main1.saveConfig() // configへセーブ
        }//verが同じ場合(読んでいる場合)
        return
    }
>>>>>>> Stashed changes
}