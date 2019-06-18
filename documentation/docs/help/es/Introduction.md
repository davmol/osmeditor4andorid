# Introducción a Vespucci

Vespucci es un completo editor de OpenStreetMap que soporta la mayoría de operaciones que proveen los editores de escritorio. Ha sido probado con éxito en Android 2.3 a 7.0 de Google y varias variantes basadas en AOSP. Un aviso: aunque las capacidades de los dispositivos móviles han alcanzado a sus rivales de escritorio, particularmente los dispositivos más antiguos tienen memoria disponible muy limitada y tienden a ser bastante lentos. Debería tener esto en cuenta cuando utilice Vespucci y mantenga, por ejemplo, el tamaño de las áreas que está editando en un tamaño razonable. 

## Utilizar por primera vez

Al iniciarse Vespucci le muestra el diálogo "Descargar otra ubicación"/"Cargar área". Si usted tiene coordenadas expuestas y quiere descargar inmediatamente, puede seleccionar la opción apropiada e introducir el radio de alrededor de la ubicación que quiera descargar. No seleccione un área amplia en dispositivos lentos. 

Como alternativa, puede cerrar el diálogo pulsando el botón «Ir al mapa» y alejar o acercar a una ubicación que quiera editar y descargar los datos a continuación (vea más adelante: «Editando con Vespucci»).

## Editando con Vespucci

Dependiendo del tamaño de pantalla y antigüedad de su dispositivo, las acciones de edición pueden estar accesibles directamente por medio de iconos en la barra superior, en un menú desplegable a la derecha de la barra superior, desde la barra inferior (si está presente) o por medio de la tecla de menú.

<a id="download"></a>

## Descargando datos OSM

Seleccione el ícono de transferencia ![Transferir](../images/menu_transfer.png) o la opción del menú «Transferir». Esto mostrará siete opciones:

* **Descargar la vista actual**: descarga el área visible en la pantalla y reemplaza cualquier información existente *(requiere conexión de red)*
* **Añadir la vista actual a descarga**: descarga el área visible en la pantalla y la combina con la información existente *(requiere conexión de red)*
* **Descargar otra ubicación**: muestra un formulario que le permite introducir coordenadas, buscar una ubicación o utilizar la ubicación actual, y después descargar un área de alrededor de la ubicación *(requiere conexión de red)*
* **Subir datos al servidor OSM**: carga ediciones a OpenStreetMap *(requiere autenticación)* *(requiere conexión de red)*
* **Autodescarga**: descarga un área alrededor de la ubicación actual automáticamente *(requiere conexión de red)* *(requiere GPS)*
* **Archivo...**: Guardar y cargar información OSM a/desde archivos del dispositivo.
* **Nota/Errores...**: descarga (automática y manualmente) Notas y "Errores" OSM desde herramientas QA (actualmente OSMOSE) *(requiere conexión de red)*

La manera más fácil de descargar información al dispositivo es hacer zoom y marcar la ubicación que quiere editar y después seleccionar "Descargar vista actual". Puede hacer zoom utilizando gestos, como los botones de zoom o los botones de control de volumen del dispositivo. Vespucci debería descargar entonces información de la vista actual. No se requiere autenticación para descargar información a su dispositivo.

## Editando

<a id="lock"></a>

#### Cambio de modo, bloquear, desbloquear

Para evitar ediciones accidentales, Vespucci comienza en modo "bloqueado", un modo que sólo permite hacer zoom y mover el mapa. Toque el icono ![Bloqueado](../images/locked.png) para desbloquear la pantalla. 

Una pulsación larga en el icono del candado mostrará un menú con cuatro opciones:

* **Normal** - el modo de edición por defecto, se pueden añadir nuevos objetos, editar los existentes, desplazados o eliminados. Se muestra un icono sencillo de un candado blanco.
* **Sólo etiquetado** - al seleccionar un objeto existente se iniciará el Editor de Propiedades, una pulsación larga en la pantalla principal añadirá objetos, pero no funcionarán otras operaciones geométricas. Se mostrará el icono de un candado blanco con una "T".
* **Interiores** - activa el modo Interiores, ver [modo Interiores](#indoor). Se muestra el icono de un candado blanco con una "I".
* **Modo-C** - activa el Modo-C, sólo se muestran los objetos que tengan una señal de aviso, ver [Modo-C](#c-mode). Se muestra el icono de un candado blanco con una "C".

#### Pulsación simple, pulsación doble y pulsación larga

Por defecto, los nodos y vías selecionables tienen un área naranja a su alrededor indicando aproximadamente dónde tiene que tocar para seleccionar un objeto. Tiene tres opciones:

* Single tap: Selects object. 
    * An isolated node/way is highlighted immediately. 
    * However, if you try to select an object and Vespucci determines that the selection could mean multiple objects it will present a selection menu, enabling you to choose the object you wish to select. 
    * Selected objects are highlighted in yellow. 
    * For further information see [Node selected](Node%20selected.md), [Way selected](Way%20selected.md) and [Relation selected](Relation%20selected.md).
* Double tap: Start [Multiselect mode](Multiselect.md)
* Long press: Creates a "crosshair", enabling you to add nodes, see below and [Creating new objects](Creating%20new%20objects.md). This is only enabled if "Simple mode" is deactivated.

Es una buena estrategia acercar el mapa si intenta editar un área con alta densidad.

Vespucci tiene un buen sistema «deshacer/rehacer» así que no tenga miedo de experimentar en su dispositivo, sin embargo no suba ni guarde los datos de prueba.

#### Seleccionando / Deseleccionando (pulsación simple y "menú de selección")

Touch an object to select and highlight it. Touching the screen in an empty region will de-select. If you have selected an object and you need to select something else, simply touch the object in question, there is no need to de-select first. A double tap on an object will start [Multiselect mode](Multiselect.md).

Tenga en cuenta que si intenta seleccionar un objeto y Vespucci determina que la selección se podría referir a varios objetos (tales como un nodo en una vía u otros objetos superpuestos) se presentará un menú de selección: pulse en el objeto que quiera seleccionar y el objeto se seleccionará. 

Los objetos seleccionados se indican con un fino borde amarillo. Puede que el borde amarillo sea difícil de ver, dependiendo en el fondo del mapa y el nivel de zoom. Una vez que se ha hecho una selección, verá una notificación confirmando la selección.

Once the selection has completed you will see (either as buttons or as menu items) a list of supported operations for the selected object: For further information see [Node selected](Node%20selected.md), [Way selected](Way%20selected.md) and [Relation selected](Relation%20selected.md).

#### Objetos seleccionados: Editando etiquetas

Un segundo toque en el objeto seleccionado abre el editor de etiquetas y puede editar las etiquetas asociadas al objeto.

Tenga en cuenta que para superponer objetos (como un nodo en un camino) el menú de selección vuelve a subir por segunda vez. Al seleccionar el mismo objeto, aparece el editor de etiquetas; seleccionar otro objeto simplemente selecciona el otro objeto.

#### Objetos seleccionados: Moviendo un nodo o vía

Una vez que haya seleccionado un objeto, puede moverlo. Tenga en cuenta que los objetos sólo se pueden arrastrar/mover cuando se seleccionan. Simplemente arrastre cerca (es decir, dentro de su zona de tolerancia) el objeto seleccionado para moverlo. Si selecciona el área de arrastre grande en las preferencias, obtiene un área grande alrededor del nodo seleccionado que facilita la colocación del objeto. 

#### Adding a new Node/Point or Way 

On first start the app launches in "Simple mode", this can be changed in the main menu by un-checking the corresponding checkbox.

##### Simple mode

Tapping the large green floating button on the map screen will show a menu. After you've selected one of the items, you will be asked to tap the screen at the location where you want to create the object, pan and zoom continues to work if you need to adjust the map view. 

See [Creating new objects in simple actions mode](Creating%20new%20objects%20in%20simple%20actions%20mode.md) for more information.

##### Advanced (long press) mode
 
Long press where you want the node to be or the way to start. You will see a black "crosshair" symbol. 
* If you want to create a new node (not connected to an object), click away from existing objects.
* If you want to extend a way, click within the "tolerance zone" of the way (or a node on the way). The tolerance zone is indicated by the areas around a node or way.

Una vez pueda ver el símbolo de la cruz, tiene estas opciones:

* Toque en el mismo lugar.
    * Si el punto de mira no está cerca de un nodo, tocar nuevamente la misma ubicación crea un nuevo nodo. Si usted está cerca de un camino (pero no cerca de un nodo), el nuevo nodo estará en camino (y conectado al camino).
    * Si la cruz está cerca de un nodo (es decir, dentro de la zona de tolerancia del nodo), al tocar la misma ubicación solo se selecciona el nodo (y se abre el editor de etiquetas. No se crea un nuevo nodo. La acción es la misma que la selección anterior.
* Toque otro lugar. Al tocar otra ubicación (fuera de la zona de tolerancia de la cruz) se añade un segmento de camino desde la posición original a la posición actual. Si la cruz estaba cerca de un camino o nodo, el nuevo segmento se conectará a ese nodo o camino.

Simplemente toque la pantalla donde desea agregar más nodos del camino. Para finalizar, toque el nodo final dos veces. Si el nodo final está ubicado en una ruta o nodo, el segmento se conectará automáticamente a la ruta o al nodo. 

You can also use a menu item: See [Creating new objects](Creating%20new%20objects.md) for more information.

#### Añadiendo un Área

Actualmente OpenStreetMap no tiene un objeto tipo "área" al contrario que otros sistemas de geo-datos. El editor en línea "iD" intenta crear una abstracción de área a partir de los elementos OSM subyacentes que funciona bien en algunas circunstancia y no tanto en otras. Actualmente Vespucci no intenta hacer nada similar, así que necesitará conocer un poco sobre la forma en que se representan las áreas.

* _rutas cerradas («polígonos»)_: la variante de área más simple y más común, son rutas que tienen un primer y último nodo compartido formando un «anillo» cerrado (por ejemplo, la mayoría de los edificios son de este tipo). Estos son muy fáciles de crear en Vespucci, simplemente conéctese de nuevo al primer nodo cuando haya terminado de dibujar el área. Nota: la interpretación del camino cerrado depende de su etiquetado: por ejemplo, si un camino cerrado se etiqueta como un edificio, se considerará un área, si se etiqueta como una rotonda, no. En algunas situaciones en las que ambas interpretaciones pueden ser válidas, una etiqueta de «área» puede aclarar el uso previsto.
* _multi-polígonos_: algunas áreas tienen múltiples partes, agujeros y anillos que no se pueden representar de una sola manera. OSM usa un tipo específico de relación (nuestro objeto de propósito general que puede modelar las relaciones entre los elementos) para sortear esto, un multi-polígono. Un multi-polígono puede tener múltiples anillos «externos» y múltiples anillos «internos». Cada anillo puede ser cerrado como se describe anteriormente, o múltiples formas individuales que tienen nodos finales comunes. Mientras que los grandes multi-polígonos son difíciles de manejar con cualquier herramienta, los pequeños no son difíciles de crear en Vespucci. 
* _costas_: para objetos muy grandes, continentes e islas, incluso el modelo de multi-polígonos no funciona de manera satisfactoria. Para las formas naturales=litorales asumimos una semántica dependiente de la dirección: la tierra está en el lado izquierdo del camino, el agua en el lado derecho. Un efecto secundario de esto es que, en general, no debe invertir la dirección de una ruta con el etiquetado de la costa. Puede encontrarse más información en la [OSM wiki](http://wiki.openstreetmap.org/wiki/Tag:natural%3Dcoastline).

#### Mejorando la geometría de vías

Si hace el suficiente zoom en una ruta seleccionada, verá pequeños segmentos «x» en medio de la ruta que son lo suficientemente largos. Al arrastrar la «x» se creará un nodo en la ruta en esa ubicación. Nota: para evitar la creación accidental de nodos, el área de tolerancia táctil para esta operación es bastante pequeña.

#### Cortar, copiar y pegar

Usted puede copiar o cortar los nodos y caminos seleccionados, y después pegarlos una o múltiples veces en una nueva ubicación. Cortar retendrá la ID y la versión de osm. Para pegar presione un rato la ubicación en la que desea pegar (verá una línea cruzada creando la ubicación). Después seleccione "Pegar" desde el menú.

#### Añadiendo direcciones de manera eficiente

Vespucci tiene una función de «agregar etiquetas de dirección» que intenta hacer las direcciones topográficas más eficientes. Puede ser seleccionado:

* tras una larga presión: Vespucci agregará un nodo en la ubicación y hará una mejor estimación del número de la casa y añadirá las etiquetas de dirección que ha estado utilizando últimamente. Si el nodo está en el contorno de un edificio, añadirá automáticamente una etiqueta de «entrada=sí» al nodo. El editor de etiquetas se abrirá para el objeto en cuestión y le permitirá realizar los cambios adicionales necesarios.
* en los modos nodo/ruta seleccionados: Vespucci agregará las etiquetas de dirección como se indica arriba e iniciará el editor de etiquetas.
* en el editor de etiquetas.

La predicción de números de casas normalmente requiere al menos dos números de casas a cada lado de la vía para que funcione; cuantos más números presentes en los datos, mejor.

Considere utilizar esto con el modo [Auto-descarga](#download).  

#### Añadiendo restricciones de giro

Vespucci tiene una manera rápida de agregar restricciones de giro. si es necesario, dividirá las formas automáticamente y le pedirá que vuelva a seleccionar los elementos. 

* seleccione una forma con una etiqueta de autovía (las restricciones de giro solo se pueden añadir a las autovías, si necesita hacer esto de otras maneras, utilice el modo genérico de «crear relación»)
* seleccione «Añadir restricción» desde el menú
* seleccione el nodo «vía» o ruta (solo los posibles elementos «via» tendrán el área táctil mostrada)
* seleccione la ruta «a» (es posible volver a doblar y configurar el elemento «a» al elemento «desde», Vespucci asumirá que está añadiendo una restricción no_u_turn)
* establezca el tipo de restricción

### Vespucci en modo «bloqueado»

Cuando se muestra el candado rojo todas las acciones no editables están disponibles. Adicionalmente una larga presión sobre o cerca de un objeto mostrará la pantalla de información detallada si es un objeto OSM.

### Guardando sus cambios

*(requiere conectividad de red)*

Seleccione el mismo botón o ítem del menú que hizo para la descarga y ahora seleccione «Subir datos al servidor OSM».

Vespucci soporta la autorización OAuth y el clásico método usuario y contraseña. OAuth es preferible ya que evita el envío de contraseñas en texto plano.

Las nuevas instalaciones de Vespucci tendrán habilitado OAuth por defecto. En su primer intento de cargar información modificada, se carga una página de OSM. Cuando usted se ha conectado (con una conexión encriptada) se le pedirá que autorice a Vespucci editar utilizando su cuenta. Si quiere o necesita autorizar el acceso OAuth a su cuenta antes de editar hay un artículo correspondiente en el menú "Herramientas".

Si quiere guardar su trabajo y no tiene acceso a Internet, puede guardar un archivo .osm compatible con JOSM y luego subir ya sea con Vespucci o con JOSM. 

#### Resolviendo conflictos al subir

Vespucci has a simple conflict resolver. However if you suspect that there are major issues with your edits, export your changes to a .osc file ("Export" menu item in the "Transfer" menu) and fix and upload them with JOSM. See the detailed help on [conflict resolution](Conflict%20resolution.md).  

## Usando GPS

Puede utilizar Vespucci para crear una pista GPX y mostrarla en su dispositivo. Además puede mostrar la ubicación GPS actual (seleccione "Mostrar ubicación" en el menú GPS) y/o centrar la pantalla y seguir su posición (seleccione "Seguir ubicación GPS" en el menú GPS). 

Si tiene este último conjunto, mover la pantalla manualmente o editar hará que se deshabilite el modo «Seguir GPS» y la flecha azul del GPS cambiará de un esquema a una flecha llena. Para volver rápidamente al modo «seguir», simplemente toque el botón GPS o vuelva a verificar la opción de menú.

## Notas y errores

Vespucci soporta la descarga, comentar y cerrar Notas de OSM (anteriormente Errores OSM) y la funcionalidad equivalente para "Errores" producidos por la [herramienta de aseguramiento de calidad OSMOSE](http://osmose.openstreetmap.fr/en/map/). Ambos deben de, o bien haber sido descargados explícitamente, o puedes usar el servicio de auto descarga para acceder a los elementos en su área inmediata. Una vez editados o cerrados, puede o bien subir el error o Nota al servidor de forma inmediata o subirlos todos de una vez.

En el mapa las Notas y errores son representados mediante un pequeño icono de error ![Error](../images/bug_open.png); los verdes son cerrados/resueltos, los azules han sido creados o editados por usted, y los amarillos indican que aún está activo y no ha sido cambiado. 

La exposición de error OSMOSE proveerá un enlace al objeto azul afectado, tocar el enlace seleccionará el objeto, centrará la pantalla sobre él y descargará el área con antelación si fuera necesario. 

### Filtrado

Besides globally enabling the notes and bugs display you can set a coarse grain display filter to reduce clutter. In the [Advanced preferences](Advanced%20preferences.md) you can individually select:

* Notes
* Osmose error
* Osmose warning
* Osmose minor issue
* Custom

<a id="indoor"></a>

## Modo de interiores

Mapping indoors is challenging due to the high number of objects that very often will overlay each other. Vespucci has a dedicated indoor mode that allows you to filter out all objects that are not on the same level and which will automatically add the current level to new objects created there.

El modo puede activarse con una pulsación larga en el icono del cansado, ver [Cambiando de modo, bloquear, desbloquear](#lock) y seleccionando la correspondiente entrada de menú.

<a id="c-mode"></a>

## Modo-C

En el Modo-C sólo se muestran los objetos que tienen activa una señal de aviso, esto facilita resaltar objetos que tienen problemas específicos o coinciden con chequeos configurables. Si un objeto es seleccionado y el Editor de Propiedades iniciado en Modo-C se aplicará automáticamente el preestablecido que mejor se ajuste.

El modo puede activarse con una pulsación larga en el icono del cansado, ver [Cambiando de modo, bloquear, desbloquear](#lock) y seleccionando la correspondiente entrada de menú.

### Configurando chequeos

Actualmente hay dos chequeos configurables (hay un chequeo para etiquetas FIXME y una prueba para etiquetas type ausentes en relaciones quite actualmente no sin configurables) ambos pueden configurarse seleccionando "Ajustes del validador" en las "Preferencias". 

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

## Filtros

### Filtro basado en etiqueta

The filter can be enabled from the main menu, it can then be changed by tapping the filter icon. More documentation can be found here [Tag filter](Tag%20filter.md).

### Filtro basado en preajustes

An alternative to the above, objects are filtered either on individual presets or on preset groups. Tapping on the filter icon will display a preset selection dialog similar to that used elsewhere in Vespucci. Individual presets can be selected by a normal click, preset groups by a long click (normal click enters the group). More documentation can be found here [Preset filter](Preset%20filter.md).

## Personalizando Vespucci

### Los ajustes que podría querer cambiar

* Background layer
* Overlay layer. Adding an overlay may cause issues with older devices and such with limited memory. Default: none.
* Notes/Bugs display. Open Notes and bugs will be displayed as a yellow bug icon, closed ones the same in green. Default: on.
* Photo layer. Displays geo-referenced photographs as red camera icons, if direction information is available the icon will be rotated. Default: off.
* Keep screen on. Default: off.
* Large node drag area. Moving nodes on a device with touch input is problematic since your fingers will obscure the current position on the display. Turning this on will provide a large area which can be used for off-center dragging (selection and other operations still use the normal touch tolerance area). Default: off.

Preferencias avanzadas

* Node icons. Default: on.
* Always show context menu. When turned on every selection process will show the context menu, turned off the menu is displayed only when no unambiguous selection can be determined. Default: off (used to be on).
* Enable light theme. On modern devices this is turned on by default. While you can enable it for older Android versions the style is likely to be inconsistent.
* Show statistics. Will show some statistics for debugging, not really useful. Default: off (used to be on).  

## Informar de problemas

If Vespucci crashes, or it detects an inconsistent state, you will be asked to send in the crash dump. Please do so if that happens, but please only once per specific situation. If you want to give further input or open an issue for a feature request or similar, please do so here: [Vespucci issue tracker](https://github.com/MarcusWolschon/osmeditor4android/issues). The "Provide feedback" function from the main menu will open a new issue and include the relevant app and device information without extra typing.

If you want to discuss something related to Vespucci, you can either start a discussion on the [Vespucci Google group](https://groups.google.com/forum/#!forum/osmeditor4android) or on the [OpenStreetMap Android forum](http://forum.openstreetmap.org/viewforum.php?id=56)


