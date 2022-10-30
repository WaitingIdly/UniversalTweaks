package mod.acgaming.universaltweaks;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.bugfixes.UTHelp;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.UTAttributes;
import mod.acgaming.universaltweaks.tweaks.stronghold.UTStronghold;
import mod.acgaming.universaltweaks.tweaks.stronghold.worldgen.SafeStrongholdWorldGenerator;

@Mod(modid = UniversalTweaks.MODID,
    name = UniversalTweaks.NAME,
    version = UniversalTweaks.VERSION,
    acceptedMinecraftVersions = "[1.12.2]",
    dependencies = UniversalTweaks.DEPENDENCIES)
public class UniversalTweaks
{
    public static final String MODID = "universaltweaks";
    public static final String NAME = "Universal Tweaks";
    public static final String VERSION = "1.12.2-1.0.0";
    public static final String DEPENDENCIES = "required-after:mixinbooter;" +
        "after:attributefix;" +
        "after:bedbreakbegone;" +
        "after:blockoverlayfix;" +
        "after:bowinfinityfix;" +
        "after:experiencebugfix;" +
        "after:fastleafdecay;" +
        "after:helpfixer;" +
        "after:leafdecay;" +
        "after:letmedespawn;" +
        "after:loginhpfix;" +
        "after:mendingfix;" +
        "after:quickleafdecay;" +
        "after:savemystronghold;" +
        "after:stepupfix";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static void throwIncompatibility()
    {
        List<String> messages = new ArrayList<>();
        messages.add("Universal Tweaks has replaced and improved upon functionalities from the following mods.");
        messages.add("Therefore, these mods are now incompatible with Universal Tweaks:");
        messages.add("");
        if (Loader.isModLoaded("attributefix")) messages.add("AttributeFix");
        if (Loader.isModLoaded("bedbreakbegone")) messages.add("BedBreakBegone");
        if (Loader.isModLoaded("blockoverlayfix")) messages.add("Block Overlay Fix");
        if (Loader.isModLoaded("bowinfinityfix")) messages.add("Bow Infinity Fix");
        if (Loader.isModLoaded("experiencebugfix")) messages.add("Fix Experience Bug");
        if (Loader.isModLoaded("fastleafdecay")) messages.add("Fast Leaf Decay");
        if (Loader.isModLoaded("helpfixer")) messages.add("HelpFixer");
        if (Loader.isModLoaded("leafdecay")) messages.add("Leaf Decay Accelerator");
        if (Loader.isModLoaded("letmedespawn")) messages.add("Let Me Despawn");
        if (Loader.isModLoaded("loginhpfix")) messages.add("Login HP Fix");
        if (Loader.isModLoaded("mendingfix")) messages.add("Mending Fix");
        if (Loader.isModLoaded("quickleafdecay")) messages.add("Quick Leaf Decay");
        if (Loader.isModLoaded("savemystronghold")) messages.add("Save My Stronghold!");
        if (Loader.isModLoaded("stepupfix")) messages.add("StepupFixer");
        if (messages.size() > 3)
        {
            if (FMLLaunchHandler.side() == Side.CLIENT) UTObsoleteModsHandler.throwException(messages);
            else
            {
                messages.add("");
                throw new RuntimeException(String.join(System.lineSeparator(), messages));
            }
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        throwIncompatibility();
        if (UTConfig.tweaks.utAttributesToggle) UTAttributes.utSetAttributes();
        if (UTConfig.tweaks.utStrongholdToggle) GameRegistry.registerWorldGenerator(new SafeStrongholdWorldGenerator(), Integer.MAX_VALUE);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (UTConfig.tweaks.utStrongholdToggle) MinecraftForge.TERRAIN_GEN_BUS.register(new UTStronghold());
        LOGGER.info("Universal Tweaks initialized");
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        if (UTConfig.bugfixes.utHelpToggle) UTHelp.onServerStarting(event);
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        if (UTConfig.bugfixes.utHelpToggle) UTHelp.onServerStarted(event);
    }
}