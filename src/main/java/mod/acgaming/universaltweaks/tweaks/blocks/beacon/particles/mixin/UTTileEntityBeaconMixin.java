package mod.acgaming.universaltweaks.tweaks.blocks.beacon.particles.mixin;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityBeacon;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(TileEntityBeacon.class)
public abstract class UTTileEntityBeaconMixin
{
    /**
     * @author WaitingIdly
     * @reason disable the potion effects of potions applied by the beacon
     */
    @WrapOperation(method = "addEffectsToPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionEffect;<init>(Lnet/minecraft/potion/Potion;IIZZ)V"))
    private PotionEffect disablePotionEffectParticlesFromBeacon(Potion potionIn, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn, Operation<PotionEffect> original)
    {
        if (UTConfigTweaks.BLOCKS.utDisableBeaconPotionParticles) original.call(potionIn, durationIn, amplifierIn, ambientIn, false);
        return original.call(potionIn, durationIn, amplifierIn, ambientIn, showParticlesIn);
    }
}
