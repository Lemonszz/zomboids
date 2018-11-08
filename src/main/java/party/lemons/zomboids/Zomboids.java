package party.lemons.zomboids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.zomboids.block.TileEntityCrate;
import party.lemons.zomboids.proxy.IProxy;
import party.lemons.zomboids.store.GuiHandler;
import party.lemons.zomboids.store.MessageBuyTrade;
import party.lemons.zomboids.store.MessageCloseGui;
import party.lemons.zomboids.store.MessageOpenStore;


/**
 * Created by Sam on 5/11/2018.
 */
@Mod(modid = Zomboids.MODID, name = Zomboids.NAME, version = Zomboids.VERSION)
public class Zomboids
{
    public static final String MODID = "zomboids";
    public static final String NAME = "Zomboids";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static Zomboids INSTANCE;

    public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @SidedProxy(clientSide = "party.lemons.zomboids.proxy.ClientProxy", serverSide = "party.lemons.zomboids.proxy.ServerProxy")
    public static IProxy PROXY;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event)
    {
        PROXY.preInitSided();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
        NETWORK.registerMessage(MessageOpenStore.Handler.class, MessageOpenStore.class, 0, Side.SERVER);
        NETWORK.registerMessage(MessageBuyTrade.Handler.class, MessageBuyTrade.class, 1, Side.SERVER);
        NETWORK.registerMessage(MessageCloseGui.Handler.class, MessageCloseGui.class, 2, Side.CLIENT);

        GameRegistry.registerTileEntity(TileEntityCrate.class, new ResourceLocation(MODID, "crate"));
    }
}
