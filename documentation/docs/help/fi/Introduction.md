# Johdanto Vespucciin

Vespucci on täysipuolinen OpenStreetMap-muokkain, joka sisältää useimmat pöytäkoneiden tarjoamat toiminnot. Se on testattu toimivaksi Googlen Androidin versioilla 2.3–7.0 sekä eri AOSP-pohjaisilla alustoilla. Pieni varoituksen sana on paikallaan: vaikka mobiililaitteet ovat kuroneet pöytäkoneiden etumatkaa kiinni, varsinkin vanhemmissa malleissa muistia on niukasti ja ne tapaavat olla varsin hitaita. Tämä kannattaa ottaa huomioon Vespuccia käytettäessä, esimerkiksi pitämällä muokattavat alueet kohtuullisen kokoisina. 

## Ensimmäinen käyttökerta

Kun Vespucci käynnistyy, se näyttää dialogin "Lataa (joku muu) alue". Jos näkyvissä on koordinaatit ja haluat aloittaa latauksen heti, voit mainitun toiminnon valittuasi asettaa paikan sekä säteen, miltä kyseisen paikan ympäriltä dataa ladataan. Älä valitse laajaa aluetta, jos laitteesi on hidas. 

Vaihtoehtoisesti voit hylätä dialogin painamalla "Kartalle"-nappia, ja etsiä alueen, jota haluat muokata. Lataa data, kun olet sovitellut karttanäkymän kohdalleen. (Katso alempaa "Muokkaaminen Vespuccilla".)

## Muokkaaminen Vespuccilla

Laitteesi näytön koosta ja iästä riippuen muokkaustoiminnot voivat löytyä joko suoraan yläpalkin kuvakkeista, palkin oikean laidan alasvetovalikosta, mahdollisesta alapalkista tai valikkonäppäimen kautta.

<a id="download"></a>

### OSM-datan lataaminen

Kosketa ensin siirtokuvaketta ![Siirto](../images/menu_transfer.png) tai avaa valikosta "Siirto". Esiin tulee seitsemän valintaa:

* **Lataa nykyinen näkymä** – lataa näytöllä näkyvä alue ja korvaa kaiken jo ladatun datan *(vaatii internetyhteyden)*
* **Lisää nyk. näkymä lataukseen** – lataa näytöllä näkyvän alueen ja yhdistä se jo ladattuun dataan *(vaatii internetyhteyden)*
* **Lataa joku muu alue** – näyttää lomakkeen, johon voit syöttää koordinaatit, etsiä paikkaa tai käyttää nykyistä sijaintia, ja ladata sitten alueen kyseisen paikan ympäriltä *(vaatii internetyhteyden)*
* **Lähetä OSM-palvelimelle** – lähetä muokkaukset OpenStreetMappiin *(vaatii tunnistautumisen)* *(vaatii internetyhteyden)*
* **Automaattilataus** – lataa automaattisesti alue nykyisen maantieteellisen sijainnin ympäriltä *(vaatii internetyhteyden)* *(vaatii GPS:n)*
* **Tiedosto...** – OSM-datan tallennus ja lataus laitteen muistiin/muistista
* **Muistiinpanot/Virheet...** – lataa (automaattisesti tai käsin) OSM-muistiinpanoja tai -virheitä QA-työkaluista (nykyisin OSMOSE) *(vaatii internetyhteyden)*

Helpoin tapa ladata dataa laitteelle on etsiä muokattavaksi aiottu karttanäkymä ja avata valikosta "Lataa nykyinen näkymä". Voit suurentaa ja pienentää sormieleillä, plus- ja miinusnapilla tai laitteen äänenvoimakkuusnappuloilla. Vespuccin pitäisi sitten ladata karttanäkymää vastaava data. Sinun ei tarvitse tunnistautua datan lataamista varten.

### Muokkaaminen

<a id="lock"></a>

#### Lock, unlock, mode switching

Tahattomien muokkausten välttämiseksi Vespucci käynnistyy lukitussa tilassa, jossa voi vain siirtää ja suurentaa karttaa. Kosketa ![lukko](../images/locked.png)-kuvaketta niin lukitus menee pois päältä. 

A long press on the lock icon will display a menu currently offering 4 options:

* **Normal** - the default editing mode, new objects can be added, existing ones edited, moved and removed. Simple white lock icon displayed.
* **Tag only** - selecting an existing object will start the Property Editor, a long press on the main screen will add objects, but no other geometry operations will work. White lock icon with a "T" is displayed.
* **Indoor** - enables Indoor mode, see [Indoor mode](#indoor). White lock icon with a "I" is displayed.
* **C-Mode** - enables C-Mode, only objects that have a warning flag set will be displayed, see [C-Mode](#c-mode). White lock icon with a "C" is displayed.

#### Napautus, tuplanapautus ja pitkä painallus

Valittavissa olevia pisteitä ja viivoja ympäröi oletuksena oranssi alue, joka näyttää suurinpiirtein mihin pitää napauttaa kohteen valitsemiseksi. Vaihtoehtoja on kolme:

* Single tap: Selects object. 
    * An isolated node/way is highlighted immediately. 
    * However, if you try to select an object and Vespucci determines that the selection could mean multiple objects it will present a selection menu, enabling you to choose the object you wish to select. 
    * Selected objects are highlighted in yellow. 
    * For further information see [Node selected](Node%20selected.md), [Way selected](Way%20selected.md) and [Relation selected](Relation%20selected.md).
* Double tap: Start [Multiselect mode](Multiselect.md)
* Long press: Creates a "crosshair", enabling you to add nodes, see below and [Creating new objects](Creating%20new%20objects.md). This is only enabled if "Simple mode" is deactivated.

Kannattaa lähentää näkymää tiheään kartoitettua aluetta muokattaessa.

Vespuccissa on hyvä peruutus- ja palautustoiminnot, joten voit huoletta tehdä kokeiluja laitteellasi. Älä kuitenkaan lähetä tai tallenna puhdasta testidataa.

#### Valitseminen ja valinnan poisto (yksi napautus ja "valintavalikko")

Touch an object to select and highlight it. Touching the screen in an empty region will de-select. If you have selected an object and you need to select something else, simply touch the object in question, there is no need to de-select first. A double tap on an object will start [Multiselect mode](Multiselect.md).

Huomaa että jos Vespucci ei valintatilanteessa ole varma mitä kohdetta yrität valita (vaikkapa viivan pistettä tai muita päällekkäisiä kohteita), sinulle näytetään valintavalikko: kosketa kohdetta, jonka haluat valita, ja valinta on valmis. 

Valitut kohteet ilmaistaan ohuen keltaisen reunuksen avulla. Keltaista reunusta voi olla vaikea huomata taustakartasta ja suurennuksesta riippuen. Valinnan toteuduttua näytetään siitä vahvistukseksi ilmoitus.

Once the selection has completed you will see (either as buttons or as menu items) a list of supported operations for the selected object: For further information see [Node selected](Node%20selected.md), [Way selected](Way%20selected.md) and [Relation selected](Relation%20selected.md).

#### Valitut kohteet: tägien muokkaaminen

Uusi kosketus valittuun kohteeseen avaa tägimuokkaimen, jossa voit muokata kohteelle kuuluvia tägejä.

Huomaa että päällekkäisten kohteiden tapauksessa (kuten piste viivalla) valintavalikko näytetään uudelleen. Saman kohteen valitseminen avaa tägimuokkaimen, mutta jos valitset jonkun toisen kohteen, tämä yksinkertaisesti valitaan.

#### Valitut kohteet: Pisteen tai viivan siirtäminen

Kun jokin kohde on valittuna, sitä voi siirtää. Huomaa että kohteita voi siirtää vain, jos ne on valittu. Siirto tehdään yksinkertaisesti vetämällä kohdetta sen läheltä (toleranssialueen sisältä). Asetuksista voit valita laajan vetoalueen valitun kohteen ympärille, jolloin sen siirtäminen tarkasti tiettyyn kohtaan on helpompaa. 

#### Adding a new Node/Point or Way 

On first start the app launches in "Simple mode", this can be changed in the main menu by un-checking the corresponding checkbox.

##### Simple mode

Tapping the large green floating button on the map screen will show a menu. After you've selected one of the items, you will be asked to tap the screen at the location where you want to create the object, pan and zoom continues to work if you need to adjust the map view. 

See [Creating new objects in simple actions mode](Creating%20new%20objects%20in%20simple%20actions%20mode.md) for more information.

##### Advanced (long press) mode
 
Long press where you want the node to be or the way to start. You will see a black "crosshair" symbol. 
* If you want to create a new node (not connected to an object), click away from existing objects.
* If you want to extend a way, click within the "tolerance zone" of the way (or a node on the way). The tolerance zone is indicated by the areas around a node or way.

Kun hiusristikko on asetettu, on valittavana seuraavat toimenpiteet:

* Kosketus samaan paikkaan.
    * Ellei hiusristikko ole lähellä pistettä, saman kohdan koskettaminen lisää uuden pisteen. Jos se on lähellä viivaa (mutta ei pistettä), uusi piste lisätään viivaan (ja on siis siinä kiinni).
    * Jos hiusristikko on lähellä pistettä (eli pisteen toleranssialueen sisällä), saman kohdan koskettaminen vain valitsee pisteen (ja tägimuokkain aukeaa). Uutta pistettä ei lisätä. Toiminto on sama kuin edellä kuvattu valitseminen.
* Kosketus toiseen paikkaan. Toisen kohdan (hiusristikon toleranssialueen ulkopuolella) koskettaminen lisää viivanpätkän ensimmäisestä kohdasta jälkimmäiseen. Jos hiusristikko oli lähellä viivaa tai pistettä, uusi pätkä on kiinni tässä pisteessä tai viivassa.

Voit lisätä viivaan lisää pisteitä koskettamalla näyttöä haluamiisi kohtiin. Lopetus tapahtuu koskettamalla viimeistä pistettä kahdesti. Jos viimeinen piste on jonkin viivan tai pisteen kohdalla, uusi viiva kiinnittyy siihen automaattisesti. 

You can also use a menu item: See [Creating new objects](Creating%20new%20objects.md) for more information.

#### Alueen lisääminen

OpenStreetMap currently doesn't have an "area" object type unlike other geo-data systems. The online editor "iD" tries to create an area abstraction from the underlying OSM elements which works well in some circumstances, in others not so. Vespucci currently doesn't try to do anything similar, so you need to know a bit about the way areas are represented:

* _suljetut viivat (*polygonit")_: yksinkertaisin ja yleisin aluetyyppi on viiva, jolla on sama alku- ja loppupiste, muodostaen suljetun "renkaan" (esimerkiksi useimmat rakennukset ovat tälläisiä). Tämä on erittäin helppo tehdä Vespuccilla: riittää että liität viimeisen pätkän takaisin viivan ensimmäiseen pisteeseen, kun olet saanut alueen piirrettyä. Huom: suljetun viivan tulkinta riippuu sen tägeistä; jos suljettu viiva on tägätty vaikkapa rakennukseksi, se käsitetään alueeksi, mutta jos se on tägätty kiertoliittymäksi, niin näin ei ole. Joissain tilanteissa, missä kumpikin tulkinta on mahdollinen, area-tägillä voi selventää viivan käyttötarkoituksen.
* _moni-monikulmio_: joissain alueissa on useita osia, reikiä ja renkaita, joita ei kertakaikkiaan voi esittää yhdellä viivalla. Ongelman kiertämiseksi OSM käyttää tietyntyyppistä relaatiota (yleiskäyttöinen elementti, jolla voi mallintaa elementtien välisiä suhteita), nimittäin "moni-monikulmiota". Moni-monikulmiossa voi olla monta "ulkorengasta" ja monta "sisärengasta". Joka rengas voi olla edellä kuvattu suljettu viiva tai muodostua monesta yksittäisestä viivasta, joilla on yhteiset päätepisteet. Vaikka laajoja moni-monikulmioita on hankala käsitellä millä tahansa työkalulla, ei pienten tekeminen Vespuccilla ole vaikeaa. 
* _rantaviivat_: todella laajojen kohteiden kuten mannerten ja saarten mallinnuksessa edes moni-monikulmio ei toimi tyydyttävällä tavalla. Viivoilla, joilla on tägi natural=coastline, oletetaan olevan suunnasta riippuva merkitys: maa on viivan vasemmalla ja vesi oikealla puolella. Tämän sivuvaikutus on se, että rantaviivaksi tägätyn viivan suuntaa ei (yleisesti ottaen) pidä kääntää. Lisätietoa on saatavilla [OSM-wikistä](http://wiki.openstreetmap.org/wiki/Tag:natural%3Dcoastline).

#### Viivan muodon parantaminen

Jos lähennät valittua viivaa tarpeeksi, voit nähdä pienen x-merkin riittävän pitkien viivanpätkien keskikohdassa. Pikku-x:n vetäminen lisää viivaan kyseiseen kohtaan pisteen. Huom: jottei pisteitä tulisi luotua vahingossa, tämän toiminnon toleranssialue on verrattain pieni.

#### Leikkaa, kopioi & liitä

Voit kopioida ja leikata sekä pisteitä että viivoja, ja liittää ne sitten kerran tai monesti uuteen paikkaan. Leikkaaminen säilyttää OSM-tunnuksen ja -version. Liittäminen aloitetaan painamalla pitkään kohtaa, johon halutaan liittää (merkiksi ilmestyy hiusristikko). Sitten valikosta valitaan "Liitä".

#### Osoitteiden lisääminen kätevästi

Vespuccissa on toiminto "lisää osoitetägit", jonka tarkoitus on tehdä osoitteiden syöttämisestä kätevämpää. Se voidaan suorittaa:

* pitkän painalluksen jälkeen: Vespucci lisää ko. paikkaan pisteen ja tekee parhaansa osoitenumeron ja muiden lähiaikoina käyttämiesi osoitetägien arvaamiseksi. Jos piste on rakennuksen ääriviivalla, lisätään siihen automaattisesti tägi "entrance=yes". Piste avataan tägimuokkaimeen, jotta voit tarpeen vaatiessa tehdä muutoksia.
* tiloissa piste/viiva valittuna: Vespucci lisää osoitetägit kuten yllä ja avaa tägimuokkaimen.
* tägimuokkaimessa.

Tyypillisesti osoitenumeron arvaus vaatii toimiakseen vähintään kahden osoitenumeron syöttämisen kadun molemmin puolin. Mitä enemmän numeroita on valmiina, sen parempi.

Kannattaa harkita käyttöä yhdessä [automaattilatauksen](#download) kanssa.  

#### Kääntymisrajoitusten lisääminen

Vespuccissa on nopea tapa lisätä kääntymisrajoituksia. Tarvittaessa viivat jaetaan automaattisesti kahtia, ja sinua pyydetään valitsemaan kohteet uudelleen. 

* valitse viiva, jossa on highway-tägi (kääntymisrajoituksia voi lisätä vain maanteille; jos tarvitset rajoitusta muille viivoille, käytä yleisluontoista toimintoa "luo relaatio")
* valitse valikosta "Lisää rajoitus"
* valitse "kautta"-piste tai viiva (kosketusalue näytetään vain mahdollisille "kautta"-elementeille)
* valitse "mihin"-viiva (on mahdollista asettaa "mihin"-elementti samaksi kuin "mistä"-elementti, jolloin Vespucci olettaa sinun olevan lisäämässä "ei U-käännöstä" -rajoitusta (no_u_turn))
* aseta rajoitustyyppi

### Vespucci lukitustilassa

Kun näkyvissä on punainen lukko, voidaan käyttää kaikkia epämuokkaavia toimintoja. Lisäksi pitkä painallus kohteen päällä tai lähellä näyttää kohteesta yksityiskohtaisia tietoja, jos se on OSM-elementti.

### Muutosten tallentaminen

*(vaatii internetyhteyden)*

Valitse sama nappi tai valikkokohta kuin ladattaessa, mutta valitse nyt "Lähetä data OSM-palvelimelle".

Vespucci tukee OAuth-todennusta sekä perinteisiä käyttäjätunnusta ja salasanaa. Suositeltu tapa on OAuth, koska sen avulla vältetään salasanojen lähettäminen selkokielisinä.

Uusissa Vespucci-asennuksissa on oletuksena käytössä OAuth. Lähettäessäsi muokattua dataa ensimmäistä kertaa, avautuu näytölle sivu OSM:n verkkosivustolta. Kirjauduttuasi sisään (salattua yhteyttä pitkin) sinua pyydetään antamaan Vespuccille lupa tehdä muokkauksia tilisi kautta. Jos haluat tai sinun täytyy antaa lupa OAuth-kirjautumiseen jo ennen muokkaamista, on työkaluvalikossa toiminto tätä varten.

Jos haluat tallentaa työsi tulokset, mutta sinulla ei ole internetyhteyttä, voit tallentaa ne JOSMin ymmärtämäksi .osm-tiedostoksi, jonka voi myöhemmin lähettää Vespuccilla tai JOSMilla. 

#### Lähetyksen ristiriitojen selvittäminen

Vespucci has a simple conflict resolver. However if you suspect that there are major issues with your edits, export your changes to a .osc file ("Export" menu item in the "Transfer" menu) and fix and upload them with JOSM. See the detailed help on [conflict resolution](Conflict%20resolution.md).  

## GPS:n käyttäminen

Vespuccilla voit luoda GPX-jälkiä ja katsella niitä laitteellasi. Lisäksi voit näyttää nykyisen GPS-sijaintisi (aseta "Näytä sijainti" GPS-valikosta) ja/tai keskittää näkymän sijaintiin ja seurata sitä (aseta "Seuraa GPS-sijaintia" GPS-valikosta). 

Jos jälkimmäinen asetus on päällä, näkymän liikuttaminen käsin tai sen muokkaaminen poistaa GPS:n seurannan, ja sininen GPS-nuoli muuttuu ääriviivasta täytetyksi nuoleksi. Pääset nopeasti takaisin seurantatilaan koskettamalla GPS-nappia, tai uudelleen valikon kautta.

## Muistiinpanot ja virheet

Vespucci supports downloading, commenting and closing of OSM Notes (formerly OSM Bugs) and the equivalent functionality for "Bugs" produced by the [OSMOSE quality assurance tool](http://osmose.openstreetmap.fr/en/map/). Both have to either be down loaded explicitly or you can use the auto download facility to access the items in your immediate area. Once edited or closed, you can either upload the bug or Note immediately or upload all at once.

Kartalla muistiinpanot ja virheet näytetään pienellä ötökkäkuvakkeella ![Bug](../images/bug_open.png): vihreät ovat suljettuja/selvitettyjä, siniset ovat käyttäjän luomia tai muokkaamia ja keltainen väri osoittaa, ettei sitä ole vielä korjattu eikä muutettu. 

OSMOSE-virheiden tiedoissa on sininen linkki kyseessä olevaan kohteeseen, ja linkin koskettaminen valitsee kohteen, keskittää näkymän siihen ja lataa tarvittaessa alueen valmiiksi. 

### Suodattaminen

Besides globally enabling the notes and bugs display you can set a coarse grain display filter to reduce clutter. In the [Advanced preferences](Advanced%20preferences.md) you can individually select:

* Notes
* Osmose error
* Osmose warning
* Osmose minor issue
* Custom

<a id="indoor"></a>

# Sisätila-tila

Mapping indoors is challenging due to the high number of objects that very often will overlay each other. Vespucci has a dedicated indoor mode that allows you to filter out all objects that are not on the same level and which will automatically add the current level to new objects created there.

The mode can be enabled by long pressing on the lock item, see [Lock, unlock, mode switching](#lock) and selecting the corresponding menu entry.

<a id="c-mode"></a>

## C-Mode

In C-Mode only objects are displayed that have a warning flag set, this makes it easy to spot objects that have specific problems or match configurable checks. If an object is selected and the Property Editor started in C-Mode the best matching preset will automatically be applied.

The mode can be enabled by long pressing on the lock item, see [Lock, unlock, mode switching](#lock) and selecting the corresponding menu entry.

### Configuring checks

Currently there are two configurable checks (there is a check for FIXME tags and a test for missing type tags on relations that are currently not configurable) both can be configured by selecting "Validator preferences" in the "Preferences". 

The list of entries is split in to two, the top half lists "re-survey" entries, the bottom half "check entries". Entries can be edited by clicking them, the green menu button allows adding of entries.

#### Re-survey entries

Re-survey entries have the following properties:

* **Key** - Key of the tag of interest.
* **Value** - Value the tag of interest should have, if empty the tag value will be ignored.
* **Age** - how many days after the element was last changed the element should be re-surveyed, if a check_date field is present that will be the used, otherwise the date the current version was create. Setting the value to zero will lead to the check simply matching against key and value.
* **Regular expression** - if checked **Value** is assumed to be a JAVA regular expression.

**Key** and **Value** are checked against the _existing_ tags of the object in question.

#### Check entries

Check entries have the following two properties:

* **Key** - Key that should be present on the object according to the matching preset.
* **Require optional** - Require the key even if the key is in the optional tags of the matching preset .

This check works by first determining the matching preset and then checking if **Key** is a "recommended" key for this object according to the preset, **Require optional** will expand the check to tags that are "optional* on the object. Note: currently linked presets are not checked.

## Suodattimet

### Tägipohjainen suodatin

The filter can be enabled from the main menu, it can then be changed by tapping the filter icon. More documentation can be found here [Tag filter](Tag%20filter.md).

### Esivalintapohjainen suodatin

An alternative to the above, objects are filtered either on individual presets or on preset groups. Tapping on the filter icon will display a preset selection dialog similar to that used elsewhere in Vespucci. Individual presets can be selected by a normal click, preset groups by a long click (normal click enters the group). More documentation can be found here [Preset filter](Preset%20filter.md).

## Vespuccin räätälöiminen

### Asetuksia jotka ehkä haluat muuttaa

* Background layer
* Overlay layer. Adding an overlay may cause issues with older devices and such with limited memory. Default: none.
* Notes/Bugs display. Open Notes and bugs will be displayed as a yellow bug icon, closed ones the same in green. Default: on.
* Photo layer. Displays geo-referenced photographs as red camera icons, if direction information is available the icon will be rotated. Default: off.
* Keep screen on. Default: off.
* Large node drag area. Moving nodes on a device with touch input is problematic since your fingers will obscure the current position on the display. Turning this on will provide a large area which can be used for off-center dragging (selection and other operations still use the normal touch tolerance area). Default: off.

#### Lisäasetukset

* Node icons. Default: on.
* Always show context menu. When turned on every selection process will show the context menu, turned off the menu is displayed only when no unambiguous selection can be determined. Default: off (used to be on).
* Enable light theme. On modern devices this is turned on by default. While you can enable it for older Android versions the style is likely to be inconsistent.
* Show statistics. Will show some statistics for debugging, not really useful. Default: off (used to be on).  

## Ongelmista ilmoittaminen

If Vespucci crashes, or it detects an inconsistent state, you will be asked to send in the crash dump. Please do so if that happens, but please only once per specific situation. If you want to give further input or open an issue for a feature request or similar, please do so here: [Vespucci issue tracker](https://github.com/MarcusWolschon/osmeditor4android/issues). The "Provide feedback" function from the main menu will open a new issue and include the relevant app and device information without extra typing.

If you want to discuss something related to Vespucci, you can either start a discussion on the [Vespucci Google group](https://groups.google.com/forum/#!forum/osmeditor4android) or on the [OpenStreetMap Android forum](http://forum.openstreetmap.org/viewforum.php?id=56)


