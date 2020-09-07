# server-news
 このプラグインは、お知らせ本をコマンドで見れる/作る/更新することが出来ます。<br>
 Wikiなどの情報を見に行かず、ゲーム内で確認ができる優れもの！
# 機能紹介
## プレイヤーjoin時自動表示機能
 プレイヤーがjoinした時、自動的にお知らせの本を開きます。<br>
 開くお知らせは「main」で固定です。<br>
 <br>
 素晴らしいのはここから！<br>
 プレイヤーが最後に見たメインお知らせと、現在のメインお知らせ。<br>
 違う場合のみ自動表示します。<br>
 つまり、一度読んだお知らせはjoin時に表示されません！<br>
## addコマンド=非常にシンプルなお知らせ追加や更新が出来る機能
 メインには必要ないが、一応ゲーム内で見れるようにしたい...それ、出来ます。<br>
 まず、追加したい内容を記載した記入済みの本をメインハンドに持ちます。<br>
 次に「/oyasainews add (BookName)」コマンドを送信！<br>
 configに保存され、後に記載するopenコマンドで開けるようになります！<br>
 <br>
 更新も同じコマンドで出来ます。<br>
 更新の場合は、更新したいBookNameを入力するだけです。<br>
 メインお知らせを更新する場合は「/oyasainews add main」となります。<br>
## openコマンド=メイン以外のお知らせも見れる機能
 さぁ、メイン以外も見てみましょう！<br>
 「/oyasainews open (BookName)」と送ると<br>
 addで追加された(BookName)を開けます。勿論mainも開けますよ！<br>
 しかもインベントリに空きがなくても大丈夫！<br>
 直接、本を開きます！インベントリの圧迫や整理も心配なし！<br>
## booklistコマンド=登録されている本の一覧表示機能
 どんなBookNameが登録されてるかわからない？大丈夫！<br>
 「/oyasainews booklist」を送ると<br>
 mainを始めとしたリストが見れます！<br>
 この中から見たいものがあるかわかりますね。<br>
## getコマンド=編集に便利な機能
 openでは直接開いてる故に、編集したい時に書き写さなきゃ...いえ、心配には及びません。<br>
 (BookName)を編集できる状態で取得します！<br>
## removeコマンド=必要がなくなった場合の機能
 addコマンドで追加したものの...もう表示しなくて良くなったなぁ。<br>
 そんな時にはremoveしましょう！表示されなくなります！<br>
 (ちなみに、mainはremove出来ない安全装置、あります。ご安心下さい)<br>
## setspawnコマンド=初inの時は自動表示機能を無効化出来る...かも？
 spawnの場所を設定できるコマンド。<br>
 spawnの設定と同じ場所だとJoin時自動表示機能を無効化します。<br>
 初inした時にお知らせ以外のGUIを開く時は必須ですね<br>
