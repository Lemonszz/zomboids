package party.lemons.zomboids.block;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 6/11/2018.
 */
public class TileEntityCrate extends TileEntity
{
    private ItemStack stack = ItemStack.EMPTY;

    public TileEntityCrate()
    {

    }

    public ItemStack getStack()
    {
        return stack;
    }

    public void setStack(@Nonnull ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        stack = new ItemStack(compound.getCompoundTag("item"));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("item", stack.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(compound);
    }

    public NBTTagCompound saveToNbt(NBTTagCompound compound)
    {
        System.out.println(stack);
        System.out.println(stack);
        System.out.println(stack);
        compound.setTag("item", stack.writeToNBT(new NBTTagCompound()));
        return compound;
    }
}
