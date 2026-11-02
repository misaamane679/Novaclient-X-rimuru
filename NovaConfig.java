package dev.nova.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class NovaConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("nova-client.json");

    public boolean fps = true;
    public boolean cps = true;
    public boolean keystrokes = true;
    public boolean coordinates = true;
    public boolean armor = true;
    public boolean potionEffects = true;
    public boolean toggleSprint = true;
    public boolean zoomIndicator = true;
    public int accent = 0xFF72B7FF;
    public int hudX = 8;
    public int hudY = 8;

    private static NovaConfig INSTANCE = new NovaConfig();

    public static NovaConfig get() {
        return INSTANCE;
    }

    public static void load() {
        try {
            if (Files.exists(FILE)) {
                NovaConfig loaded = GSON.fromJson(Files.readString(FILE), NovaConfig.class);
                if (loaded != null) INSTANCE = loaded;
            }
        } catch (Exception exception) {
            System.err.println("[Nova] Config load failed; defaults will be used: " + exception.getMessage());
        }
    }

    public static void save() {
        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(FILE, GSON.toJson(INSTANCE));
        } catch (IOException exception) {
            System.err.println("[Nova] Config save failed: " + exception.getMessage());
        }
    }
}
