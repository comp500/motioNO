package link.infra.motiono.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigHandler {
	private static final transient Path configFile = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("motiono.json");
	private static final transient Logger LOGGER = LogManager.getLogger(ConfigHandler.class);

	private ConfigHandler() {}

	private static transient ConfigHandler INSTANCE = null;

	public static ConfigHandler getInstance() {
		if (INSTANCE == null) {
			Gson gson = new Gson();
			try (FileReader reader = new FileReader(configFile.toFile())) {
				INSTANCE = gson.fromJson(reader, ConfigHandler.class);
			} catch (FileNotFoundException ignored) {
				// Do nothing!
			} catch (IOException e) {
				LOGGER.error("Failed to read configuration", e);
			}
			if (INSTANCE == null) {
				INSTANCE = new ConfigHandler();
				INSTANCE.save();
			}
		}
		return INSTANCE;
	}

	public boolean enabled = true;

	public void save() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter(configFile.toFile())) {
			gson.toJson(this, ConfigHandler.class, writer);
		} catch (IOException e) {
			LOGGER.error("Failed to save configuration", e);
		}
	}
}