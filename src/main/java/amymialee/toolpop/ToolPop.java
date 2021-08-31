package amymialee.toolpop;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class ToolPop implements ModInitializer {
    public static ToolPopConfig configGet = new ToolPopConfig(true, false, false, 5);
    Path configPath = Paths.get("config/toolpop.json");
    public Gson daData = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

    public void saveDaData() {
        try{
            if (configPath.toFile().exists()) {
                configGet = daData.fromJson(new String(Files.readAllBytes(configPath)), ToolPopConfig.class);
            } else {
                Files.write(configPath, Collections.singleton(daData.toJson(configGet)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onInitialize() {
        saveDaData();
        if (configGet.incorrectBreakCap < 1) {
            configGet.incorrectBreakCap = 1;
        }
    }
}

