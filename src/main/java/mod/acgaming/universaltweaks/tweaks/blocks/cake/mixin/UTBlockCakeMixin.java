package mod.acgaming.universaltweaks.tweaks.blocks.cake.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = BlockCake.class, remap = false)
abstract class UTBlockCakeMixin
{
    @Shadow
    @Final
    public static PropertyInteger BITES;

    @Shadow
    public abstract int getMetaFromState(IBlockState state);

    /**
     * @author WaitingIdly
     * @reason drop up to one item - if {@link BlockCake#getItemDropped} returns {@link Items#AIR}, it will still not drop anything.
     */
    @ModifyReturnValue(method = "quantityDropped", at = @At("RETURN"))
    public int utDropSomething(int original)
    {
        if (!UTConfigTweaks.BLOCKS.utDropUneatenCake) return original;
        return 1;
    }

    /**
     * @author WaitingIdly
     * @reason get the relevant item for the given block and drop that instead.
     * Should be compatible with mods extending {@link BlockCake} if they use the {@link BlockCake#BITES} property.
     */
    @ModifyReturnValue(method = "getItemDropped", at = @At("RETURN"))
    public Item utCakeItem(Item original, @Local(argsOnly = true) IBlockState state)
    {
        if (!UTConfigTweaks.BLOCKS.utDropUneatenCake) return original;
        if (getMetaFromState(state) == 0) return Item.getItemFromBlock((Block) (Object) this);
        return original;
    }
}
