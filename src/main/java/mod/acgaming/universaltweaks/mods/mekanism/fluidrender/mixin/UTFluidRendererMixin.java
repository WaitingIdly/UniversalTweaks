package mod.acgaming.universaltweaks.mods.mekanism.fluidrender.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mekanism.client.render.FluidRenderer;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = FluidRenderer.class, remap = false)
public abstract class UTFluidRendererMixin
{
    @ModifyExpressionValue(method = "getStages", at = @At(value = "FIELD", target = "Lmekanism/client/render/FluidRenderer$RenderData;height:I"))
    private static int utClampHeightData(int original)
    {
        if (!UTConfigMods.MEKANISM.utFixFluidRenderIndex) return original;
        return Math.max(1, original);
    }
}