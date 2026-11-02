package dev.nova.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public final class NovaHud {
    private NovaHud() {}

    public static void render(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        NovaConfig config = NovaConfig.get();
        int x = config.hudX;
        int y = config.hudY;
        int line = 0;
        int accent = config.accent;

        context.fill(x - 5, y - 5, x + 164, y + 8, 0xD9101726);
        context.fill(x - 5, y - 5, x + 164, y - 3, accent);

        if (config.fps) line = drawLine(context, "FPS  " + client.getCurrentFps(), x, y + line * 12, accent, line);
        if (config.cps) line = drawLine(context, "CPS  " + NovaState.cps, x, y + line * 12, 0xFFFFFFFF, line);

        if (config.coordinates) {
            BlockPos pos = client.player.getBlockPos();
            line = drawLine(context, "XYZ  " + pos.getX() + " / " + pos.getY() + " / " + pos.getZ(),
                    x, y + line * 12, 0xFFFFFFFF, line);
        }

        if (config.keystrokes) {
            String keys = (client.options.forwardKey.isPressed() ? "[W] " : " W  ")
                    + (client.options.leftKey.isPressed() ? "[A] " : " A  ")
                    + (client.options.backKey.isPressed() ? "[S] " : " S  ")
                    + (client.options.rightKey.isPressed() ? "[D]" : " D ");
            line = drawLine(context, keys, x, y + line * 12, 0xFFFFFFFF, line);
        }

        if (config.toggleSprint) {
            line = drawLine(context, "SPRINT  " + (NovaState.sprintToggled ? "ON" : "OFF"),
                    x, y + line * 12, 0xFFFFFFFF, line);
        }

        if (config.zoomIndicator && NovaState.zoomHeld) {
            line = drawLine(context, "ZOOM  ON", x, y + line * 12, accent, line);
        }

        if (config.armor) {
            StringBuilder armorText = new StringBuilder("ARMOR  ");
            for (ItemStack stack : client.player.getArmorItems()) {
                if (!stack.isEmpty()) armorText.append(stack.getName().getString()).append("  ");
            }
            line = drawLine(context, armorText.toString(), x, y + line * 12, 0xFFFFFFFF, line);
        }

        if (config.potionEffects) {
            for (StatusEffectInstance effect : client.player.getStatusEffects()) {
                String effectName = effect.getEffectType().getName().getString();
                String amplifier = effect.getAmplifier() > 0 ? " " + (effect.getAmplifier() + 1) : "";
                line = drawLine(context, effectName + amplifier, x, y + line * 12, 0xFFB9D7FF, line);
            }
        }

        context.fill(x - 5, y + 7, x + 164, y + line * 12 + 5, 0x990B1020);
        updateCps();
    }

    private static int drawLine(DrawContext context, String text, int x, int y, int color, int line) {
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, text, x, y, color);
        return line + 1;
    }

    private static void updateCps() {
        long now = System.currentTimeMillis();
        if (now - NovaState.cpsWindowStart >= 1000L) {
            NovaState.cps = NovaState.clickCount;
            NovaState.clickCount = 0;
            NovaState.cpsWindowStart = now;
        }
    }
}
