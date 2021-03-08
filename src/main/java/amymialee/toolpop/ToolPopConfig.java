package amymialee.toolpop;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "toolpop")
public class ToolPopConfig implements ConfigData {
    public boolean instantKill = true;
    public boolean explode = false;
    public boolean includeTallGrass = false;
    public int incorrectBreakCap = 5;
}
