package amymialee.toolpop.mixin;

import amymialee.toolpop.PlayerEntityWrapper;
import amymialee.toolpop.ToolPop;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.ExplodingTntEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ToolItem.class)
public class MiningToolItemMixin {
    @Shadow
    private Set<Block> effectiveBlocks;

    int popCount = 0;
    private void popUp(LivingEntity miner, Block state, World world) {
        if (miner instanceof PlayerEntity &&
                (ToolPop.configGet.includeTallGrass ||
                        (state.getBlastResistance(miner) != 0 && !ToolPop.configGet.includeTallGrass))) {
            popCount = ((PlayerEntityWrapper) miner).getPopAmount();
            if (popCount >= ToolPop.configGet.incorrectBreakCap - 1) {
                (miner).sendMessage(new LiteralText("Someone forgot to switch tools!"));
                if (ToolPop.configGet.explode) {
                    try {
                        ExplodingTntEntity tnt = new ExplodingTntEntity(world);
                        //assert tnt != null;
                        tnt.refreshPositionAndAngles(miner.x + 0.5D, miner.y, miner.z + 0.5D, 0.0F, 0.0F);
                        world.spawnEntity(tnt);
                        tnt.fuseTimer = 0;
                    } catch (Exception ignored) {
                    }
                }
                if (ToolPop.configGet.instantKill) {
                    miner.damage(DamageSource.MAGIC, 5000);
                }
                popCount = 0;
            } else {
                popCount++;
            }
            ((PlayerEntityWrapper) miner).setPopAmount(popCount);
        }
    }

    private void popDown(LivingEntity miner, Block state) {
        if (miner instanceof PlayerEntity && state.getBlastResistance(miner) != 0) {
            ((PlayerEntityWrapper) miner).setPopAmount(0);
        }
    }

    @Inject(method = "onBlockBroken", at = @At("HEAD"))
    private void postMine(ItemStack stack, World world, Block state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        Material material = state.getMaterial();
        if (stack.getItem() instanceof SwordItem) {
            if (material != Material.COBWEB) {
                popUp(miner, state, world);
            } else {
                popDown(miner, state);
            }
        } else if (stack.getItem() instanceof PickaxeItem) {
            if (material != Material.IRON && material != Material.STONE) {
                popUp(miner, state, world);
            } else {
                popDown(miner, state);
            }
        } else if (stack.getItem() instanceof AxeItem) {
            if (material != Material.WOOD
                    && material != Material.PLANT
                    && material != Material.REPLACEABLE_PLANT
                    && material != Material.PUMPKIN) {
                popUp(miner, state, world);
            } else {
                popDown(miner, state);
            }
        } else if (stack.getItem() instanceof ShovelItem ||
                stack.getItem() instanceof HoeItem) {
            if (!effectiveBlocks.contains(state)) {
                popUp(miner, state, world);
            } else {
                popDown(miner, state);
            }
        } else if (stack.getItem() instanceof ShearsItem) {
            if (state != Blocks.COBWEB && state != Blocks.LEAVES) {
                popUp(miner, state, world);
            } else {
                popDown(miner, state);
            }
        }
    }
}
