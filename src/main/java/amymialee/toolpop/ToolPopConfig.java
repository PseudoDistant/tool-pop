package amymialee.toolpop;

public class ToolPopConfig {
    public boolean instantKill;
    public boolean explode;
    public boolean includeTallGrass;
    public int incorrectBreakCap;

    public ToolPopConfig(boolean iK, boolean e, boolean iTG, int iBC) {
        this.instantKill = iK;
        this.explode = e;
        this.includeTallGrass = iTG;
        this.incorrectBreakCap = iBC;
    }
}
