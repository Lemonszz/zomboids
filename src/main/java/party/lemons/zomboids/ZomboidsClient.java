package party.lemons.zomboids;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.zomboids.proxy.ClientProxy;
import party.lemons.zomboids.store.MessageOpenStore;

/**
 * Created by Sam on 5/11/2018.
 */
@Mod.EventBusSubscriber(modid = Zomboids.MODID, value = Side.CLIENT)
public class ZomboidsClient
{
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if(event.phase != TickEvent.Phase.START || Minecraft.getMinecraft().world == null)
            return;

        if(ZomboidsConfig.USE_SHADER && !Minecraft.getMinecraft().entityRenderer.isShaderActive())
        {
            Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation(ZomboidsConfig.SHADER));
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if(event.phase != TickEvent.Phase.START)
            return;

        if(ClientProxy.KEY_STORE.isPressed())
        {
            sendStoreMessage();
        }
    }

    public static int LAST_PAGE = 0;

    public static void sendStoreMessage()
    {
        Zomboids.NETWORK.sendToServer(new MessageOpenStore(LAST_PAGE));
    }
}
