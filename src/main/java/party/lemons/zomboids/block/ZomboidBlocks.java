package party.lemons.zomboids.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.zomboids.Zomboids;

/**
 * Created by Sam on 6/11/2018.
 */
@Mod.EventBusSubscriber(modid = Zomboids.MODID)
@GameRegistry.ObjectHolder(value = Zomboids.MODID)
public class ZomboidBlocks
{
    public static final Block CRATE = Blocks.AIR;

    @SubscribeEvent
    public static void onRegisterBlock(RegistryEvent.Register<Block> event)
    {
        BlockStoreCrate crate = new BlockStoreCrate();
        crate.setRegistryName(new ResourceLocation(Zomboids.MODID, "crate"));
        crate.setUnlocalizedName(Zomboids.MODID + ".crate");
        crate.setHardness(0.25F);

        event.getRegistry().register(crate);
    }
}
