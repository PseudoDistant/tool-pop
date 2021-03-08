package amymialee.toolpop.mixin;

import amymialee.toolpop.PlayerEntityWrapper;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityWrapper {
    @Unique
    private int popAmount;

    @Override
    public int getPopAmount() {
        return popAmount;
    }

    @Override
    public void setPopAmount(int value) {
        popAmount = value;
    }
}