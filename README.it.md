# NextOS

### Un Sistema Operativo Fantasy per il Retro Computing Moderno

NextOS è un sistema operativo fantasy open-source progettato come evoluzione moderna dei home computer degli anni '80. Fornisce un ambiente virtuale autonomo con una shell, un modello di distribuzione basato su cartucce e un ambiente di programmazione integrato — il tutto all'interno di un'applicazione desktop su Windows, Linux e macOS.

> NextOS non è un vero sistema operativo. È un ambiente portabile progettato per simulare una piattaforma informatica completa.

---

## Visione

L'obiettivo è ricreare lo spirito dei classici home computer come il Commodore 64 o lo ZX Spectrum — un piccolo universo informatico coerente con limiti ben definiti, dove chiunque può sedersi, scrivere un programma ed eseguirlo immediatamente.

NextOS è pensato per:

- Sviluppo di videogiochi
- Programmazione creativa
- Scopi educativi
- Esperienze narrative interattive
- Sperimentazione

---

## Stack Tecnologico

| Componente | Scelta | Motivazione |
|---|---|---|
| Linguaggio | Java | Stabile, cross-platform, parte dello standard del linguaggio |
| Framework UI | Java Swing | Collaudato dal 1997, longevità garantita, nessuna dipendenza esterna |
| Scripting (programmi utente) | Java + NextOS BASIC | Potenza nativa per sviluppatori, modalità accessibile per principianti |
| Isolamento runtime | URLClassLoader + sandboxing custom | Standard JDK, nessuna dipendenza esterna |
| Compilazione (interna) | `javax.tools` | Compila cartucce Java direttamente dall'interno di NextOS |

---

## Principi Fondamentali

**Portabilità** — Funziona in modo identico su Windows, Linux e macOS.

**Ambiente Deterministico** — Le applicazioni girano in un runtime controllato con comportamento prevedibile.

**Limiti Ispirati al Retro** — I vincoli hardware incoraggiano la creatività e la coerenza stilistica.

**Semplicità** — Il sistema deve rimanere comprensibile e modificabile anche da un singolo sviluppatore.

**Ecosistema Aperto** — Le applicazioni sono distribuite come cartucce (file `.nextcart`).

---

## Hardware Virtuale

| Componente | Specifiche |
|---|---|
| Display | 320 × 200 pixel |
| Profondità colore | 256 colori (basato su palette) |
| RAM | 16 MB predefinita, espandibile a 32 MB |
| Audio | Stereo, multi-canale |
| Input | Tastiera, mouse, gamepad |
| Dimensione massima cartuccia | 32 MB |

---

## Panoramica dell'Architettura

```
Applicazione Desktop NextOS (Java + Swing)
│
├── Shell                  Interfaccia a riga di comando ispirata a POSIX
├── Kernel                 Filesystem virtuale, gestione processi, memoria
├── Dispositivo Grafico    Framebuffer, palette, sprite
├── Dispositivo Audio      Riproduzione campioni, sintesi
├── Sistema di Input       Tastiera, mouse, gamepad
├── Sistema Timer          Game loop, animazioni, eventi schedulati
│
└── Runtime Cartucce
    ├── Modalità Java      API NextOS completa, compilazione via javax.tools
    └── Modalità BASIC     Interprete integrato, accessibile ai principianti
```

---

## Sistema Cartucce (NextCart)

Le applicazioni sono distribuite come bundle `.nextcart` — archivi JAR standard con una struttura definita:

```
space_invaders.nextcart
│
├── manifest.json       Nome, versione, entry point, requisiti di memoria
├── Cartridge.class     Entry point — implementa l'interfaccia NextCartridge
├── assets/
│   ├── sprites/
│   └── sounds/
└── config/
```

### Interfaccia NextCartridge

Ogni cartuccia — sia scritta in Java che compilata da BASIC — implementa lo stesso contratto:

```java
public interface NextCartridge {
    void onStart(NextOSApi api);
    void onUpdate(float deltaTime);
    void onStop();
}
```

### API NextOS (disponibile per tutte le cartucce)

```java
public interface NextOSApi {
    void setPixel(int x, int y, int colorIndex);
    void clearScreen(int colorIndex);
    void drawSprite(int x, int y, String spriteName);
    boolean isKeyDown(int keyCode);
    void playSound(String soundName);
    String readFile(String path);
}
```

Le cartucce non hanno accesso al sistema operativo host.

---

## Modalità di Programmazione

### Modalità Java (nativa)

Accesso completo all'API NextOS. Le cartucce sono scritte in Java e compilate all'interno di NextOS tramite `javax.tools`. Destinata agli sviluppatori che vogliono sfruttare appieno la piattaforma.

```java
public class MyGame implements NextCartridge {

    @Override
    public void onStart(NextOSApi api) {
        api.clearScreen(0);
        api.drawSprite(160, 100, "hero");
    }

    @Override
    public void onUpdate(float deltaTime) {
        // chiamato ogni frame
    }

    @Override
    public void onStop() { }
}
```

### Modalità BASIC (semplificata)

Un interprete BASIC integrato per chi vuole iniziare subito senza dover imparare Java. Ispirato ai dialetti BASIC dei classici home computer.

```basic
10 CLS
20 PRINT "Ciao da NextOS!"
30 FOR I = 1 TO 10
40   SETPIXEL I*20, 100, 3
50 NEXT I
60 GOTO 20
```

Entrambe le modalità producono un `.nextcart` che gira in modo identico sullo stesso runtime.

---

## Filesystem Virtuale

```
/
├── bin        Eseguibili di sistema
├── sys        File del kernel e di sistema
├── home       File utente
├── cart       Cartucce montate
└── tmp        File temporanei
```

---

## Shell

NextOS espone una shell ispirata a POSIX con:

- Cronologia dei comandi
- Pipe e ridirezione
- Supporto agli script
- Controllo dei processi

Esempio:

```
nextos> ls /cart
nextos> run space_invaders.nextcart
nextos> edit mygame.java
```

---

## Roadmap di Sviluppo

### Fase 1 — Shell desktop
- [ ] Finestra Java + Swing
- [ ] UI terminale embedded
- [ ] Dispatcher comandi base (`ls`, `run`, `exit`)

### Fase 2 — Kernel base
- [ ] Filesystem virtuale
- [ ] Modello dei processi
- [ ] Loop principale e sistema timer

### Fase 3 — API NextOS
- [ ] Dispositivo grafico (framebuffer, palette, sprite)
- [ ] Sistema di input
- [ ] Dispositivo audio

### Fase 4 — Interprete BASIC
- [ ] Lexer e parser
- [ ] Comandi BASIC principali (`PRINT`, `FOR`, `GOTO`, `SETPIXEL`, ecc.)
- [ ] Integrazione con l'API NextOS

### Fase 5 — Compilazione cartucce Java
- [ ] Integrazione `javax.tools`
- [ ] Editor interno con compilazione ed esecuzione
- [ ] Packaging delle cartucce

### Fase 6 — Rifinitura
- [ ] Editor di testo integrato
- [ ] Validazione manifest delle cartucce
- [ ] Documentazione ed esempi

---

## Open Source

NextOS sarà rilasciato come progetto open-source. Contributi, cartucce e strumenti sono benvenuti.

---

## Nota sulle Scelte Tecnologiche

NextOS è costruito intenzionalmente su tecnologie stabili e longeve. Java e Swing sono stati scelti perché fanno parte della libreria standard di Java, con una storia che risale al 1997 e nessuna pianificazione di rimozione. Questo garantisce che il progetto rimanga compilabile e funzionante senza dipendere da framework UI di terze parti la cui longevità non può essere garantita.
