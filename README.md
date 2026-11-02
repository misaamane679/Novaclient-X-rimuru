# Nova Client — Mojo Launcher / Android Edition

A client-side Fabric mod starter configured specifically for **Minecraft Java 1.20.1**, **Java 17**, and **Fabric Loader**. It is designed to be installed as a mod through Mojo Launcher; it is not a standalone launcher.

## Target setup
- Minecraft Java Edition 1.20.1
- Fabric Loader 0.15.x or compatible
- Fabric API 0.92.2+1.20.1
- Java 17
- Android / Mojo Launcher
- Holy GL renderer (rendering is handled through Minecraft/Fabric APIs; no renderer-specific native library is bundled)

## Features implemented
- Right Shift opens Nova's in-game settings screen (external keyboard).
- V toggles sprint.
- C toggles a zoom *indicator state*; camera/FOV zoom is not implemented yet.
- HUD: FPS, real left-mouse CPS counter, coordinates, keyboard state, sprint state, armor item names, potion effects.
- Touch-friendly in-game settings buttons.
- Enable/disable HUD modules and save settings to `config/nova-client.json`.
- Uses a small client-side mixin for left mouse click counting.

## Not yet implemented
- Actual camera zoom / FOV modification.
- Drag-and-drop / resize HUD editor.
- Custom crosshair editor.
- Minimap.
- Search and categories in a full mod browser.
- Cosmetics and Microsoft account authentication.
- Standalone launcher and custom main menu.

## Build on a computer
Install JDK 17 and Gradle 8.x, then run:
```bash
gradle build
```
The built mod JAR will be in `build/libs/`. Copy the JAR into the Minecraft instance's `mods` folder alongside Fabric API.

## Build on Android
If your Mojo Launcher environment does not provide Gradle build tools, build the JAR on a PC or a compatible Android development environment first, then transfer the JAR to the instance's `mods` folder. The source ZIP is not itself a playable mod.

## Controls
- Right Shift: open Nova menu (keyboard)
- V: toggle sprint
- C: toggle zoom indicator state

## Notes
- This source package has not been compiled or runtime-tested in Mojo Launcher here. Please treat it as source code pending a Gradle build and device test.
- Holy GL compatibility cannot be guaranteed without testing on the exact Android device, GPU, and Mojo build.
- This project does not request or store Microsoft credentials.
- Unofficial community project; not affiliated with Mojang or Microsoft.


## Rimuru / Tempest edition build

This package adds a blue Tempest-inspired in-game settings panel and a GitHub Actions
workflow. It is a Fabric mod project for Minecraft 1.20.1 and Java 17.

To build in GitHub: upload the *contents* of this project folder to the root of your
repository, including the hidden `.github/workflows/build.yml` file. Open the
repository's **Actions** tab, choose **Build NovaClient**, and run the workflow.
Download the `NovaClient-Fabric-1.20.1` artifact from the completed run.

This is source code, not a precompiled JAR. The build workflow has not been run or
verified here. The current project is a starter client mod, not a standalone
Minecraft launcher; it does not replace Mojo Launcher.
