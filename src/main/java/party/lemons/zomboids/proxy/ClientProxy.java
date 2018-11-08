package party.lemons.zomboids.proxy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by Sam on 5/11/2018.
 */
public class ClientProxy implements IProxy
{
    public static KeyBinding KEY_STORE = new KeyBinding("key.store", 25, "key.categories.gameplay");

    @Override
    public void preInitSided()
    {
        ClientRegistry.registerKeyBinding(KEY_STORE);
    }
}
