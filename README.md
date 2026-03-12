# NextOS

### A Fantasy Operating System for Modern Retro Computing

NextOS is an open-source fantasy operating system designed as a modern evolution of 1980s home computers. It provides a self-contained virtual environment with a shell, a cartridge-based application model, and a built-in programming environment — all running inside a desktop application on Windows, Linux, and macOS.

> NextOS is not a real operating system. It is a portable environment designed to simulate a complete computer platform.

---

## Vision

The goal is to recreate the spirit of classic home computers like the Commodore 64 or ZX Spectrum — a small, coherent computing universe with clear limitations, where anyone can sit down, write a program, and run it immediately.

NextOS is intended for:

- Game development
- Creative coding
- Educational purposes
- Narrative software experiences
- Experimentation

---

## Technology Stack

| Component | Choice | Rationale |
|---|---|---|
| Language | Java | Stable, cross-platform, part of the language standard |
| UI Framework | Java Swing | Proven since 1997, guaranteed longevity, no third-party dependency |
| Scripting (user programs) | Java + NextOS BASIC | Native power for developers, accessible mode for beginners |
| Runtime isolation | URLClassLoader + custom sandboxing | Standard JDK, no external dependencies |
| Compilation (internal) | `javax.tools` | Compile Java cartridges from within NextOS itself |

---

## Core Principles

**Portability** — Runs identically on Windows, Linux, and macOS.

**Deterministic Environment** — Applications run inside a controlled runtime with predictable behavior.

**Retro-Inspired Limitations** — Hardware-like constraints encourage creativity and stylistic coherence.

**Simplicity** — The system should remain understandable and hackable by a single developer.

**Open Ecosystem** — Applications are distributed as cartridges (`.nextcart` files).

---

## Virtual Hardware

| Component | Specification |
|---|---|
| Display | 320 × 200 pixels |
| Color depth | 256 colors (palette-based) |
| RAM | 16 MB default, expandable to 32 MB |
| Audio | Stereo, multi-channel |
| Input | Keyboard, mouse, game controllers |
| Max cartridge size | 32 MB |

---

## Architecture Overview

```
NextOS Desktop Application (Java + Swing)
│
├── Shell                  POSIX-like command interface
├── Kernel                 Virtual filesystem, process management, memory
├── Graphics Device        Framebuffer, palette, sprites
├── Audio Device           Sample playback, synthesis
├── Input System           Keyboard, mouse, controllers
├── Timer System           Game loops, animation, scheduled events
│
└── Cartridge Runtime
    ├── Java Mode          Full NextOS API, compiled via javax.tools
    └── BASIC Mode         Built-in interpreter, accessible for beginners
```

---

## Cartridge System (NextCart)

Applications are distributed as `.nextcart` bundles — standard JAR archives with a defined structure:

```
space_invaders.nextcart
│
├── manifest.json       Application name, version, entry point, memory requirements
├── Cartridge.class     Entry point — implements NextCartridge interface
├── assets/
│   ├── sprites/
│   └── sounds/
└── config/
```

### NextCartridge Interface

Every cartridge — whether written in Java or compiled from BASIC — implements the same contract:

```java
public interface NextCartridge {
    void onStart(NextOSApi api);
    void onUpdate(float deltaTime);
    void onStop();
}
```

### NextOS API (available to all cartridges)

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

Cartridges have no access to the host operating system.

---

## Programming Modes

### Java Mode (native)

Full access to the NextOS API. Cartridges are written in Java and compiled inside NextOS using `javax.tools`. Intended for developers who want the full power of the platform.

```java
public class MyGame implements NextCartridge {

    @Override
    public void onStart(NextOSApi api) {
        api.clearScreen(0);
        api.drawSprite(160, 100, "hero");
    }

    @Override
    public void onUpdate(float deltaTime) {
        // called every frame
    }

    @Override
    public void onStop() { }
}
```

### BASIC Mode (simplified)

A built-in BASIC interpreter for users who want to start immediately without learning Java. Inspired by the BASIC dialects of classic home computers.

```basic
10 CLS
20 PRINT "Hello from NextOS!"
30 FOR I = 1 TO 10
40   SETPIXEL I*20, 100, 3
50 NEXT I
60 GOTO 20
```

Both modes produce a `.nextcart` that runs identically on the same runtime.

---

## Virtual Filesystem

```
/
├── bin        System executables
├── sys        Kernel and system files
├── home       User files
├── cart       Mounted cartridges
└── tmp        Temporary files
```

---

## Shell

NextOS exposes a POSIX-inspired shell with:

- Command history
- Pipes and redirection
- Scripting support
- Process control

Example:

```
nextos> ls /cart
nextos> run space_invaders.nextcart
nextos> edit mygame.java
```

---

## Development Roadmap

### Phase 1 — Desktop shell
- [ ] Java + Swing window
- [ ] Embedded terminal UI
- [ ] Basic command dispatcher (`ls`, `run`, `exit`)

### Phase 2 — Kernel base
- [ ] Virtual filesystem
- [ ] Process model
- [ ] Main loop and timer system

### Phase 3 — NextOS API
- [ ] Graphics device (framebuffer, palette, sprites)
- [ ] Input system
- [ ] Audio device

### Phase 4 — BASIC interpreter
- [ ] Lexer and parser
- [ ] Core BASIC commands (`PRINT`, `FOR`, `GOTO`, `SETPIXEL`, etc.)
- [ ] Integration with NextOS API

### Phase 5 — Java cartridge compilation
- [ ] `javax.tools` integration
- [ ] Internal editor with compile + run
- [ ] Cartridge packaging

### Phase 6 — Polish
- [ ] Integrated text editor
- [ ] Cartridge manifest validation
- [ ] Documentation and examples

---

## Open Source

NextOS will be released as an open-source project. Contributions, cartridges, and tools are welcome.

---

## Note on Technology Choices

NextOS is intentionally built on stable, long-lived technology. Java and Swing were chosen because they are part of the Java standard library with a track record dating to 1997 and no planned removal. This ensures the project remains buildable and runnable without dependency on third-party UI frameworks whose longevity cannot be guaranteed.
