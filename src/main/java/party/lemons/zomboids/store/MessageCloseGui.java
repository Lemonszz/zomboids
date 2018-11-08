package party.lemons.zomboids.store;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Sam on 5/11/2018.
 */
public class MessageCloseGui implements IMessage
{
    public MessageCloseGui(){}


    @Override
    public void fromBytes(ByteBuf buf){
    }

    @Override
    public void toBytes(ByteBuf buf)
    {

    }

    public static class Handler implements IMessageHandler<MessageCloseGui, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageCloseGui message, final MessageContext ctx)
        {
            Minecraft.getMinecraft().displayGuiScreen(null);
            return null;
        }
    }

}
