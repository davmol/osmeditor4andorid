# Vespucci - An OpenStreetMap editor for Android

original soruce: https://github.com/MarcusWolschon/osmeditor4android

## Version with location based editing

In regard of improved GNNS Technologie i.e.: Dual Band GPS in todays smartphones an even finer positing is possible. With this option in better precision locating several new usecases come to reality. Especially in the mapping rather than the surveying world, this new development comes in handy to quickly aquire spatial data. In combination with the Vespucci App - which offers a whole editor and viewer for OpenStreetMap (OSM) Data - an more accurate positioning improves the creating of OSM geodata or helps to improve existing data. Therefore we implemented some changes to the oringinal Vespucci App to make this described usecase possible. It is now possible to switch between the regular editing functions and the new implemented GNNS functionality for positioning. 

![alt text](http://i67.tinypic.com/5b7l5.jpg | width=400 "Regular Vespucci Edting Mode")


![alt text](http://i67.tinypic.com/s5g1sk.jpg | width=400 "Enabled GNNS Locating for editing")
LongClick on the Locating Button activetes the positioning from GNNS



## Outlook
The Vespucci App offers a desktop like functionallity in editing and viewing OSM Data. Unfortunately the GUI is a litte too clutterd to make it intutive for unexperienced users. Particularly the reediting for OSM Elements is complicated. An Easy to use GUI for exactly this purpose, where users can choose an existing OSM Element an redefine or adjust its shapes and coordinates, is still pending.

A theretical precicion in positioning up to +/- 30 cm could be archived with Dual Band GPS, however the reality is a bit more complicated. Interferences that comes from reflections worsens that threshold a lot. Nevertheless an improvement with the https://www.flamingognss.com could be a corrective in further projects.

