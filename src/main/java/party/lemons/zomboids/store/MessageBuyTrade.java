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
public class MessageBuyTrade implements IMessage
{
    public int index;

    public MessageBuyTrade(){}

    public MessageBuyTrade(int index)
    {
        this.index = index;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.index = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(index);
    }

    public static class Handler implements IMessageHandler<MessageBuyTrade, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageBuyTrade message, final MessageContext ctx)
        {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            final WorldServer world = player.getServerWorld();

            world.addScheduledTask(() ->{
                Trades.StoreTrade trade = Trades.getTrades().get(message.index);
                if(trade.canPurchase(player))
                {
                    trade.removeItems(player);
                    Zomboids.NETWORK.sendTo(new MessageCloseGui(), player);

                    trade.spawnBox(player);
                }

                });
            return null;
        }
    }

}
