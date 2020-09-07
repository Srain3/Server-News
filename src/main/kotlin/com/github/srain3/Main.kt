package com.github.srain3

import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.context.ContextManager
import net.luckperms.api.query.QueryOptions
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
            val luckperms = LuckPermsProvider.get()
            fun hasPerm(p: Player, permission: String): Boolean {
                if (!p.isOnline) throw IllegalArgumentException("Player is Offile")
                val user = luckperms.userManager.getUser(p.uniqueId)!!
                val contextManager: ContextManager = luckperms.contextManager
                val contextSet = contextManager.getContext(user).orElseGet { contextManager.staticContext }
                val permissionData = user.cachedData.getPermissionData(QueryOptions.contextual(contextSet))
                return permissionData.checkPermission(permission).asBoolean()
            }
            if(args.isNullOrEmpty()){ // oyasainewsの後に引数は？
                // 引数がない場合
                if(hasPerm(sender,command.permission.toString()+".open")) { //open権限はあるか？
                    sender.openBook(mainbook) // 権限があればメインの本を開く
                    if (config.getInt("playerdata.${sender.name}") != mainver) { // ログイン時に開いたverと違うか？
                        // 違う場合configへ保存
                        config.set("playerdata.${sender.name}", mainver); saveConfig()
                    } //同じ場合スルー
                    return true
                } // open権限がない場合
                sender.sendMessage("権限がありません！")
                return true
            } //引数がある場合
            when(args[0]){
                "open" -> { // oyasainews openである場合
                    if (hasPerm(sender, command.permission.toString() + ".open")) { //open権限はあるか？
                        if (args.getOrNull(1) != null) { // oyasainews openの後に引数は？
                            //引数がある場合
                            val book = config.getItemStack(args[1])
                            if (book != null) { // [BookName]にデータはあるか？
                                // データが有る場合
                                sender.openBook(book) //権限がある場合本を開く
                                if (args[1].equals("main")) { // oyasainews open mainであるか？
                                    //mainだった場合、verの違いはあるか？
                                    if (config.getInt("playerdata.${sender.name}") != mainver) {
                                        //verが違う場合、現在のverをconfigへ保存
                                        config.set("playerdata.${sender.name}", mainver); saveConfig()
                                    }//verが同じ場合スルー
                                } //mainではない場合スルー
                                return true
                            } // データがない場合
                            sender.sendMessage("${args[1]}は存在しません！")
                            return true
                        } //引数がない場合
                        sender.sendMessage("/$label open [BookName]\nBookNameの部分が足りません！")
                        return true
                    } //open権限がない場合
                    sender.sendMessage("権限がありません！")
                    return true
                }
                "add" -> { // oyasainews addである場合
                    if(hasPerm(sender,command.permission.toString()+".add")) { //add権限はあるか？
                        val item = sender.inventory.itemInMainHand // 権限があればメインハンドのアイテム入手
                        if (item.type == Material.WRITTEN_BOOK) { //アイテムは記入済みの本？
                            //記入済みの本だった場合
                            if (args.getOrNull(1) != null) { // oyasainews addの後に引数は？
                                //引数がある場合
                                config.set(args[1], item); saveConfig() // configへセーブ
                                sender.sendMessage("${args[1]}を上書きしました")
                                if (args[1].equals("main")) { // oyasainews add mainか？
                                    // mainである場合、関数mainbookを更新&mainversionカウントを増やす&configへ保存
                                    mainbook = item
                                    mainver += 1
                                    config.set("mainversion", mainver); saveConfig()
                                } //mainじゃない場合スルー
                                return true
                            } //引数がない場合
                            sender.sendMessage("/$label add [BookName]\nBookNameの部分が足りません！")
                            return true
                        } //記入済みの本以外の場合
                        sender.sendMessage("記入済みの本を持って登録して下さい！")
                        return true
                    } //権限がない場合
                    sender.sendMessage("権限がありません！")
                    return true
                }
                "get" -> { // oyasainews getである場合
                    if(hasPerm(sender,command.permission.toString()+".get")) { //get権限はあるか？
                        if (args.getOrNull(1) != null) { // 権限があって、oyasainews getの後に引数は？
                            //引数がある場合
                            val book = config.getItemStack(args[1])
                            if (book != null) { // [BookName]にデータはあるか？
                                //データが有る場合
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
                    } //get権限がない場合
                    sender.sendMessage("権限がありません！")
                    return true
                }
                "remove" -> { // oyasainews removeの場合
                    if(hasPerm(sender,command.permission.toString()+".remove")) { //remove権限はあるか？
                        if (args.getOrNull(1) != null) { // 権限があって、removeの後に引数は？
                            //引数がある場合、それはmainであるか？
                            if (args[1].equals("main")) {
                                //mainの場合は警告してキャンセルする
                                sender.sendMessage("mainは消せません！")
                                return true
                            } //mainではない場合
                            val book = config.getItemStack(args[1])
                            if (book != null) { // [BookName]にデータはあるか？
                                config.set(args[1], "${sender.name} is remove"); saveConfig()
                                sender.sendMessage("${args[1]}を消去しました")
                                return true
                            } //データがない場合
                            sender.sendMessage("${args[1]}は存在しないため消去できません")
                            return true
                        }//引数がない場合
                        sender.sendMessage("/${label} remove [BookName]\nBookNameの部分が足りません！")
                        return true
                    } //remove権限がない場合
                    sender.sendMessage("権限がありません！")
                    return true
                }
                else -> { //引数がヒットしない場合
                    sender.sendMessage("無効なオプションです")
                    return true
                }
            }
        } // コマンド送信元がPlayerじゃない場合
        sender.sendMessage("プレイヤーのみ実行可能です")
        return false
    }

    override fun onDisable() {
    }
}