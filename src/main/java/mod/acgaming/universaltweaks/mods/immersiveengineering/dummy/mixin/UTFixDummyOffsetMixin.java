package mod.acgaming.universaltweaks.mods.immersiveengineering.dummy.mixin;

import java.util.Arrays;

import blusunrize.immersiveengineering.common.blocks.TileEntityMultiblockPart;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TileEntityMultiblockPart.class, remap = false)
public abstract class UTFixDummyOffsetMixin
{
    /**
     * @reason ensure that loading the offset field from NBT data will always load an array with a length of 3.
     */
    @WrapOperation(method = "readCustomNBT", at = @At(value = "FIELD", target = "Lblusunrize/immersiveengineering/common/blocks/TileEntityMultiblockPart;offset:[I"))
    private void utEnsureOffsetIsValid(TileEntityMultiblockPart<?> instance, int[] offset, Operation<Void> original)
    {
        if (!UTConfigMods.IMMERSIVE_ENGINEERING.utDummyFix || offset.length == 3)
        {
            original.call(instance, offset);
        }
        original.call(instance, Arrays.copyOf(offset, 3));
    }
}
