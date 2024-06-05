# Rodent Reboot

Dette repoet inneholder INF112-prosjektet "Rodent Reboot", laget av gruppen Early Birds våren 2024.

Gruppen består av:

- Emil Hausvik
- Jan Petter Iversen
- Magnus Elias Sletten
- Isabell Philipp
- Kai Waløen


## Om spillet

Vi lager et top-down rogue-like spill. Spillet består av en spillkarakter, random-genererte kart, fiender og items.

Playeren er kontrollert med WASD og musen.
* WASD - generell bevegelse
* Left click - skyter en kule mot der det klikkes
* Right click - gjør spilleren immun mot skade i en kort periode

Du får poeng for å drepe fiender, og spillet har en highscore funksjon som lagres mellom instanser. Poengene per fiende øker med map-nivået du er på, men det gjør også styrken til fiendene.

Det finnes følgende item-power-ups:
* ArmorPU - gir damage reduction de neste 10 treffene
* BulletSpeedPU - Gir spilleren midlertidig raskere kuler
* FullHealthPU - Gir spilleren full HP
* HealthPU - Gir spilleren tilbake litt HP
* SpeedPU (1-3) - Dette power-up gir spilleren en midlertidig økning i hastighet. Det finnes tre varianter, merket fra 1 til 3. Hver variant gir samme hastighetsøkning, men varigheten av effekten øker med variantens verdi.


## Struktur

Prosjektet bruker libgdx og maven.


## Produksjon

### Kjør spillet

Spillet kan kjøres med følgende kommando: `maven exec:java`.

### Bygg spillet

Spillet kan bygges med kommandoen: `mvn package`

Dette produserer to kompilerte `.jar`-filer i `target/` - én `*-fat.jar`-fil som inneholder alle dependencies, og én uten.

Disse filene kan igjen kjøres med: `java -jar <filnavn.jar>`


## Kilder på ressurser

Made by Evangelia Koutsoukou aka Apocalypse, specifically for this project (https://www.facebook.com/EveOfTheApocalypse):

- bulletGreen.png
- bulletGreenLarge.png
- bulletGreenLargeCircle.png
- bulletOrange.png
- bulletOrangeLarge.png
- door.png
- enemy1.png
- floor.png
- fullHealthPU.png
- healthPU.png
- player.png
- speed1PU.png
- speed2PU.png
- speed3PU.png
- button.png
- panel.png
- gameoverPanel.png

GdxTestRunner was used under license from:
- https://github.com/TomGrill/gdx-testing/tree/master

Sprites we have gotten from other sources:
- font.png https://opengameart.org/content/oleaguid-font (lastet ned 12 mars 2024) (The Oleaguid font was made by Arynoc)
- shield.png https://opengameart.org/content/shield-aura-effect (lastet ned 26 april 2024)
- greenbar.png/yellowbar.png/redbar.png - https://opengameart.org/content/health-bars (lastet ned 27 april 2024)

Sounds:

- neon-fury-clipped.wav - https://pixabay.com/music/upbeat-neon-fury-201183/ (lastet ned 04.mai 2024 og modifisert av Kai Waløen)
- laser.wav - https://freesound.org/people/MATRIXXX_/sounds/523205/ (lastet ned 05.mai 2024)
- itemPickup.wav - https://opengameart.org/content/win-sound-2 (lastet ned 12.april 2024)
- door.wav - https://opengameart.org/content/door-open-door-close (lastet ned 12.april 2024 og konvertert til wav)
- playerDamage.wav - https://opengameart.org/content/sci-fi-trooper-voice-pack-54-lines (lastet ned 05.mars 2024)
- enemyDeath.wav - https://opengameart.org/content/4-projectile-launches (lastet ned 05 mars 2024)
- playerDefeat.wav - https://opengameart.org/content/sci-fi-trooper-voice-pack-54-lines (lastet ned 12.april 2024)
- newHighscore.wav - https://opengameart.org/content/highscore-0 (lastet ned og konvertert 03.mai 2024)
