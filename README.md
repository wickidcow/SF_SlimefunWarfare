# ⚔️ SlimefunWarfare Legacy

[![Build SlimefunWarfare Legacy](https://github.com/wickidcow/SF_SlimefunWarfare/actions/workflows/build.yml/badge.svg)](https://github.com/wickidcow/SF_SlimefunWarfare/actions/workflows/build.yml)
[![Latest Release](https://img.shields.io/github/v/release/wickidcow/SF_SlimefunWarfare?label=release)](https://github.com/wickidcow/SF_SlimefunWarfare/releases/latest)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE.txt)

A maintained **Slimefun Legacy** fork of **SlimefunWarfare**, modernized for current Paper-era Minecraft servers while preserving the original addon’s combat, technology, and science-fiction progression.

> **Server focus:** maintained for the Slimefun Legacy ecosystem and used by **AlbionMC.com**. This is an independent community fork and is not an official Slimefun, Mojang, Microsoft, Minecraft, PaperMC, or Purpur project.

## ❤️ Credits & project lineage

SlimefunWarfare exists because of the developers and maintainers who built it before this fork.

- **Seggan** — original SlimefunWarfare creator and primary upstream author  
  https://github.com/Seggan/SlimefunWarfare
- **LobbyTech-MC** — maintained and modernized the intermediate fork used as the starting point for this Legacy branch  
  https://github.com/LobbyTech-MC/SlimefunWarfare
- **wickidcow / Slimefun Legacy** — Paper 26.2+, Java 25-era compatibility and ongoing maintenance  
  https://github.com/wickidcow/SF_SlimefunWarfare

Please keep the upstream credits and GPL license intact when redistributing or building on this work. If the original project helped your server, consider starring and supporting the upstream repositories as well.

## 💥 What does SlimefunWarfare add?

Warfare is one of the larger combat-focused Slimefun addons. It adds a progression path around weapons, advanced materials, military technology, space resources, and powered equipment.

### 🔫 Firearms & ammunition

- Pistols and revolvers
- Rifles and assault rifles
- Machine guns and miniguns
- Shotguns and sniper rifles
- Energy rifles
- Multiple ammunition tiers with different damage/effects
- Bullet production machinery

### 🧨 Explosives & nuclear technology

- Chemical and advanced explosives
- Grenade-related materials
- Reinforced concrete
- Nuclear weapon progression
- Radioactive processing and late-game resources

### 🛡️ Power suits & advanced equipment

- Modular power armor
- Power-suit generator and module systems
- Flight-related suit functionality
- Energy blades and advanced melee equipment
- High-tier materials such as Osmium and Segganesson

### ☄️ Space, resources & machines

- Meteor Attractor gameplay
- Osmium and Segganesson meteors
- Monazite and rare-earth progression
- Specialized Warfare processing machines
- Elemental Reactor and energy-focused technology
- Optional DynaTech Orechid and Infinity Expansion integrations

Because Warfare interacts with projectiles, explosions, inventories, player flight, world blocks, Slimefun energy networks, and scheduled tasks, this fork is being modernized subsystem-by-subsystem rather than merely forcing old source code to compile.

## ✅ Compatibility

| Platform / integration | Status |
| --- | --- |
| **Slimefun Legacy** | ✅ Primary runtime target |
| **Paper 26.2** | ✅ Primary server target |
| **Minecraft 1.21.11+ / 26.2 generation** | ✅ Targeted |
| **Java 25 server runtime** | ✅ Build/test environment |
| **Java 21 addon bytecode** | ✅ Compatibility floor |
| **Purpur 26.2** | 🟡 Expected through Paper compatibility; runtime testing recommended |
| **Folia** | 🟡 Compatibility work in progress; not yet declared fully Folia-safe |
| **Slimefun United** | 🟡 Best-effort API compatibility |
| **Slimefun Gugu** | 🟡 Upstream/reference compatibility only; not the runtime target |

The project builds with a **Java 25 toolchain** while emitting **Java 21 bytecode**.

## 📦 Dependencies

### Required

| Plugin | Purpose |
| --- | --- |
| **Slimefun Legacy** | Core Slimefun API/runtime required by Warfare |

Slimefun Legacy: https://github.com/wickidcow/Slimefun-Legacy

### Optional integrations

| Plugin | What Warfare uses it for | Required? |
| --- | --- | --- |
| **InfinityExpansion / InfinityExpansion2** | Monazite GEO-resource balancing/integration | No |
| **DynaTech** | Orechid meteor/resource registration when a compatible API is present | No |
| **TownyFlight** | Coordinates power-suit flight with Towny flight handling | No |

This Legacy fork recognizes both the older `InfinityExpansion` plugin name and `InfinityExpansion2`.

### No longer required

- **GuizhanLibPlugin** — the hard runtime requirement and updater path were removed.
- **SimpleStorage** — stale upstream soft-dependency metadata; Warfare does not require it.
- **Separate InfinityLib plugin/JAR** — InfinityLib is shaded into the Warfare JAR during build.

## 🚀 SlimefunWarfare Legacy v1.0.0

The first Legacy release establishes the modern compatibility foundation.

### Compatibility changes

- Updated the build target to **Paper 26.2**.
- Added a **Java 25 CI/build toolchain** while retaining Java 21 bytecode.
- Made **Slimefun Legacy** the primary runtime target.
- Removed the hard **GuizhanLibPlugin** runtime dependency.
- Removed the old Gugu automatic-updater path.
- Migrated removed Paper `Attribute.GENERIC_*` constants to the modern attribute API.
- Replaced unsafe asynchronous player/inventory/world repeating work with Paper-safe scheduling.
- Added compatibility for both the current and older DynaTech Orechid package layouts.
- Added detection for both `InfinityExpansion` and `InfinityExpansion2`.

### Gameplay/runtime fixes

- Fixed an inherited gun-ammunition race condition where Slimefun backpack contents were loaded asynchronously after weapon firing had already made its ammo decision.
- Gun ammunition consumption from the off-hand and normal player inventory is now synchronous and deterministic.
- Backpack ammunition scanning is intentionally disabled during firing until it can be implemented safely with Slimefun Legacy’s asynchronous backpack API.

### English Legacy cleanup

- Restored **Seggan’s original English item names and lore** over the Chinese intermediate-fork catalog.
- Restored English Slimefun Warfare guide/category names.
- Restored English power-suit module text and configuration comments.
- Removed obsolete bundled Guizhan helper code.
- Removed a duplicate/unused item catalog inherited from the intermediate fork.

### Build & repository cleanup

- Removed obsolete Java 8 and mismatched Gradle workflows.
- Added a single current GitHub Actions build for the Legacy branch.
- Produces a directly usable raw JAR named:

```text
SF_SlimefunWarfare_Legacy_v1.0.0.jar
```

- Master builds create/update the matching GitHub Release and attach the raw JAR.

## 📥 Download & installation

1. Download **`SF_SlimefunWarfare_Legacy_v1.0.0.jar`** from the latest GitHub Release.
2. Place the raw `.jar` directly in your server's `plugins` folder.
3. Make sure **Slimefun Legacy** is installed.
4. Install DynaTech / InfinityExpansion2 only if you want those optional integrations.
5. Restart the server normally; do not use plugin hot-reload tools for Slimefun addons.

Releases: https://github.com/wickidcow/SF_SlimefunWarfare/releases

## 🔨 Building from source

GitHub Actions is the preferred build path. The workflow builds with Java 25, validates the resulting plugin metadata, verifies the JAR, and publishes the versioned raw JAR.

For a local Maven build:

```bash
mvn clean package
```

Expected output:

```text
target/SF_SlimefunWarfare_Legacy_v1.0.0.jar
```

## 🧪 Runtime testing status

Compilation and packaging are validated, but Warfare contains several high-impact gameplay systems that should continue receiving live-server testing:

- Gun/projectile hit behavior
- Grenades, explosives, and nuclear effects
- Meteor attraction and impact flow
- Warfare machines and Slimefun energy handling
- Power-suit modules and flight interaction
- Deeper Folia/region-thread compatibility

Please report reproducible runtime problems through the repository when issue tracking is enabled or through the project maintainer’s normal support channel.

## ⚖️ License

This repository retains the upstream **GNU General Public License v3.0 (GPL-3.0)**. See [`LICENSE.txt`](LICENSE.txt) for the complete license text.

When distributing modified versions or binaries, preserve applicable copyright notices, attribution, the GPL license, and corresponding-source obligations required by GPL-3.0.

## 📜 Trademark & affiliation notice

This is an independent community project. It is **not affiliated with, endorsed by, sponsored by, or approved by Microsoft, Mojang Studios, Minecraft, PaperMC, Purpur, or the official Slimefun project**. Minecraft and related names and marks belong to their respective owners.

---

### 🏰 AlbionMC

This fork is maintained with **AlbionMC.com** in mind as part of the broader Slimefun Legacy addon ecosystem. AlbionMC’s use of this fork does not imply endorsement by the original SlimefunWarfare developers or by Mojang/Microsoft.

### ❤️ Respect the upstream developers

The goal of this fork is to keep a classic Slimefun addon usable on modern servers—not to replace or erase the people who created and maintained it. Please preserve upstream credit when making further forks.
