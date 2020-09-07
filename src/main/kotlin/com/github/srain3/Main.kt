package com.github.srain3

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    //変数作成
    lateinit var mainbook: ItemStack
    var mainver = 0

    override fun onEnable() {
        saveDefaultConfig() //configがなければ作成
        config //config読み込み
        mainbook = config.getItemStack("main")!! //mainのItemStackデータを読み込み
        mainver = config.getInt("mainversion") //mainversionのIntデータを読み込み
        server.pluginManager.registerEvents(JoinOpenBook,this) //イベント登録(JoinEventで本を開く)
        JoinOpenBook.joinEv(this) //JoinOpenBook内のfun joinEvにmainを渡す
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // oyasainewsコマンドを受け取ったら
        if(sender is Player){ // コマンド送信元はPlayerか？
            // コマンド送信元がPlayerの場合
            if(args.isNullOrEmpty()){ // oyasainewsの後に引数は？
                // 引数がない場合
                sender.openBook(mainbook) // メインの本を開く
                if(config.getInt("playerdata.${sender.name}") != mainver){ // ログイン時に開いたverと違うか？
                    // 違う場合configへ保存
                    config.set("playerdata.${sender.name}", mainver) ; saveConfig()
                } //同じ場合スルー
                return true
            } //引数がある場合
            if(args[0].equals("add")){ // oyasainews addである場合
                val item = sender.inventory.itemInMainHand // メインハンドのアイテム入手
                if(item.type == Material.WRITTEN_BOOK){ //アイテムは記入済みの本？
                    //記入済みの本だった場合
                    if(args.getOrNull(1)!=null){ // oyasainews addの後に引数は？
                        //引数がある場合
                        config.set(args[1],item) ; saveConfig() // configへセーブ
                        sender.sendMessage("${args[1]}を上書きしました")
                        if(args[1].equals("main")){ // oyasainews add mainか？
                            // mainである場合、関数mainbookを更新&mainversionカウントを増やす&configへ保存
                            mainbook = item
                            mainver += 1
                            config.set("mainversion",mainver) ; saveConfig()
                        } //mainじゃない場合スルー
                        return true
                    } //引数がない場合
                    sender.sendMessage("/$label add [BookName]\nBookNameの部分が足りません！")
                    return true
                } //記入済みの本以外の場合
                sender.sendMessage("記入済みの本を持って登録して下さい！")
                return true
            }
            if(args[0].equals("open")){ // oyasainews openである場合
                if(args.getOrNull(1)!=null){ // oyasainews openの後に引数は？
                    //引数がある場合
                    val book = config.getItemStack(args[1])
                    if(book != null){ // [BookName]にデータはあるか？
                        // データが有る場合
                        sender.openBook(book)
                        if(args[1].equals("main")){ // oyasainews open mainであるか？
                            //mainだった場合、verの違いはあるか？
                            if(config.getInt("playerdata.${sender.name}") != mainver){
                                //verが違う場合、現在のverをconfigへ保存
                                config.set("playerdata.${sender.name}", mainver) ; saveConfig()
                            }//verが同じ場合スルー
                        }
                        return true
                    } // データがない場合
                    sender.sendMessage("${args[1]}は存在しません！")
                    return true
                } //引数がない場合
                sender.sendMessage("/$label open [BookName]\nBookNameの部分が足りません！")
                return true
            }
            if(args[0].equals("get")){ // oyasainews getである場合
                if(args.getOrNull(1)!=null){ // oyasainews getの後に引数は？
                    //引数がある場合
                    val book = config.getItemStack(args[1])
                    if(book != null){ // [BookName]にデータはあるか？
                        val bookmeta = book.itemMeta
                        val editbook = ItemStack(Material.WRITABLE_BOOK)
                        editbook.itemMeta = bookmeta
                        sender.inventory.setItemInMainHand(editbook)
                        sender.sendMessage("${args[1]}を入手しました")
                        return true
                    } // データがない場合
                    sender.sendMessage("${args[1]}は存在しません！")
                    return true
                } //引数がない場合
                sender.sendMessage("/$label get [BookName]\nBookNameの部分が足りません！")
                return true
            } // 引数がヒットしない場合
            sender.sendMessage("無効なオプションです")
            return true
        } // コマンド送信元がPlayerじゃない場合
        sender.sendMessage("プレイヤーのみ実行可能です")
        return false
    }

    override fun onDisable() {
    }
}