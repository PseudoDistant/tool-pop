package amymialee.toolpop;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class ToolPop implements ModInitializer {
    public static ToolPopConfig configGet;
    @Override
    public void onInitialize() {
        AutoConfig.register(ToolPopConfig.class, GsonConfigSerializer::new);
        configGet = AutoConfig.getConfigHolder(ToolPopConfig.class).getConfig();
        if (configGet.incorrectBreakCap < 1) {
            configGet.incorrectBreakCap = 1;
        }
    }
}

