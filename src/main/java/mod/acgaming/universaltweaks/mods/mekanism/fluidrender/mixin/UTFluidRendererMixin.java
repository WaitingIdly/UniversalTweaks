package mod.acgaming.universaltweaks.mods.mekanism.fluidrender.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import mekanism.client.render.FluidRenderer;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = FluidRenderer.class, remap = false)
public abstract class UTFluidRendererMixin
{
    @ModifyReturnValue(method = "getStages", at = @At("RETURN"))
    private int utClampIndex(int original)
    {
        if (UTConfigMods.MEKANISM.utFixFluidRenderIndex) return original;
        return Math.max(0, original);
    }
}