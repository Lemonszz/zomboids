package party.lemons.zomboids.store;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.zomboids.Zomboids;

/**
 * Created by Sam on 5/11/2018.
 */
public class MessageOpenStore implements IMessage
{
    public int page;

    public MessageOpenStore(){}

    public MessageOpenStore(int page)
    {
        this.page = page;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.page = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(page);
    }

    public static class Handler implements IMessageHandler<MessageOpenStore, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageOpenStore message, final MessageContext ctx)
        {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            final WorldServer world = player.getServerWorld();

            world.addScheduledTask(() -> player.openGui(Zomboids.INSTANCE, message.page, world, 0, 0, 0));
            return null;
        }
    }

}
