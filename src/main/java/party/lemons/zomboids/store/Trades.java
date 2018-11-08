package party.lemons.zomboids.store;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import party.lemons.zomboids.Zomboids;
import party.lemons.zomboids.block.TileEntityCrate;
import party.lemons.zomboids.block.ZomboidBlocks;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 5/11/2018.
 */

@ZenRegister
@ZenClass("mods.Zomboids.Store")
public class Trades
{
    public static List<StoreTrade> trades = new ArrayList<>();

    @ZenMethod
    public static void clear()
    {
        trades.clear();
    }

    @ZenMethod
    public static void addTrade(IItemStack result, IIngredient... inputs)
    {
        StoreTrade trade = new StoreTrade(CraftTweakerMC.getItemStack(result), getIngredients(inputs));

        trades.add(trade);
    }

    private static Ingredient[] getIngredients(IIngredient... inputs)
    {
        Ingredient[] ingreds = new Ingredient[inputs.length];
        for(int i = 0; i < inputs.length; i++)
            ingreds[i] = CraftTweakerMC.getIngredient(inputs[i]);

        return ingreds;
    }


    public static List<StoreTrade> getTrades()
    {
        return trades;
    }

    public static class StoreTrade
    {
        public ItemStack result;
        public Ingredient[] cost;

        public StoreTrade(ItemStack result, Ingredient... cost)
        {
            this.result = result;
            this.cost = cost;
        }

        public boolean canPurchase(EntityPlayer player)
        {
            for(Ingredient ingredient : cost)
            {
                boolean found = false;
                for (ItemStack stack : player.inventory.mainInventory)
                {
                    if(ingredient.apply(stack) && stack.getCount() >= ingredient.getMatchingStacks()[0].getCount())
                    {
                        found = true;
                        break;
                    }
                }

                if(!found)
                    return false;
            }
            return true;
        }

        public void removeItems(EntityPlayer player)
        {
            for(Ingredient ingredient : cost)
            {
                for (ItemStack stack : player.inventory.mainInventory)
                {
                    if (ingredient.apply(stack)  && stack.getCount() >= ingredient.getMatchingStacks()[0].getCount())
                    {
                        stack.shrink(ingredient.getMatchingStacks()[0].getCount());
                        break;
                    }
                }
            }

            player.inventoryContainer.detectAndSendChanges();
        }

        public void spawnBox(EntityPlayer player)
        {
            BlockPos playerPos = player.getPosition();
            float xPos = 0.5F + playerPos.getX() + (-player.getRNG().nextInt(5) + player.getRNG().nextInt(10));
            float zPos = 0.5F + playerPos.getZ() + (-player.getRNG().nextInt(5) + player.getRNG().nextInt(10));


            EntityFallingBlock block = new EntityFallingBlock(player.world, xPos, player.world.getHeight() + player.getRNG().nextInt(25), zPos, ZomboidBlocks.CRATE.getDefaultState());

            TileEntityCrate crateTe = new TileEntityCrate();
            crateTe.setStack(result.copy());
            block.tileEntityData = crateTe.saveToNbt(new NBTTagCompound());
            player.world.spawnEntity(block);
            block.fallTime = 1;
        }
    }

}
