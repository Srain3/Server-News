package com.github.srain3

import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.context.ContextManager
import net.luckperms.api.query.QueryOptions
import net.md_5.bungee.api.chat.*
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Main : JavaPlugin() {
    //変数作成
    lateinit var mainbook: ItemStack
    var mainver = 0
<<<<<<< Updated upstream
    private var bookcash = mutableMapOf<String,ItemStack>()
=======
    var bookcash = mutableMapOf<String,ItemStack>()
>>>>>>> Stashed changes

    override fun onEnable() {
        saveDefaultConfig() //configがなければ作成
        config //config読み込み
        mainbook = config.getItemStack("main")!! //mainのItemStackデータを読み込み
        mainver = config.getInt("mainversion") //mainversionのIntデータを読み込み
        server.pluginManager.registerEvents(JoinOpenBook, this) //イベント登録(JoinEventで本を開く)
        JoinOpenBook.joinEv(this) //JoinOpenBook内のfun joinEvにmainを渡す
<<<<<<< Updated upstream
        val booklist0 = config.getKeys(false)
        val booklist1 = booklist0.filterNot { it == "mainversion" }//いらないmainversionを除外
        val booklist2 = booklist1.filterNot { it == "spawnlocation" } //いらないspawnlocationを除外
        val booklist3 = booklist2.filterNot { it == "playerdata" } //いらないplayerdataを除外
        for (i5 in 0..booklist3.size.minus(1)){
            val book00 = config.getItemStack(booklist3[i5])!!
            val book : BookMeta = book00.itemMeta as BookMeta
            val newbook0 = book.pages.toString().removePrefix("[").removeSuffix("]").split(", ")
            val url = """\[(§.)?http.*(§.)?]""".toRegex()
            val table = """\[.*]\[[0-9]*]""".toRegex()
            val cmd = """\{(§.)?/.*(§.)?}""".toRegex()
            val urlandtable = """(\[(§.)?http.*(§.)?]|\[.*]\[[0-9]*]|\{(§.)?/.*(§.)?})""".toRegex()
            var book5 = arrayOf<BaseComponent>()
            for (i in 0..newbook0.size.minus(1)) {
                val book3 = newbook0[i].split(urlandtable)
                val book4 = url.findAll(newbook0[i]).toList()
                val book41 = table.findAll(newbook0[i]).toList()
                val book42 = cmd.findAll(newbook0[i]).toList()
                if (book41.size == 0) {
                    if (book42.size == 0) {
                        if (book3.size == book4.size) {
                            val book8: BaseComponent = TextComponent()
                            for (i2 in 0..book3.size.minus(1)) {
                                val book9 = book4[i2].value.removePrefix("[").removeSuffix("]")
                                val book6 = ComponentBuilder(book3[i2]).currentComponent
                                val book7 =
                                    ComponentBuilder("[${book9}]").event(ClickEvent(ClickEvent.Action.OPEN_URL,
                                        book9.replace("""§.""".toRegex(), "")))
                                        .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                            Text("§eClick to open!"))).currentComponent
                                book8.addExtra(book6)
                                book8.addExtra(book7)
                            }
                            book5 += book8
                            continue
                        } else {
                            if (book4.size > book3.size) {
                                val book8: BaseComponent = TextComponent()
                                val count = 0
                                for (i2 in 0..book3.size.minus(1)) {
                                    val book9 = book4[i2].value.removePrefix("[").removeSuffix("]")
                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                    val book7 =
                                        ComponentBuilder("[${book9}]").event(ClickEvent(ClickEvent.Action.OPEN_URL,
                                            book9.replace("""§.""".toRegex(), "")))
                                            .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                Text("§eClick to open!"))).currentComponent
                                    book8.addExtra(book6)
                                    book8.addExtra(book7)
                                    count.plus(1)
                                }
                                for (i2 in count..book4.size.minus(1)) {
                                    val book9 = book4[i2].value.removePrefix("[").removeSuffix("]")
                                    val book7 =
                                        ComponentBuilder("[${book9}]").event(ClickEvent(ClickEvent.Action.OPEN_URL,
                                            book9.replace("""§.""".toRegex(), "")))
                                            .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                Text("§eClick to open!"))).currentComponent
                                    book8.addExtra(book7)
                                }
                                book5 += book8
                                continue
                            } else {
                                val book8: BaseComponent = TextComponent()
                                for (i2 in 0..book4.size.minus(1)) {
                                    val book9 = book4[i2].value.removePrefix("[").removeSuffix("]")
                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                    val book7 =
                                        ComponentBuilder("[${book9}]").event(ClickEvent(ClickEvent.Action.OPEN_URL,
                                            book9.replace("""§.""".toRegex(), "")))
                                            .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                Text("§eClick to open!"))).currentComponent
                                    book8.addExtra(book6)
                                    book8.addExtra(book7)
                                }
                                val book6 =
                                    ComponentBuilder(book3[book3.size.minus(1)]).currentComponent
                                book8.addExtra(book6)
                                book5 += book8
                                continue
                            }
                        }
                    } else {
                        if(book4.size == 0){
                            if (book3.size == book42.size) {
                                val book8: BaseComponent = TextComponent()
                                for (i2 in 0..book3.size.minus(1)) {
                                    val book9 = book42[i2].value.removePrefix("{").removeSuffix("}")
                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                    val book7 =
                                        ComponentBuilder("{${book9}}").event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,
                                            book9.replace("""(§.)""".toRegex(), "")))
                                            .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                Text("§eClick to §9copy§e to §2clipboard!"))).currentComponent
                                    book8.addExtra(book6)
                                    book8.addExtra(book7)
                                }
                                book5 += book8
                                continue
                            } else {
                                if (book42.size > book3.size) {
                                    val book8: BaseComponent = TextComponent()
                                    val count = 0
                                    for (i2 in 0..book3.size.minus(1)) {
                                        val book9 = book42[i2].value.removePrefix("{").removeSuffix("}")
                                        val book6 = ComponentBuilder(book3[i2]).currentComponent
                                        val book7 =
                                            ComponentBuilder("{${book9}}").event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,
                                                book9.replace("""(§.)""".toRegex(), "")))
                                                .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                    Text("§eClick to §9copy§e to §2clipboard!"))).currentComponent
                                        book8.addExtra(book6)
                                        book8.addExtra(book7)
                                        count.plus(1)
                                    }
                                    for (i2 in count..book42.size.minus(1)) {
                                        val book9 = book42[i2].value.removePrefix("{").removeSuffix("}")
                                        val book7 =
                                            ComponentBuilder("{${book9}}").event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,
                                                book9.replace("""(§.)""".toRegex(), "")))
                                                .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                    Text("§eClick to §9copy§e to §2clipboard!"))).currentComponent
                                        book8.addExtra(book7)
                                    }
                                    book5 += book8
                                    continue
                                } else {
                                    val book8: BaseComponent = TextComponent()
                                    for (i2 in 0..book42.size.minus(1)) {
                                        val book9 = book42[i2].value.removePrefix("{").removeSuffix("}")
                                        val book6 = ComponentBuilder(book3[i2]).currentComponent
                                        val book7 =
                                            ComponentBuilder("{${book9}}").event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,
                                                book9.replace("""(§.)""".toRegex(), "")))
                                                .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                    Text("§eClick to §9copy§e to §2clipboard!"))).currentComponent
                                        book8.addExtra(book6)
                                        book8.addExtra(book7)
                                    }
                                    val book6 =
                                        ComponentBuilder(book3[book3.size.minus(1)]).currentComponent
                                    book8.addExtra(book6)
                                    book5 += book8
                                    continue
                                }
                            }
                        } else {
                            return
                        }
                    }
                } else {
                    if (book4.size == 0){
                        if (book41.size == book3.size){
                            val book8: BaseComponent = TextComponent()
                            for (i2 in 0..book3.size.minus(1)) {
                                val book9 = book41[i2].value.replace("]",", ").replace("[","").removeSuffix(", ").split(", ")
                                val book6 = ComponentBuilder(book3[i2]).currentComponent
                                val book7 =
                                    ComponentBuilder("[${book9[0]}][${book9[1]}]").event(ClickEvent(ClickEvent.Action.CHANGE_PAGE,
                                        book9[1])).event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                        Text("§eClick to §a${book9[1]}§epage!"))).currentComponent
                                book8.addExtra(book6)
                                book8.addExtra(book7)
                            }
                            book5 += book8
                            continue
                        } else {
                            if (book41.size > book3.size){
                                val book8: BaseComponent = TextComponent()
                                val count = 0
                                for (i2 in 0..book3.size.minus(1)){
                                    val book9 = book41[i2].value.replace("]",", ").replace("[","").removeSuffix(", ").split(", ")
                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                    val book7 =
                                        ComponentBuilder("[${book9[0]}][${book9[1]}]").event(ClickEvent(ClickEvent.Action.CHANGE_PAGE,
                                            book9[1])).event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                            Text("§eClick to §a${book9[1]}§epage!"))).currentComponent
                                    book8.addExtra(book6)
                                    book8.addExtra(book7)
                                    count.plus(1)
                                }
                                for (i2 in count..book41.size.minus(1)){
                                    val book9 = book41[i2].value.replace("]",", ").replace("[","").removeSuffix(", ").split(", ")
                                    val book7 =
                                        ComponentBuilder("[${book9[0]}][${book9[1]}]").event(ClickEvent(ClickEvent.Action.CHANGE_PAGE,
                                            book9[1])).event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                            Text("§eClick to §a${book9[1]}§epage!"))).currentComponent
                                    book8.addExtra(book7)
                                }
                                book5 += book8
                                continue
                            } else {
                                val book8: BaseComponent = TextComponent()
                                val count = 0
                                for (i2 in 0..book41.size.minus(1)){
                                    val book9 = book41[i2].value.replace("]",", ").replace("[","").removeSuffix(", ").split(", ")
                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                    val book7 =
                                        ComponentBuilder("[${book9[0]}][${book9[1]}]").event(ClickEvent(ClickEvent.Action.CHANGE_PAGE,
                                            book9[1])).event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                            Text("§eClick to §a${book9[1]}§epage!"))).currentComponent
                                    book8.addExtra(book6)
                                    book8.addExtra(book7)
                                    count.plus(1)
                                }
                                val book6 = ComponentBuilder(book3[book3.size.minus(1)]).currentComponent
                                book8.addExtra(book6)
                                book5 += book8
                                continue
                            }
                        }
                    }
                    return
                }
            }
            for (i2 in 0..book5.size.minus(1)){
                book.spigot().setPage(i2.plus(1),book5[i2])
            }
            book00.itemMeta = book
            bookcash[booklist3[i5]] = book00
        }

=======
        bookcash = ConvertAddBooks.addbooks(bookcash,this)
>>>>>>> Stashed changes
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
                        "test".startsWith(args[0]) -> return Arrays.asList("test")
                        else -> {
                            //JavaPlugin#onTabComplete()を呼び出す
                            return super.onTabComplete(sender, command, alias, args)
                        }
                    }
                }
            }
            if (args.size == 2){
                val booklist0 = config.getKeys(false)
                val booklist1 = booklist0.filterNot { it == "mainversion" }//いらないmainversionを除外
                val booklist2 = booklist1.filterNot { it == "spawnlocation" } //いらないspawnlocationを除外
                val booklist3 = booklist2.filterNot { it == "playerdata" } //いらないplayerdataを除外
                if (args[1].isNotEmpty()) {
                    val size = booklist3.size.minus(1)
                    val books = mutableListOf<String>()
                    for (i in 0..size) {
                        if (booklist3[i].startsWith(args[1])) {
                            books.add(booklist3[i])
                        }
                    }
                    return books.toMutableList()
                }
                return booklist3.toMutableList()
            }
        }
        return null
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
                if(hasPerm(sender,command.permission.toString() + ".open")) { //open権限はあるか？
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
                            val book = bookcash[args[1]]
                            if (book != null) { // bookcashにデータはあるか？
                                // データが有る場合
                                sender.openBook(book) //権限がある場合本を開く
                                if (args[1] == "main") { // oyasainews open mainであるか？
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
                "booklist" -> { // oyasainews booklistである場合
                    if (hasPerm(sender,command.permission.toString()+".booklist")){ //booklist権限は？
                        val booklist0 = config.getKeys(false)
                        val booklist1 = booklist0.filterNot { it == "mainversion" }//いらないmainversionを除外
                        val booklist2 = booklist1.filterNot { it == "spawnlocation" } //いらないspawnlocationを除外
                        val booklist = booklist2.filterNot { it == "playerdata" } //いらないplayerdataを除外
                        sender.sendMessage(booklist.toString())
                        return true
                    } // 権限がない場合
                    sender.sendMessage("権限がありません！")
                    return true
                }
                "color" -> { // oyasainews color
                    if(hasPerm(sender,command.permission.toString()+".color")){ //color権限
                        val oldbook = sender.inventory.itemInMainHand
                        if (oldbook.type == Material.WRITABLE_BOOK){ //本と羽根ペン？
                            val book: BookMeta = oldbook.itemMeta as BookMeta
                            val newbmeta = ChatColor.translateAlternateColorCodes('$', book.pages.toString().removePrefix("[").removeSuffix("]"))
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
                }
                "add" -> { // oyasainews addである場合
                    if(hasPerm(sender,command.permission.toString()+".add")) { //add権限はあるか？
                        val item = sender.inventory.itemInMainHand // 権限があればメインハンドのアイテム入手
                        if (item.type == Material.WRITTEN_BOOK) { //アイテムは記入済みの本？
                            //記入済みの本だった場合
                            val book: BookMeta = item.itemMeta as BookMeta
                            val newbmeta = ChatColor.translateAlternateColorCodes('$', book.pages.toString().removePrefix("[").removeSuffix("]"))
                            val newbook0 = newbmeta.split(", ").toList()
                            val url = """\[(§.)?http.*(§.)?]""".toRegex()
                            val table = """\[.*]\[[0-9]*]""".toRegex()
                            val cmd = """\{(§.)?/.*(§.)?}""".toRegex()
                            val urlandtable = """(\[(§.)?http.*(§.)?]|\[.*]\[[0-9]*]|\{(§.)?/.*(§.)?})""".toRegex()
                            var book5 = arrayOf<BaseComponent>()
                            for (i in 0..newbook0.size.minus(1)) {
                                val book3 = newbook0[i].split(urlandtable)
                                val book4 = url.findAll(newbook0[i]).toList()
                                val book41 = table.findAll(newbook0[i]).toList()
                                val book42 = cmd.findAll(newbook0[i]).toList()
                                if (book41.size == 0) {
                                    if (book42.size == 0) {
                                        if (book3.size == book4.size) {
                                            val book8: BaseComponent = TextComponent()
                                            for (i2 in 0..book3.size.minus(1)) {
                                                val book9 = book4[i2].value.removePrefix("[").removeSuffix("]")
                                                val book6 = ComponentBuilder(book3[i2]).currentComponent
                                                val book7 =
                                                    ComponentBuilder("[${book9}]").event(ClickEvent(ClickEvent.Action.OPEN_URL,
                                                        book9.replace("""(§.)""".toRegex(), "")))
                                                        .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                            Text("§eClick to open!"))).currentComponent
                                                book8.addExtra(book6)
                                                book8.addExtra(book7)
                                            }
                                            book5 += book8
                                            continue
                                        } else {
                                            if (book4.size > book3.size) {
                                                val book8: BaseComponent = TextComponent()
                                                val count = 0
                                                for (i2 in 0..book3.size.minus(1)) {
                                                    val book9 = book4[i2].value.removePrefix("[").removeSuffix("]")
                                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                                    val book7 =
                                                        ComponentBuilder("[${book9}]").event(ClickEvent(ClickEvent.Action.OPEN_URL,
                                                            book9.replace("""(§.)""".toRegex(), "")))
                                                            .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                Text("§eClick to open!"))).currentComponent
                                                    book8.addExtra(book6)
                                                    book8.addExtra(book7)
                                                    count.plus(1)
                                                }
                                                for (i2 in count..book4.size.minus(1)) {
                                                    val book9 = book4[i2].value.removePrefix("[").removeSuffix("]")
                                                    val book7 =
                                                        ComponentBuilder("[${book9}]").event(ClickEvent(ClickEvent.Action.OPEN_URL,
                                                            book9.replace("""(§.)""".toRegex(), "")))
                                                            .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                Text("§eClick to open!"))).currentComponent
                                                    book8.addExtra(book7)
                                                }
                                                book5 += book8
                                                continue
                                            } else {
                                                val book8: BaseComponent = TextComponent()
                                                for (i2 in 0..book4.size.minus(1)) {
                                                    val book9 = book4[i2].value.removePrefix("[").removeSuffix("]")
                                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                                    val book7 =
                                                        ComponentBuilder("[${book9}]").event(ClickEvent(ClickEvent.Action.OPEN_URL,
                                                            book9.replace("""(§.)""".toRegex(), "")))
                                                            .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                Text("§eClick to open!"))).currentComponent
                                                    book8.addExtra(book6)
                                                    book8.addExtra(book7)
                                                }
                                                val book6 =
                                                    ComponentBuilder(book3[book3.size.minus(1)]).currentComponent
                                                book8.addExtra(book6)
                                                book5 += book8
                                                continue
                                            }
                                        }
                                    } else {
                                        if(book4.size == 0){
                                            if (book3.size == book42.size) {
                                                val book8: BaseComponent = TextComponent()
                                                for (i2 in 0..book3.size.minus(1)) {
                                                    val book9 = book42[i2].value.removePrefix("{").removeSuffix("}")
                                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                                    val book7 =
                                                        ComponentBuilder("{${book9}}").event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,
                                                            book9.replace("""(§.)""".toRegex(), "")))
                                                            .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                Text("§eClick to §9copy§e to §2clipboard!"))).currentComponent
                                                    book8.addExtra(book6)
                                                    book8.addExtra(book7)
                                                }
                                                book5 += book8
                                                continue
                                            } else {
                                                if (book42.size > book3.size) {
                                                    val book8: BaseComponent = TextComponent()
                                                    val count = 0
                                                    for (i2 in 0..book3.size.minus(1)) {
                                                        val book9 = book42[i2].value.removePrefix("{").removeSuffix("}")
                                                        val book6 = ComponentBuilder(book3[i2]).currentComponent
                                                        val book7 =
                                                            ComponentBuilder("{${book9}}").event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,
                                                                book9.replace("""(§.)""".toRegex(), "")))
                                                                .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                    Text("§eClick to §9copy§e to §2clipboard!"))).currentComponent
                                                        book8.addExtra(book6)
                                                        book8.addExtra(book7)
                                                        count.plus(1)
                                                    }
                                                    for (i2 in count..book42.size.minus(1)) {
                                                        val book9 = book42[i2].value.removePrefix("{").removeSuffix("}")
                                                        val book7 =
                                                            ComponentBuilder("{${book9}}").event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,
                                                                book9.replace("""(§.)""".toRegex(), "")))
                                                                .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                    Text("§eClick to §9copy§e to §2clipboard!"))).currentComponent
                                                        book8.addExtra(book7)
                                                    }
                                                    book5 += book8
                                                    continue
                                                } else {
                                                    val book8: BaseComponent = TextComponent()
                                                    for (i2 in 0..book42.size.minus(1)) {
                                                        val book9 = book42[i2].value.removePrefix("{").removeSuffix("}")
                                                        val book6 = ComponentBuilder(book3[i2]).currentComponent
                                                        val book7 =
                                                            ComponentBuilder("{${book9}}").event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,
                                                                book9.replace("""(§.)""".toRegex(), "")))
                                                                .event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                    Text("§eClick to §9copy§e to §2clipboard!"))).currentComponent
                                                        book8.addExtra(book6)
                                                        book8.addExtra(book7)
                                                    }
                                                    val book6 =
                                                        ComponentBuilder(book3[book3.size.minus(1)]).currentComponent
                                                    book8.addExtra(book6)
                                                    book5 += book8
                                                    continue
                                                }
                                            }
                                        } else {
                                            sender.sendMessage("URLとページジャンプとコマンドは同じページで使えません")
                                            return true
                                        }
                                    }
                                } else {
                                    if (book4.size == 0){
                                        if (book41.size == book3.size){
                                            val book8: BaseComponent = TextComponent()
                                            for (i2 in 0..book3.size.minus(1)) {
                                                val book9 = book41[i2].value.replace("]",", ").replace("[","").removeSuffix(", ").split(", ")
                                                val book6 = ComponentBuilder(book3[i2]).currentComponent
                                                val book7 =
                                                    ComponentBuilder("[${book9[0]}][${book9[1]}]").event(ClickEvent(ClickEvent.Action.CHANGE_PAGE,
                                                        book9[1])).event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                        Text("§eClick to §a${book9[1]}§epage!"))).currentComponent
                                                book8.addExtra(book6)
                                                book8.addExtra(book7)
                                            }
                                            book5 += book8
                                            continue
                                        } else {
                                            if (book41.size > book3.size){
                                                val book8: BaseComponent = TextComponent()
                                                val count = 0
                                                for (i2 in 0..book3.size.minus(1)){
                                                    val book9 = book41[i2].value.replace("]",", ").replace("[","").removeSuffix(", ").split(", ")
                                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                                    val book7 =
                                                        ComponentBuilder("[${book9[0]}][${book9[1]}]").event(ClickEvent(ClickEvent.Action.CHANGE_PAGE,
                                                            book9[1])).event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                            Text("§eClick to §a${book9[1]}§epage!"))).currentComponent
                                                    book8.addExtra(book6)
                                                    book8.addExtra(book7)
                                                    count.plus(1)
                                                }
                                                for (i2 in count..book41.size.minus(1)){
                                                    val book9 = book41[i2].value.replace("]",", ").replace("[","").removeSuffix(", ").split(", ")
                                                    val book7 =
                                                        ComponentBuilder("[${book9[0]}][${book9[1]}]").event(ClickEvent(ClickEvent.Action.CHANGE_PAGE,
                                                            book9[1])).event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                            Text("§eClick to §a${book9[1]}§epage!"))).currentComponent
                                                    book8.addExtra(book7)
                                                }
                                                book5 += book8
                                                continue
                                            } else {
                                                val book8: BaseComponent = TextComponent()
                                                val count = 0
                                                for (i2 in 0..book41.size.minus(1)){
                                                    val book9 = book41[i2].value.replace("]",", ").replace("[","").removeSuffix(", ").split(", ")
                                                    val book6 = ComponentBuilder(book3[i2]).currentComponent
                                                    val book7 =
                                                        ComponentBuilder("[${book9[0]}][${book9[1]}]").event(ClickEvent(ClickEvent.Action.CHANGE_PAGE,
                                                            book9[1])).event(HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                            Text("§eClick to §a${book9[1]}§epage!"))).currentComponent
                                                    book8.addExtra(book6)
                                                    book8.addExtra(book7)
                                                    count.plus(1)
                                                }
                                                val book6 = ComponentBuilder(book3[book3.size.minus(1)]).currentComponent
                                                book8.addExtra(book6)
                                                book5 += book8
                                                continue
                                            }
                                        }
                                    }
                                    sender.sendMessage("URLとページジャンプとコマンドは同じページで使えません")
                                    return true
                                }
                            }
                            if (args.getOrNull(1) != null) {
                                for (i in 0..book5.size.minus(1)){
                                    book.spigot().setPage(i.plus(1),book5[i])
                                }
                                item.itemMeta = book
                                sender.sendMessage("自動で装飾($)とリンク[http...]とページジャンプ[...][Page]とコマンド{/...}を変換しました！")
                                if (args[1] == "mainversion"){ //mainversionだった場合キャンセルする
                                    sender.sendMessage("mainversionは変更できません！")
                                    return true
                                } //mainversionでなければ続行
                                if (args[1] == "spawnlocation"){ //spawnlocationだった場合キャンセルする
                                    sender.sendMessage("spawnlocationは変更できません！")
                                    return true
                                }
                                if (args[1] == "playerdata") {
                                    sender.sendMessage("playerdataは変更できません！")
                                    return true
                                }
                                config.set(args[1], item); saveConfig() // configへセーブ
                                sender.sendMessage("${args[1]}を上書きしました")
                                if (args[1] == "main") { // oyasainews add mainか？
                                    // mainである場合、関数mainbookを更新&mainversionカウントを増やす&configへ保存
                                    mainbook = item
                                    mainver += 1
                                    config.set("mainversion", mainver); saveConfig()
                                } //mainじゃない場合
                                bookcash[args[1]] = item
                                return true
<<<<<<< Updated upstream
                            } //引数がない場合
                            sender.sendMessage("/$label add [BookName]\nBookNameの部分が足りません！")
=======
                            }
                            val newbook = ConvertAddBooks.convertbooks(item)
                            sender.sendMessage("装飾($)リンク[http...]ページジャンプ[...][Page]コマンド[/...]を変換！")
                            bookcash[args[1]] = newbook
                            config.set(args[1], newbook); saveConfig() // configへセーブ
                            sender.sendMessage("${args[1]}を上書きしました")
                            if (args[1] == "main") { // oyasainews add mainか？
                                // mainである場合、関数mainbookを更新&mainversionカウントを増やす&configへ保存
                                mainbook = item
                                mainver += 1
                                config.set("mainversion", mainver); saveConfig()
                            } //mainじゃない場合
                            bookcash[args[1]] = item
>>>>>>> Stashed changes
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
                            val book = bookcash[args[1]]
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
                }
                "remove" -> { // oyasainews removeの場合
                    if(hasPerm(sender,command.permission.toString()+".remove")) { //remove権限はあるか？
                        if (args.getOrNull(1) != null) { // 権限があって、removeの後に引数は？
                            when(args[1]){
                                "main" -> {sender.sendMessage("mainは消去できません！"); return true}
                                "mainversion" -> {sender.sendMessage("mainversionは消去できません！"); return true}
                                "spawnlocation" -> {sender.sendMessage("spawnlocationは消去できません！"); return true}
                                "playerdata" -> {sender.sendMessage("playerdataは消去できません！"); return true}
                            }
                            val book = config.getItemStack(args[1])
                            if (book != null) { // [BookName]にデータはあるか？
                                bookcash.minus(args[1])
                                config.set(args[1], null); saveConfig()
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
                "setspawn" ->{
                    if(hasPerm(sender,command.permission.toString()+".setspawn")){ //setspawn権限はあるか？
                        config.set("spawnlocation",sender.location) ; saveConfig()
                        sender.sendMessage("spawnのlocationを保存しました！")
                        return true
                    } //setspawn権限がない場合
                    sender.sendMessage("権限がありません！")
                    return true
                }
<<<<<<< Updated upstream
=======
                /* 必要のないオプション、だけど使うかもしれないのでコメントアウト
                "reload" ->{
                    if(hasPerm(sender,command.permission.toString()+".reload")) {
                        bookcash = mutableMapOf()
                        bookcash = ConvertAddBooks.addbooks(bookcash, this)
                        sender.sendMessage("データをリロードしました")
                        return true
                    }
                    sender.sendMessage("権限がありません！")
                    return true
                }
>>>>>>> Stashed changes
                else -> { //引数がヒットしない場合
                    sender.sendMessage("無効なオプションです")
                    return true
                }
                 */
            }
        } // コマンド送信元がPlayerじゃない場合
        sender.sendMessage("プレイヤーのみ実行可能です")
        return false
    }

    override fun onDisable() {
    }
}