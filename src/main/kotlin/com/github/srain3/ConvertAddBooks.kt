package com.github.srain3

import net.md_5.bungee.api.chat.*
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.ChatColor
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta

object ConvertAddBooks {
    fun addbooks(bookc:MutableMap<String,ItemStack>,main: Main) : MutableMap<String,ItemStack> {
        val booklist0 = main.config.getKeys(false)
        val booklist1 = booklist0.filterNot { it == "mainversion" }
        val booklist2 = booklist1.filterNot { it == "spawnlocation" }
        val booklist3 = booklist2.filterNot { it == "playerdata" }
        val booklist4 = booklist3.filterNot { it == "notmask" }
        for (i in 0..booklist4.size.minus(1)) {
            val book = main.config.getItemStack(booklist4[i])!!
            val newbook = convertbooks(book.clone())
            bookc[booklist4[i]] = newbook
        }
        return bookc
    }

    //データ変換用処理
    fun convertbooks(book1: ItemStack): ItemStack {
        val bookmeta = book1.itemMeta as BookMeta
        val bookaddcolor = ChatColor.translateAlternateColorCodes('$', bookmeta.pages.toString())
        val booklist = bookaddcolor.removePrefix("[").removeSuffix("]").split(", ")
        val url = """\[(§.)*http(s)?://([^\[\]]*)]""".toRegex()
        val table = """\[[^\[]*]\[(§.)*[0-9]*(§.)*]""".toRegex()
        val cmd = """\[(§.)*(/[^\[\]]*)]""".toRegex()
        val urltext = "§eClick to open!"
        val tabletext1 = "§eClick to §a"
        val tabletext2 = "§epage!"
        val cmdtext = "§eClick to §9copy §6"
        val cmdtext2 = "\n§eOpen chatbox to KEY[ctrl]+[V] to Paste"
        val notcolor = """(§.)*""".toRegex()
        val nottable = """]\[(§.)*[0-9]*(§.)*""".toRegex()
        var newbooklist = arrayOf<BaseComponent>()
        var urlcount : Int ; var tablecount : Int ; var cmdcount : Int
        for (page in 0..booklist.size.minus(1)) {
            val bookblocks: BaseComponent = TextComponent()
            val strings = booklist[page].replace(url,"u-r-l-0, ").replace(table,"ta-b-le-0, ").replace(cmd,"c-m-d-0, ").removeSuffix(", ").split(", ")
            val urls = url.findAll(booklist[page]).toList()
            val tables = table.findAll(booklist[page]).toList()
            val cmds = cmd.findAll(booklist[page]).toList()
            urlcount = 0 ; tablecount = 0 ; cmdcount = 0
            for (block in 0..strings.size.minus(1)){
                if (strings[block].lastIndexOf("u-r-l-0")!= -1){
                    val text1 = strings[block].removeSuffix("u-r-l-0")
                    val text0 = ComponentBuilder(text1).currentComponent
                    bookblocks.addExtra(text0)
                    val urllink = urls[urlcount].value.removePrefix("[").removeSuffix("]").replace(notcolor, "")
                    val url0 = ComponentBuilder(urls[urlcount].value.removePrefix("[").removeSuffix("]")).event(ClickEvent(ClickEvent.Action.OPEN_URL, urllink))
                        .event(HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(urltext))).currentComponent
                    bookblocks.addExtra(url0)
                    urlcount = urlcount.plus(1)
                } else {
                    if (strings[block].lastIndexOf("ta-b-le-0")!= -1){
                        val text1 = strings[block].removeSuffix("ta-b-le-0")
                        val text0 = ComponentBuilder(text1).currentComponent
                        bookblocks.addExtra(text0)
                        val hovertext = tables[tablecount].value.removePrefix("[").removeSuffix("]").replace(notcolor, "").split("][")
                        val table0 = ComponentBuilder(tables[tablecount].value.removePrefix("[").removeSuffix("]").replace(nottable, "")).event(ClickEvent(ClickEvent.Action.CHANGE_PAGE, hovertext[1]))
                            .event(HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("${tabletext1}${hovertext[1]}${tabletext2}"))).currentComponent
                        bookblocks.addExtra(table0)
                        tablecount = tablecount.plus(1)
                    } else {
                        if (strings[block].lastIndexOf("c-m-d-0")!= -1){
                            val text1 = strings[block].removeSuffix("c-m-d-0")
                            val text0 = ComponentBuilder(text1).currentComponent
                            bookblocks.addExtra(text0)
                            val cmdname = cmds[cmdcount].value.removePrefix("[").removeSuffix("]").replace(notcolor, "")
                            val cmd0 = ComponentBuilder(cmds[cmdcount].value.removePrefix("[").removeSuffix("]")).event(ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, cmdname))
                                .event(HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("${cmdtext}${cmdname}${cmdtext2}"))).currentComponent
                            bookblocks.addExtra(cmd0)
                            cmdcount = cmdcount.plus(1)
                        } else {
                            val text1 = strings[block]
                            val text0 = ComponentBuilder(text1).currentComponent
                            bookblocks.addExtra(text0)
                        }
                    }
                }
            }
            newbooklist += bookblocks
        }
        for (i in 0..newbooklist.size.minus(1)){
            bookmeta.spigot().setPage(i.plus(1),newbooklist[i])
        }
        book1.itemMeta = bookmeta
        return book1
    }
}