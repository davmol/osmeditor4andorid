# Vespucci の導入

Vespucci は、デスクトップエディタが提供する大半の機能をサポートする、フル機能の OpenStreetMap エディタです。Google の Android 2.3 から 7.0 と AOSP ベースの派生物でうまくテストされています。注意事項：モバイルデバイスの性能はデスクトップに追いついていますが、特に古いデバイスではメモリが限られており、かなり遅くなる傾向にあります。Vespucci を使用する際にはこれを念頭に置き、編集するエリアを適切なサイズに保つなどしてください。 

## 初回の使用

Vespucci 起動時に、Download other location"/"Load Area"ダイアログを表示します。表示された座標をすぐにダウンロードする場合は、適切なオプションを選択し、ダウンロードする場所の半径を設定します。遅いデバイスでは広いエリアを選択しないでください。 

または「地図を表示」ボタンを押してダイアログを無視し、パンしたりズームして編集・ダウンロードしたい位置に移動できます(下記参照: 「Vespucciでの編集」).

## Vespucci での編集

画面サイズや機種の年代によって、編集操作は上部バーにあるアイコンで直接、あるいは上部バー右側のドロップダウンメニューで、あるいは下部のバー(あれば)から、あるいはメニューキーでアクセスできます。

<a id="download"></a>

### OSM データのダウンロード

転送アイコン ![Transfer](../images/menu_transfer.png) または「転送」メニュー項目で選びます。オプションが7つあります:

* **表示領域をダウンロード** - 画面上に表示されている領域をダウンロードして既存データを置き換えます *(ネットワーク接続必須)*
* **表示領域を追加ダウンロード** - 画面上に表示されている領域をダウンロードして既存データに追加します *(ネットワーク接続必須)*
* **その他の領域をダウンロード** - 座標を入力したり、位置を探したり、現在地を使用したりして、指定された位置の周りの領域をダウンロードするフォームを表示します *(ネットワーク接続必須)*
* **データをOSMサーバーにアップロード** - 編集内容をOpenStreetMapにアップロードします *(requires authentication)* *(ネットワーク接続必須)*
* **自動ダウンロード** - 現在地周辺の領域を自動的にダウンロードします *(ネットワーク接続必須)* *(requires GPS)*
* **ファイル...** - OSMデータを端末装置にあるファイルに保存したり、ファイルからロードしたりします。
* **メモ/バグ...** - OSMメモおよびQAツール(現在はOSMOSE)による「バグ」を(自動および手動で)ダウンロードします *(ネットワーク接続必須)*

端末装置にデータをダウンロードするいちばん簡単なやり方は、編集したい位置にズームしたりパンして「表示領域をダウンロード」を選ぶことです。ジャスチャー、ズームボタン、装置のボリューム制御ボタンでズーム操作を行えます。するとVespucci は現在の表示領域のデータをダウンロードします。自分の端末装置にダウンロードするのに認証は不要です。

### 編集する

<a id="lock"></a>

#### ロック、アンロック、モード切り替え

誤編集を避けるためにVespucciは「ロック」モードで始まります。これは地図のズームと移動だけができるモードです。画面をアンロックするには ![Locked](../images/locked.png) アイコンをタップします。 

ロックアイコン上で長押しすると、メニューが表示され、現在4つのオプションがあります
：

* **通常** - デフォルトの編集モード。オブジェクトを新規追加したり、既存のものを編集・移動・削除できます。単に白いロックアイコンが表示されます。
* **タグのみ** - 既存のオブジェクトを選ぶとプロパティ・エディタが起動され、マエイン画面上で長押しするとオブジェクトが新規追加されますが、他のジオメトリ操作は機能しません。「T」の付いた白いロックアイコンが表示されます。
* **室内** - 室内モードを有効化します。[Indoor mode](#indoor)参照。「I」の付いた白いロックアイコンが表示されます。
* **Cモード** - Cモードを有効化し、警告フラグのセットを持つオブジェクトだけが表示されます。 [C-Mode](#c-mode)参照。「C」の付いた白いロックアイコンが表示されます。

#### シングルタップ・ダブルタップ・長押し

デフォルトで、選択可能なノードとウェイの周りにはオレンジの領域があり、オブジェクトを選択するのにタッチすべき場所をおおよそ示しています。3つの選択肢があります：

* Single tap: Selects object. 
    * An isolated node/way is highlighted immediately. 
    * However, if you try to select an object and Vespucci determines that the selection could mean multiple objects it will present a selection menu, enabling you to choose the object you wish to select. 
    * Selected objects are highlighted in yellow. 
    * For further information see [Node selected](Node%20selected.md), [Way selected](Way%20selected.md) and [Relation selected](Relation%20selected.md).
* Double tap: Start [Multiselect mode](Multiselect.md)
* Long press: Creates a "crosshair", enabling you to add nodes, see below and [Creating new objects](Creating%20new%20objects.md). This is only enabled if "Simple mode" is deactivated.

マッピング密度の高い領域を編集する際にはズームインしてからやると良いでしょう。

Vespucciには良い「取り消し/やり直し」システムがあるので自分の端末装置上で気軽に試してください。ただしテストのためだけのデータをアップロードしたりはしないでください。

#### 選択 / 選択解除 (シングルタップおよび「選択メニュー」)

オブジェクトにタッチして選択し、ハイライトさせてください。空の領域で画面をタッチすると選択が解除されます。あるオブジェクトを選択してから別のものを選択したい場合は、単に対象のオブジェクトをタッチしてください。最初に選択解除する必要はありません。オブジェクト上でダブルタップすると[Multiselect mode](Multiselect.md)が始まります。

あるオブジェクトを選択した際にVespucci がその選択対象が複数あると判断した場合(ウェイ上のノードや他のオーバーラップしたオブジェクトなど)、選択メニューが表示されることに注意： 選択したいオブジェクトをタップするとそのオブジェクトが選択されます。 

選択されたオブジェクトは薄い黄色の境界線で示されます。黄色の境界線は、地図の背景やズームレベルによっては指し示すことが難しい場合があるかもしれません。いったん選択されたら、その選択が正しいか確認する通知が表示されます。

選択が終わると(ボタンあるいはメニュー項目のいずれかで)そのオブジェクトに対してできる操作の一覧が現れます： 詳細は [Node selected](Node%20selected.md)、 [Way selected](Way%20selected.md) および [Relation selected](Relation%20selected.md)を参照。

#### 選択したオブジェクト: タグを編集する

選択されたオブジェクトにもういちどタッチするとタグエディタが開き、そのオブジェクトに関連するタグを編集できます。

オーバーラップしたオブジェクト(ウェイ上のノードなど)に対しては選択メニューは2回目に現れることに注意してください。同じオブジェクトを選択するとタグエディタが現れます；別のオブジェクトを選択すると単純に他のオブジェクトを選択します。

#### 選択したオブジェクト: ノードやウェイを移動する

いったんオブジェクトを選択すると、移動させることができます。オブジェクトを移動/削除できるのは選択したときだけであることに注意してください。単純に選択されたオブジェクトのあたり(反応域ゾーン内)をドラッグして移動します。独自設定で大きなドラッグ領域を選択すると、選択されたノードの周りに大きな領域が得られ、オブジェクトの位置合わせがしやすくなります。 

#### Adding a new Node/Point or Way 

On first start the app launches in "Simple mode", this can be changed in the main menu by un-checking the corresponding checkbox.

##### Simple mode

Tapping the large green floating button on the map screen will show a menu. After you've selected one of the items, you will be asked to tap the screen at the location where you want to create the object, pan and zoom continues to work if you need to adjust the map view. 

See [Creating new objects in simple actions mode](Creating%20new%20objects%20in%20simple%20actions%20mode.md) for more information.

##### Advanced (long press) mode
 
Long press where you want the node to be or the way to start. You will see a black "crosshair" symbol. 
* If you want to create a new node (not connected to an object), click away from existing objects.
* If you want to extend a way, click within the "tolerance zone" of the way (or a node on the way). The tolerance zone is indicated by the areas around a node or way.

十字のシンボルが現れたら次のような選択肢があります：

* 同じ場所でタッチ。
    * 十字がノードの近くにない場合、同じ位置を再度タッチすると新しいノードが作成されます。ウェイの近く(だがノードの近くではない)にいる場合、新しいノードはウェイ上(かつウェイに接続)にできます。
    * 十字がノードの近くにある場合(ノードの反応域ゾーン内)にある場合、同じ位置をタッチすると単にそのノードを選択し、タグエディタが開きます。新しいノードは作成されません。上述の選択と同じ操作です。
* 別の場所をタッチ。別の位置(十字の反応域外)をタッチすると元の位置から現在の位置まで、ウェイのセグメントを追加します。十字がウェイやノードの近くなら、新しいセグメントはそのノードやウェイに接続します。

ウェイにノードをもっと追加したい場合は単純にスクリーンにタッチしてください。終える際には、最後のノードに2回タッチします。最後のノードがウェイやノード上にある場合、そのセグメントはそのウェイまたはノードに自動的に接続します。 

You can also use a menu item: See [Creating new objects](Creating%20new%20objects.md) for more information.

#### エリアを追加する

OpenStreetMap は他の地理データのシステムとは異なり、現在「エリア」オブジェクトという種類がありません。オンライエディタの「iD」はOSMのエレメントを元にエリアの概念を持ち込もうとしていますが、環境によりうまくいく場合とそうでない場合があります。Vespucci は現在このような試みは行っていないため、あなたはエリアの表現方法について、少し知っておく必要があります：

* _closed ways (*polygons")_: the simplest and most common area variant, are ways that have a shared first and last node forming a closed "ring" (for example most buildings are of this type). These are very easy to create in Vespucci, simply connect back to the first node when you are finished with drawing the area. Note: the interpretation of the closed way depends on its tagging: for example if a closed way is tagged as a building it will be considered an area, if it is tagged as a roundabout it wont. In some situations in which both interpretations may be valid, an "area" tag can clarify the intended use.
* _multi-ploygons_: some areas have multiple parts, holes and rings that can't be represented with just one way. OSM uses a specific type of relation (our general purpose object that can model relations between elements) to get around this, a multi-polygon. A multi-polygon can have multiple "outer" rings, and multiple "inner" rings. Each ring can either be a closed way as described above, or multiple individual ways that have common end nodes. While large multi-polygons are difficult to handle with any tool, small ones are not difficult to create in Vespucci. 
* _coastlines_: for very large objects, continents and islands, even the multi-polygon model doesn't work in a satisfactory way. For natural=coastline ways we assume direction dependent semantics: the land is on the left side of the way, the water on the right side. A side effect of this is that, in general, you shouldn't reverse the direction of a way with coastline tagging. More information can be found on the [OSM wiki](http://wiki.openstreetmap.org/wiki/Tag:natural%3Dcoastline).

#### ウェイのジオメトリを改善する

選択したウェイ上で十分にズームインすると、十分な長さのウェイのセグメントの中央に小さな「x」が現れます。その「x」をドラッグするとその位置のウェイ内にノードを作成します。注意: 誤操作によるノード作成を避けるために、この操作の反応域はかなり小さくなっています。

#### 切り取り、コピー&ペースト

選択したノードやウェイはコピーまたは切り取りでき、新しい位置に一回または複数回貼り付けることができます。切り取った内容にはosm id とバージョンが含まれます。貼り付ける際には貼り付けたい位置で長押しします (その位置を示す十字が現れます) そのあとメニューから「貼り付け」を選択できます。 

#### 住所を効果的に追加する

Vespucci には「住所タグを追加」機能があり、住所の調査をより効果的にできるようにします。以下から選択できます：

* after a long press: Vespucci will add a node at the location and make a best guess at the house number and add address tags that you have been lately been using. If the node is on a building outline it will automatically add a "entrance=yes" tag to the node. The tag editor will open for the object in question and let you make any necessary further changes.
* in the node/way selected modes: Vespucci will add address tags as above and start the tag editor.
* in the tag editor.

住居番号の予測入力が機能するには典型的には道路の両側に最低2つの住居番号が必要で、データ中に多くの番号があるほどベターです。

使う場合には [Auto-download](#download) モードでの使用を考慮してください。  

#### 進行方向制限を追加する

Vespucci には進行方向制限を素早く追加するやり方があります。必要な場合には、ウェイを自動的に分割して、あなたにエレメントを再選択するように尋ねるでしょう。 

* highwayタグの付いたウェイを選択 (進行方向制限はhighwayにのみ追加できます。他のウェイでこれを行う場合は汎用的な「リレーションを作成」モードを使ってください)
* メニューから「制限を追加」を選択
* 「via」ノードまたはウェイを選択 (対象となる「via」エレメントだけにタッチエリアが表示されます)
* 「to」ウェイを選択 (2回戻って「to」エレメントを「from」エレメントにセットすることができます。Vespucci はあなたが Uターン禁止制限を追加しようとしているとみなします)
* 制限の種別をセット

### 「ロック」モードのVespucci

赤いロックボタンが表示されている時は編集しない操作は全て行えます。加えて、オブジェクト近辺での長押しで、それがOSMオブジェクトであれば詳細情報が表示されます。

### 自分の変更内容を保存する

*(ネットワーク接続必須)*

ダウンロード時と同じボタンまたはメニュー項目を選んで、今度は「データをOSMサーバーにアップロード」を選択します。

Vespucci はOAuth 認証と旧式のユーザー名とパスワードによる方法をサポートします。OAuth はクリア時のパスワード送信を避けるため、そちらの方が望ましいです。

New Vespucci installs will have OAuth enabled by default. On your first attempt to upload modified data, a page from the OSM website loads. After you have logged on (over an encrypted connection) you will be asked to authorize Vespucci to edit using your account. If you want to or need to authorize the OAuth access to your account before editing there is a corresponding item in the "Tools" menu.

保存したくてもインターネット接続が無い場合には、JOSM互換の.osmファイルに保存して後からVespucci またはJOSMでアップロードできます。 

#### アップロード時の競合を解決する

Vespucci にはシンプルな競合リゾルバがあります。しかしながら、重大な問題がありそうな場合には、変更内容を However if you suspect that there are major issues with your edits, export your changes to a .osc ファイル (「転送」メニュー内の「エクスポート」メニュー項目)にエクスポートしてJOSMで修正してアップロードしてください。詳細なヘルプは [conflict resolution](Conflict%20resolution.md)参照。  

## GPSを使う

Vespucci を使ってGPX トラックを作成し、端末装置上に表示することができます。さらに、現在のGPS 位置(GPS メニューで「位置を表示」をセット) を表示したり、付近を画面の中央に持ってきたり、位置を追跡(GPS メニューで「GPS位置を追跡」をセット) したりすることができます。 

後者のセットを持っている場合、画面を手動で動かしたり編集すると、無効化すべき「GPS追跡」モードとなって青いGPSの矢印が中抜きから色塗りされた矢印に変わります。素早く「追跡」モードに戻るには、単にGPSボタンにタッチするか、メニューの選択肢を再チェックしてください。

## メモとバグ

Vespucci supports downloading, commenting and closing of OSM Notes (formerly OSM Bugs) and the equivalent functionality for "Bugs" produced by the [OSMOSE quality assurance tool](http://osmose.openstreetmap.fr/en/map/). Both have to either be down loaded explicitly or you can use the auto download facility to access the items in your immediate area. Once edited or closed, you can either upload the bug or Note immediately or upload all at once.

地図上ではメモとバグが小さな虫のアイコン ![Bug](../images/bug_open.png)で表され、緑はクローズ済/解決済で、青は自分が作成・変更したもの、黄色がまだ有効で変更されていないことを示します。 

OSMOSEバグ表示は影響するオブジェクトへのリンクを青色で提供し、そのリンクをタッチするとオブジェクトが選択され、そこに画面が中央寄せされ、必要であれば予めそのエリアをダウンロードします。 

### フィルタリング

Besides globally enabling the notes and bugs display you can set a coarse grain display filter to reduce clutter. In the [Advanced preferences](Advanced%20preferences.md) you can individually select:

* メモ
* Osmose エラー
* Osmose 警告
* Osmose マイナーな問題
* カスタム

<a id="indoor"></a>

## 室内モード

Mapping indoors is challenging due to the high number of objects that very often will overlay each other. Vespucci has a dedicated indoor mode that allows you to filter out all objects that are not on the same level and which will automatically add the current level to new objects created there.

モードはロック項目上で長押しすると有効化できます。 [Lock, unlock, mode switching](#lock) および対応するメニュー項目の選択を参照。

<a id="c-mode"></a>

## Cモード

Cモードでは、警告フラグセットを持つオブジェクトだけが表示され、特定の問題を持っていたり構成可能なチェックに一致するオブジェクトを簡単に選び出せます。オブジェクトが選択され、プロパティエディタがCモードで起動されるとベストマッチのプリセットが自動的に適用されます。

モードはロック項目上で長押しすると有効化できます。 [Lock, unlock, mode switching](#lock) および対応するメニュー項目の選択を参照。

### 構成チェック

現在、構成可能なチェックが2つあり (FIXMEタグ用とと現在が構成できないリレーション上のtypeタグの抜けのテスト用のチェックがあります) ともに「独自設定」内の「入力値検査の独自設定」で選んで構成できます。 

エントリーのリストは、リスト上半分の「再調査」エントリーと下半分の「チェックエントリー」の2つに分割されます。エントリーはクリックすると編集でき、緑のメニューモタンでエントリーを追加できます。

#### 再調査エントリー

再調査エントリーには次のようなプロパティがあります:

* **Key** - Key of the tag of interest.
* **Value** - Value the tag of interest should have, if empty the tag value will be ignored.
* **Age** - how many days after the element was last changed the element should be re-surveyed, if a check_date field is present that will be the used, otherwise the date the current version was create. Setting the value to zero will lead to the check simply matching against key and value.
* **Regular expression** - if checked **Value** is assumed to be a JAVA regular expression.

**Key** and **Value** are checked against the _existing_ tags of the object in question.

#### エントリーのチェック

Check entries have the following two properties:

* **Key** - Key that should be present on the object according to the matching preset.
* **Require optional** - Require the key even if the key is in the optional tags of the matching preset .

This check works by first determining the matching preset and then checking if **Key** is a "recommended" key for this object according to the preset, **Require optional** will expand the check to tags that are "optional* on the object. Note: currently linked presets are not checked.

## フィルター

### タグベースのフィルター

The filter can be enabled from the main menu, it can then be changed by tapping the filter icon. More documentation can be found here [Tag filter](Tag%20filter.md).

### プリセットベースのフィルター

An alternative to the above, objects are filtered either on individual presets or on preset groups. Tapping on the filter icon will display a preset selection dialog similar to that used elsewhere in Vespucci. Individual presets can be selected by a normal click, preset groups by a long click (normal click enters the group). More documentation can be found here [Preset filter](Preset%20filter.md).

## Vespucci をカスタマイズする

### 変更希望がありそうな設定

* 背景レイヤ
* オーバーレイするレイヤ。オーバーレイの追加は旧式の機器やメモリが少ないものでは問題が起きる場合があります。デフォルト: なし。
* メモ/バグの表示。オープン状態のメモやバグは黄色い虫のアイコンで表示され、クローズ済のものは同じく緑で表示されます。デフォルト: on.
* 写真レイヤ。ジオリファレンスされた写真を赤いカメラのアイコンで表示します。方向の情報が利用できる場合にはアイコンは回転します。デフォルト: off.
* 画面を点灯したままにする。デフォルト: off.
* 大きなノードのドラッグ用領域。タッチ入力で端末装置上のノードを動かそうとすると、自分の指がいま画面上のどの位置にあるかが曖昧なため、よく問題を引き起こします。これをオンにすると、大きな領域が提供され、中心を外れたドラッグ(選択と他の操作はそれまで通り通常のタッチ反応域を使用)が使えます。デフォルト: off.

#### 高度な独自設定

* Node icons. Default: on.
* Always show context menu. When turned on every selection process will show the context menu, turned off the menu is displayed only when no unambiguous selection can be determined. Default: off (used to be on).
* Enable light theme. On modern devices this is turned on by default. While you can enable it for older Android versions the style is likely to be inconsistent.
* Show statistics. Will show some statistics for debugging, not really useful. Default: off (used to be on).  

## Reporting Problems

If Vespucci crashes, or it detects an inconsistent state, you will be asked to send in the crash dump. Please do so if that happens, but please only once per specific situation. If you want to give further input or open an issue for a feature request or similar, please do so here: [Vespucci issue tracker](https://github.com/MarcusWolschon/osmeditor4android/issues). The "Provide feedback" function from the main menu will open a new issue and include the relevant app and device information without extra typing.

If you want to discuss something related to Vespucci, you can either start a discussion on the [Vespucci Google group](https://groups.google.com/forum/#!forum/osmeditor4android) or on the [OpenStreetMap Android forum](http://forum.openstreetmap.org/viewforum.php?id=56)


