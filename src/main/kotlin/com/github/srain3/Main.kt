@file:Suppress("DEPRECATION")

package com.github.srain3

import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.context.ContextManager
import net.luckperms.api.query.QueryOptions
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import org.geysermc.connector.GeyserConnector

class Main : JavaPlugin() {
    //変数作成
    var mainver = 0
    var bookcash = mutableMapOf<String,ItemStack>()
    private lateinit var booklistnomal : List<String>
    private lateinit var booklistnotmask : List<String>

    override fun onEnable() {
        saveDefaultConfig() //configがなければ作成
        config //config読み込み
        bookcash = ConvertAddBooks.addbooks(bookcash,this)
        mainver = config.getInt("mainversion") //mainversionのIntデータを読み込み
        server.pluginManager.registerEvents(JoinOpenBook, this) //イベント登録(JoinEventで本を開く)
        JoinOpenBook.joinEv(this) //JoinOpenBook内のfun joinEvにmainを渡す
        reloadbooklists()
    }

    private fun reloadbooklists(){
        val booklist0 = config.getKeys(false)
        val booklist1 = booklist0.filterNot { it == "mainversion" }//いらないmainversionを除外
        val booklist2 = booklist1.filterNot { it == "spawnlocation" } //いらないspawnlocationを除外
        val booklist3 = booklist2.filterNot { it == "notmask" }
        //チュートリアル対応コード
        val booklist4 = booklist3.filterNot { it == "tutolocation"}
        val booklist5 = booklist4.filterNot { it == "tutolook" }
        //チュートリアル対応コードend
        booklistnotmask = booklist5.filterNot { it == "playerdata" } //いらないplayerdataを除外
        booklistnomal = booklistnotmask.toString().replace("""(, )?#[^#]*#""".toRegex(), "").removePrefix("[").removeSuffix("]").split(", ")
        return
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (sender is Player) {
            if (command.name != "oyasainews") return super.onTabComplete(sender, command, alias, args)
            if (args.size == 1) {
                if (args[0].isEmpty()) {
                    return Arrays.asList("open","booklist","color")
                } else {
                    //入力されている文字列と先頭一致
                    when (args[0].isNotEmpty()) {
                        "open".startsWith(args[0]) -> return Arrays.asList("open")
                        "booklist".startsWith(args[0]) -> return Arrays.asList("booklist")
                        "color".startsWith(args[0]) -> return Arrays.asList("color")
                        "add".startsWith(args[0]) -> return Arrays.asList("add")
                        "get".startsWith(args[0]) -> return Arrays.asList("get")
                        "remove".startsWith(args[0]) -> return Arrays.asList("remove")
                        else -> {
                            //JavaPlugin#onTabComplete()を呼び出す
                            return super.onTabComplete(sender, command, alias, args)
                        }
                    }
                }
            }
            if (args.size == 2){
                if (config.getBoolean("notmask.${sender.name}", false)) {
                    if (args[1].isNotEmpty()) {
                        val size = booklistnotmask.size.minus(1)
                        val books = mutableListOf<String>()
                        for (i in 0..size) {
                            if (booklistnotmask[i].startsWith(args[1])) {
                                books.add(booklistnotmask[i])
                            }
                        }
                        return books.toMutableList()
                    }
                    return booklistnotmask.toMutableList()
                }
                if (args[1].isNotEmpty()) {
                    val size = booklistnomal.size.minus(1)
                    val books = mutableListOf<String>()
                    for (i in 0..size) {
                        if (booklistnomal[i].startsWith(args[1])) {
                            books.add(booklistnomal[i])
                        }
                    }
                    return books.toMutableList()
                }
                return booklistnomal.toMutableList()
            }
        }
        return null
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        // oyasainewsコマンドを受け取ったら
        if (sender is Player) { // コマンド送信元はPlayerか？
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
                if (args.isEmpty()) { // oyasainewsの後に引数は？
                    // 引数がない場合
                    if (hasPerm(sender, command.permission.toString() + ".open")) { //open権限はあるか？
                        if (GeyserConnector.getInstance().getPlayerByUuid(sender.uniqueId) == null) { //BE版ならfalse、Javaならnullでtrue
                            bookcash["main"]?.let { sender.openBook(it) } // 権限があればメインの本を開く
                            if (config.getInt("playerdata.${sender.name}") != mainver) { // ログイン時に開いたverと違うか？
                                // 違う場合configへ保存
                                config.set("playerdata.${sender.name}", mainver); saveConfig()
                            } //同じ場合スルー
                            return true
                        } else { //BE版の処理
                            if (sender.inventory.itemInMainHand.amount == 0) {
                                sender.inventory.setItemInMainHand(bookcash["main"])
                                sender.sendMessage("bookを付与しました")
                                return true
                            } //手になにか持ってる場合
                            sender.sendMessage("何も手に持たない状態で/onewsをしてください。")
                            return true
                        }
                    } // open権限がない場合
                    sender.sendMessage("権限がありません！")
                    return true
                } //引数がある場合
                when (args[0]) {
                    "open" -> { // oyasainews openである場合
                        if (hasPerm(sender, command.permission.toString() + ".open")) { //open権限はあるか？
                            if (args.getOrNull(1) != null) { // oyasainews openの後に引数は？
                                //引数がある場合
                                val bookmask: String
                                if (config.getBoolean("notmask.${sender.name}", false)) {
                                    bookmask = args[1]
                                } else {
                                    bookmask = args[1].replace("""(, )?#[^#]*#""".toRegex(), "")
                                }
                                val book = bookcash[bookmask]
                                if (book != null) { // bookcashにデータはあるか？
                                    // データが有る場合
                                    if (GeyserConnector.getInstance().getPlayerByUuid(sender.uniqueId) == null) { //BE版ならfalse、Javaならnullでtrue
                                        sender.openBook(book) //権限がある場合本を開く
                                        if (args[1] == "main") { // oyasainews open mainであるか？
                                            //mainだった場合、verの違いはあるか？
                                            if (config.getInt("playerdata.${sender.name}") != mainver) {
                                                //verが違う場合、現在のverをconfigへ保存
                                                config.set("playerdata.${sender.name}", mainver); saveConfig()
                                            }//verが同じ場合スルー
                                        } //mainではない場合スルー
                                        return true
                                    }else { //BE版の処理
                                        if (sender.inventory.itemInMainHand.amount == 0) {
                                            sender.inventory.setItemInMainHand(bookcash[args[1]])
                                            sender.sendMessage("bookを付与しました")
                                            return true
                                        } //手になにか持ってる場合
                                        sender.sendMessage("何も手に持たない状態で/onewsをしてください。")
                                        return true
                                    }
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
                    "booklist" -> { // oyasainews booklistである場合
                        if (hasPerm(sender, command.permission.toString() + ".booklist")) { //booklist権限は？
                            if (config.getBoolean("notmask.${sender.name}", false)) {
                                sender.sendMessage(booklistnotmask.toString())
                                return true
                            }
                            sender.sendMessage(booklistnomal.toString())
                            return true
                        } // 権限がない場合
                        sender.sendMessage("権限がありません！")
                        return true
                    }
                    "color" -> { // oyasainews color
                        if (GeyserConnector.getInstance().getPlayerByUuid(sender.uniqueId) == null) { //BE版ならfalse、Javaならnullでtrue
                            if (hasPerm(sender, command.permission.toString() + ".color")) { //color権限
                                val oldbook = sender.inventory.itemInMainHand
                                if (oldbook.type == Material.WRITABLE_BOOK) { //本と羽根ペン？
                                    val book: BookMeta = oldbook.itemMeta as BookMeta
                                    val newbmeta = ChatColor.translateAlternateColorCodes(
                                        '$',
                                        book.pages.toString().removePrefix("[").removeSuffix("]")
                                    )
                                    val newbook0 = newbmeta.split(", ").toList()
                                    book.pages = newbook0
                                    oldbook.itemMeta = book
                                    sender.inventory.setItemInMainHand(oldbook)
                                    sender.sendMessage("変換しました！")
                                    return true
                                } //アイテム違い
                                sender.sendMessage("非対応アイテムです")
                                return true
                            } //権限なし
                            sender.sendMessage("権限がありません！")
                            return true
                        } else { //BEでは使えないようにする(color)
                            sender.sendMessage("BE版ではcolorは使えません。")
                            return true
                        }
                    }
                    "add" -> { // oyasainews addである場合
                        if (GeyserConnector.getInstance().getPlayerByUuid(sender.uniqueId) == null) { //BE版ならfalse、Javaならnullでtrue
                            if (hasPerm(sender, command.permission.toString() + ".add")) { //add権限はあるか？
                                if (args.getOrNull(1) != null) {
                                    val item = sender.inventory.itemInMainHand // 権限があればメインハンドのアイテム入手
                                    if (item.type == Material.WRITTEN_BOOK) { //アイテムは記入済みの本？
                                        config.set(args[1], item); saveConfig()
                                        val olditem = item.clone()
                                        val newbook = ConvertAddBooks.convertbooks(olditem)
                                        when (args[1]) {
                                            "mainversion" -> {
                                                sender.sendMessage("mainversionは変更できません！");return true
                                            }
                                            "spawnlocation" -> {
                                                sender.sendMessage("spawnlocationは変更できません！");return true
                                            }
                                            "playerdata" -> {
                                                sender.sendMessage("playerdataは変更できません！");return true
                                            }
                                            else -> {
                                            }
                                        }
                                        bookcash[args[1]] = newbook
                                        sender.inventory.setItemInOffHand(newbook)
                                        sender.sendMessage("${args[1]}を上書きしました")
                                        reloadbooklists()
                                        if (args[1] == "main") {
                                            mainver += 1
                                            config.set("mainversion", mainver); saveConfig()
                                        }
                                        return true
                                    }
                                    sender.sendMessage("非対応アイテムです")
                                    return true
                                }
                            }
                            sender.sendMessage("権限がありません！")
                            return true
                        } else { //BE版ではつかわせない(add)
                            sender.sendMessage("BE版ではaddは使えません。")
                            return true
                        }
                    }
                    "get" -> { // oyasainews getである場合
                        if (GeyserConnector.getInstance().getPlayerByUuid(sender.uniqueId) == null) { //BE版ならfalse、Javaならnullでtrue
                            if (hasPerm(sender, command.permission.toString() + ".get")) { //get権限はあるか？
                                if (args.getOrNull(1) != null) { // 権限があって、oyasainews getの後に引数は？
                                    //引数がある場合
                                    val book = config.getItemStack(args[1])
                                    if (book != null) { // bookcashにデータはあるか？
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
                        } else { //BE版では使わせない(get)
                            sender.sendMessage("BE版ではgetは使えません。")
                            return true
                        }
                    }
                    "remove" -> { // oyasainews removeの場合
                        if (hasPerm(sender, command.permission.toString() + ".remove")) { //remove権限はあるか？
                            if (args.getOrNull(1) != null) { // 権限があって、removeの後に引数は？
                                when (args[1]) {
                                    "main" -> {
                                        sender.sendMessage("mainは消去できません！"); return true
                                    }
                                    "mainversion" -> {
                                        sender.sendMessage("mainversionは消去できません！"); return true
                                    }
                                    "spawnlocation" -> {
                                        sender.sendMessage("spawnlocationは消去できません！"); return true
                                    }
                                    "playerdata" -> {
                                        sender.sendMessage("playerdataは消去できません！"); return true
                                    }
                                }
                                val book = config.getItemStack(args[1])
                                if (book != null) { // [BookName]にデータはあるか？
                                    bookcash.minus(args[1])
                                    config.set(args[1], null); saveConfig()
                                    sender.sendMessage("${args[1]}を消去しました")
                                    reloadbooklists()
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
                    "notmask" -> {
                        if (hasPerm(sender, command.permission.toString() + ".admin")) {
                            if (args.size == 3) {
                                when (args[2]) {
                                    "false" -> {
                                        config.set("notmask.${args[1]}", false); saveConfig()
                                        sender.sendMessage("${args[1]}のnotmaskをfalseにしました(#...#を非表示にする)")
                                        return true
                                    }
                                    "true" -> {
                                        config.set("notmask.${args[1]}", true); saveConfig()
                                        sender.sendMessage("${args[1]}のnotmaskをtrueにしました(#...#を表示する)")
                                        return true
                                    }
                                    else -> {
                                        sender.sendMessage("true もしくは false を指定して下さい")
                                        return true
                                    }
                                }
                            }
                            sender.sendMessage("引数不足です！")
                            return true
                        }
                        sender.sendMessage("権限がありません！")
                        return true
                    }
                    "setspawn" -> {
                        if (hasPerm(sender, command.permission.toString() + ".setspawn")) { //setspawn権限はあるか？
                            config.set("spawnlocation", sender.location); saveConfig()
                            sender.sendMessage("spawnのlocationを保存しました！")
                            return true
                        } //setspawn権限がない場合
                        sender.sendMessage("権限がありません！")
                        return true
                    }
                    //チュートリアル対応コード
                    "settuto" -> {
                        if (hasPerm(sender, command.permission.toString() + ".setspawn")) { //setspawn権限流用
                            config.set("tutolocation", sender.location); saveConfig()
                            sender.sendMessage("tutoのlocationを保存しました！")
                            return true
                        }
                        sender.sendMessage("権限がありません！")
                        return true
                    } //チュートリアル対応コードend
                }
                sender.sendMessage("無効なオプションです")
                return true
        } //プレイヤー以外の送信なら
        sender.sendMessage("Not Player!!!")
        return true
    }

    override fun onDisable() {
    }
}