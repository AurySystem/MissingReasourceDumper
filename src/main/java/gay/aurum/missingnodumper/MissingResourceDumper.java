package gay.aurum.missingnodumper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MissingResourceDumper implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("missingnodumper");
	public static final Map<Identifier, GenericResource> Assets = new HashMap<>();
	public static final Map<Identifier, GenericResource> Data = new HashMap<>();


	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Loading {}!", mod.metadata().name());
		Assets.put(new Identifier("missingnodumper","textures/test.png"),GenericResource.MISSINGNO);
		dump("test",false);
		Data.put(new Identifier("missingnodumper","test.json"),GenericResource.buildJson(new JsonObject()));
		dump("test",true);
	}

	public void dump(String outPath, boolean isData){
		String group = isData?"data":"assets";
		for (Map.Entry<Identifier,GenericResource> entry : isData?Data.entrySet():Assets.entrySet()) {
			Identifier id = entry.getKey();
			String path = "./" + outPath + "/" + group + "/" + id.getNamespace() + "/" + id.getPath();
			writeFile(new File(path), entry.getValue());
		}
		if(isData)Data.clear(); else Assets.clear();
	}

	private void writeFile(File output, GenericResource resource) {
		try {
			if (output.getParentFile().exists() || output.getParentFile().mkdirs()) {
				if (resource.isTexture) {
					ImageIO.write(GenericResource.missingtex, "PNG", output);
				} else {
					FileWriter writer = new FileWriter(output);
					writer.write(resource.data);
					writer.close();
				}
			} else {
				LOGGER.error("Failed to generate parent directories for file: {}", output.getPath());
			}
		} catch (IOException e) {
			LOGGER.error("Error handling writing default files: ", e);
		}
	}
}
