package de.doomedartemis.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.doomedartemis.ClimbingClaws;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public final class ClientConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Values values = new Values();

    private ClientConfig() {
    }

    public static void load() {
        Path path = path();
        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                Values loaded = GSON.fromJson(reader, Values.class);
                if (loaded != null) {
                    values = loaded;
                    return;
                }
            } catch (IOException ignored) {
            }
        }
        save(path);
    }

    public static Snapshot snapshot() {
        return new Snapshot(values.showWallSpringCooldownOverlay);
    }

    public static void apply(Snapshot snapshot) {
        values.showWallSpringCooldownOverlay = snapshot.showWallSpringCooldownOverlay();
        save(path());
    }

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve(ClimbingClaws.MOD_ID + "-client.json");
    }

    private static void save(Path path) {
        try {
            Files.createDirectories(path.getParent());
            try (Writer writer = Files.newBufferedWriter(path)) {
                GSON.toJson(values, writer);
            }
        } catch (IOException ignored) {
        }
    }

    public static boolean showWallSpringCooldownOverlay() {
        return values.showWallSpringCooldownOverlay;
    }

    public record Snapshot(boolean showWallSpringCooldownOverlay) {
    }

    private static final class Values {
        boolean showWallSpringCooldownOverlay = true;
    }
}
