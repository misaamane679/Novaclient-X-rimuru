package dev.nova.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class NovaMenuScreen extends Screen {
    private static final int ICE_BLUE = 0x65CFFF;
    private static final int PALE_BLUE = 0xD9F5FF;
    private static final int PANEL = 0xB0102444;

    public NovaMenuScreen() {
        super(Text.literal("NovaClient • Tempest"));
    }

    @Override
    protected void init() {
        int col = 112;
        int left = width / 2 - col;
        int top = height / 2 - 83;

        addToggle("FPS", () -> NovaConfig.get().fps, v -> NovaConfig.get().fps = v, left, top);
        addToggle("CPS", () -> NovaConfig.get().cps, v -> NovaConfig.get().cps = v, left + col, top);
        addToggle("Keystrokes", () -> NovaConfig.get().keystrokes, v -> NovaConfig.get().keystrokes = v, left, top + 25);
        addToggle("Coordinates", () -> NovaConfig.get().coordinates, v -> NovaConfig.get().coordinates = v, left + col, top + 25);
        addToggle("Armor", () -> NovaConfig.get().armor, v -> NovaConfig.get().armor = v, left, top + 50);
        addToggle("Potions", () -> NovaConfig.get().potionEffects, v -> NovaConfig.get().potionEffects = v, left + col, top + 50);
        addToggle("Sprint HUD", () -> NovaConfig.get().toggleSprint, v -> NovaConfig.get().toggleSprint = v, left, top + 75);
        addToggle("Zoom HUD", () -> NovaConfig.get().zoomIndicator, v -> NovaConfig.get().zoomIndicator = v, left + col, top + 75);

        addDrawableChild(ButtonWidget.builder(Text.literal("Save Tempest settings"), b -> NovaConfig.save())
                .dimensions(width / 2 - 108, top + 112, 216, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close())
                .dimensions(width / 2 - 108, top + 137, 216, 20).build());
    }

    private void addToggle(String label, BooleanSupplier getter, BooleanConsumer setter, int x, int y) {
        ButtonWidget widget = ButtonWidget.builder(toggleText(label, getter.get()), b -> {
            setter.accept(!getter.get());
            NovaConfig.save();
            b.setMessage(toggleText(label, getter.get()));
        }).dimensions(x, y, 108, 20).build();
        addDrawableChild(widget);
    }

    private Text toggleText(String label, boolean enabled) {
        return Text.literal(label + "  " + (enabled ? "§bON" : "§7OFF"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        int panelW = Math.min(360, width - 20);
        int panelH = 190;
        int x = (width - panelW) / 2;
        int y = height / 2 - 96;
        context.fill(x, y, x + panelW, y + panelH, PANEL);
        context.fill(x, y, x + panelW, y + 2, ICE_BLUE);
        context.drawCenteredTextWithShadow(textRenderer, "NOVA CLIENT", width / 2, y - 17, PALE_BLUE);
        context.drawCenteredTextWithShadow(textRenderer, "TEMPEST EDITION  •  RIMURU BLUE", width / 2, y + 7, ICE_BLUE);
        super.render(context, mouseX, mouseY, delta);
    }

    @FunctionalInterface private interface BooleanSupplier { boolean get(); }
    @FunctionalInterface private interface BooleanConsumer { void accept(boolean value); }
}
