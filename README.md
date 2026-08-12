# ⚔️ SlimefunWarfare Legacy

[![Build SlimefunWarfare Legacy](https://github.com/wickidcow/SF_SlimefunWarfare/actions/workflows/build.yml/badge.svg)](https://github.com/wickidcow/SF_SlimefunWarfare/actions/workflows/build.yml)

A maintained fork of **SlimefunWarfare** focused on modern **Slimefun Legacy** servers and the Paper 26.2 generation.

SlimefunWarfare expands Slimefun with military and science-fiction progression: firearms, ammunition, explosives, nuclear technology, rare-earth resources, meteor systems, advanced machines, energy weapons, and modular power armor.

> This fork is maintained for the Slimefun Legacy ecosystem and is used for the **AlbionMC.com** server environment. It is not an official Slimefun, Mojang, Microsoft, Paper, or Minecraft project.

## 🧭 Project lineage and credit

SlimefunWarfare exists because of the work of the developers who came before this fork.

- **Seggan** — original SlimefunWarfare author and project creator: https://github.com/Seggan/SlimefunWarfare
- **LobbyTech-MC** — maintained/modernized the intermediate fork used as the starting point for this Legacy branch: https://github.com/LobbyTech-MC/SlimefunWarfare
- **wickidcow / Slimefun Legacy** — compatibility maintenance for current Paper-era servers: https://github.com/wickidcow/SF_SlimefunWarfare

Please give the original project and its contributors credit when redistributing or building on this work.

## 💥 What Warfare adds

Warfare is one of the larger Slimefun addons and touches several gameplay systems:

- 🔫 Pistols, revolvers, rifles, machine guns, shotguns, sniper rifles and energy weapons
- 🧨 Grenades, advanced explosives and nuclear weapons
- 🛡️ Reinforced materials and defensive technology
- 🤖 Modular power suits with powered abilities and flight-related modules
- ⚡ Energy blades and advanced combat equipment
- ☢️ Radioactive materials and industrial processing
- 🪨 GEO resources including Monazite and rare-earth progression
- ☄️ Meteor resources and Meteor Attractor gameplay
- 🏭 Specialized Warfare machines and processing chains
- 🎯 Training/combat utility items

Because Warfare interacts with projectiles, explosions, player inventories, flight, world blocks, Slimefun energy systems and repeating tasks, compatibility work is intentionally being done subsystem-by-subsystem rather than only forcing the old source to compile.

## ✅ Compatibility target

| Component | Status |
| --- | --- |
| Slimefun Legacy | ✅ Primary runtime target |
| Minecraft / Paper 26.2 | ✅ Primary server target |
| Java 25 server runtime | ✅ Build/test target |
| Java 21 addon bytecode | ✅ Preserved compatibility floor |
| Purpur 26.2 | 🟡 Expected through Paper compatibility; runtime testing recommended |
| Folia | 🟡 Planned; not yet declared Folia-safe |
| Slimefun Gugu | 🟡 Compile/API reference only, not the runtime target |
| Slimefun United | 🟡 Best-effort API compatibility, not the primary runtime target |

The build uses a Java 25 toolchain while emitting Java 21 bytecode, matching the compatibility direction used by Slimefun Legacy.

## 📦 Dependencies

### Required on the server

- **Slimefun Legacy** — https://github.com/wickidcow/Slimefun-Legacy

### Optional integrations

These are **not required** for Warfare to start:

- **InfinityExpansion** — Warfare adjusts Monazite GEO-resource balance when InfinityExpansion is present.
- **DynaTech** — Warfare can register meteor resources with DynaTech's Orechid integration when the compatible API is available.
- **TownyFlight** — Warfare coordinates power-suit flight with TownyFlight when its compatibility API is available.

### Not required

- **GuizhanLibPlugin** — removed as a runtime requirement in this Legacy fork.
- **SimpleStorage** — old upstream metadata listed it as a soft dependency, but Warfare does not require it.
- **InfinityLib plugin/JAR** — InfinityLib is a code library and is shaded/relocated into the Warfare JAR by the build.

## 🧱 Legacy modernization

The first Legacy compatibility foundation includes:

- Paper 26.2 API build target
- Java 25 CI toolchain with Java 21 bytecode output
- versioned direct JAR name: `SF_SlimefunWarfare_Legacy_v1.0.0.jar`
- raw/uncompressed JAR artifact output from GitHub Actions
- automatic current GitHub Release asset when the Legacy build lands on `master`
- removal of the GuizhanLibPlugin hard runtime dependency
- removal of the Gugu automatic updater path
- removal of obsolete Java 8 and mismatched Gradle workflows
- replacement of unsafe asynchronous player/inventory/world repeating tasks with main-thread scheduling for Paper compatibility
- preservation of optional DynaTech, InfinityExpansion and TownyFlight behavior
- English Legacy configuration comments and repository documentation

Further passes will focus on weapon/projectile APIs, explosions, machine APIs, power suits, translation cleanup, performance, and region-thread/Folia safety.

## 🔨 Building

GitHub Actions is the preferred build path.

The workflow builds using **Java 25** and Maven, verifies the resulting plugin metadata, and publishes:

`SF_SlimefunWarfare_Legacy_v1.0.0.jar`

The JAR is uploaded directly rather than requiring a source-code archive to be used as the server plugin.

For a local build:

```bash
mvn clean package
```

The resulting plugin is written to:

```text
target/SF_SlimefunWarfare_Legacy_v1.0.0.jar
```

## ⚖️ License

This repository retains the **GNU General Public License v3.0 (GPL-3.0)** licensing of the upstream project. See [`LICENSE.txt`](LICENSE.txt) for the complete license text.

If you distribute modified versions or binaries, make sure you continue to satisfy the GPL's source-code, copyright, attribution, and license requirements.

## 📜 Trademark / affiliation notice

This is an independent community project. It is **not affiliated with, endorsed by, sponsored by, or approved by Microsoft, Mojang Studios, Minecraft, PaperMC, or the official Slimefun project**. Minecraft and related names/marks belong to their respective owners.

---

### ❤️ Respect the upstream developers

This fork is intended to keep a classic Slimefun addon usable on modern servers, not to replace or erase the people who created and maintained it. Please preserve the upstream credits and GPL license when making further forks.
