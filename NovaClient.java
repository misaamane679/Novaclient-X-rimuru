package dev.nova.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class NovaClient implements ClientModInitializer {
    public static KeyBinding menuKey;
    public static KeyBinding sprintKey;
    public static KeyBinding zoomKey;

    @Override
    public void onInitializeClient() {
        NovaConfig.load();

        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nova.open_menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.nova.general"));
        sprintKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nova.toggle_sprint", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V,
                "category.nova.general"));
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nova.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C,
                "category.nova.general"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) client.setScreen(new NovaMenuScreen());
            while (sprintKey.wasPressed()) NovaState.sprintToggled = !NovaState.sprintToggled;
            while (zoomKey.wasPressed()) NovaState.zoomHeld = !NovaState.zoomHeld;

            if (client.player != null && NovaConfig.get().toggleSprint && NovaState.sprintToggled) {
                client.player.setSprinting(true);
            }
            if (client.player == null) {
                NovaState.sprintToggled = false;
                NovaState.zoomHeld = false;
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> NovaHud.render(drawContext));
    }
}
