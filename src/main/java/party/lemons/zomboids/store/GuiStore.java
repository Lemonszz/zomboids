package party.lemons.zomboids.store;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import party.lemons.zomboids.Zomboids;
import party.lemons.zomboids.ZomboidsClient;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sam on 5/11/2018.
 */
public class GuiStore extends GuiContainer
{
    private ContainerStore store;

    public GuiStore(ContainerStore store)
    {
        super(store);
        this.store = store;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        addButton(new GuiButtonExt(0, guiLeft + (xSize - 30), guiTop - 5, 20, 20, "->"));
        addButton(new GuiButtonExt(1, guiLeft + 10, guiTop - 5, 20, 20, "<-"));

        List<Trades.StoreTrade> trades = Trades.getTrades();

        int page = ZomboidsClient.LAST_PAGE;
        int PER_PAGE = 6;
        if(page * PER_PAGE > trades.size())
        {
            page = 0;
            ZomboidsClient.LAST_PAGE = page;
        }

        if(page < 0)
        {
            page = trades.size() / PER_PAGE;
            ZomboidsClient.LAST_PAGE = page;
        }

        int startIndex = page * PER_PAGE;


        int ind = 0;

        for(int i = startIndex; i < Math.min(startIndex + PER_PAGE, trades.size()); i++)
        {
            int yPos = guiTop + 20 + (ind * 20);
            addButton(new ConfirmButton(2, i, guiLeft + xSize - 20, yPos));

            ind++;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                ZomboidsClient.LAST_PAGE++;
                ZomboidsClient.sendStoreMessage();
                break;
            case 1:
                ZomboidsClient.LAST_PAGE--;
                ZomboidsClient.sendStoreMessage();
                break;
            case 2:
                Zomboids.NETWORK.sendToServer(new MessageBuyTrade(((ConfirmButton)button).storeIndex));
                break;
        }

        super.actionPerformed(button);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        for(Slot slot : store.inventorySlots)
        {
            if(slot instanceof ContainerStore.SlotStoreTradeCost)
            {
                ((ContainerStore.SlotStoreTradeCost) slot).doUpdate(partialTicks);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        mc.renderEngine.bindTexture(BG);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    private static final ResourceLocation BEACON_GUI_TEXTURES = new ResourceLocation("textures/gui/container/beacon.png");
    private static final ResourceLocation BG = new ResourceLocation(Zomboids.MODID, "textures/store.png");
    static class Button extends GuiButton
    {
        private final ResourceLocation iconTexture;
        private final int iconX;
        private final int iconY;
        private boolean selected;

        protected Button(int buttonId, int x, int y, ResourceLocation iconTextureIn, int iconXIn, int iconYIn)
        {
            super(buttonId, x, y, 17, 17, "");
            this.iconTexture = iconTextureIn;
            this.iconX = iconXIn;
            this.iconY = iconYIn;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
        {
            if (this.visible)
            {
                mc.getTextureManager().bindTexture(BEACON_GUI_TEXTURES);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                int i = 219;
                int j = 0;

                if(this.hovered)
                {
                    GlStateManager.color(0.258823529F, 0.525490196F, 0.956862745F, 1F);

                }
                this.drawTexturedModalRect(this.x, this.y, j, 219, this.width, this.height);
                GlStateManager.color(1F, 1F, 1F, 1F);

                if (!BEACON_GUI_TEXTURES.equals(this.iconTexture))
                {
                    mc.getTextureManager().bindTexture(this.iconTexture);
                }

                this.drawTexturedModalRect(this.x, this.y - 1, this.iconX, this.iconY, 18, 18);
            }
        }

        public boolean isSelected()
        {
            return this.selected;
        }

        public void setSelected(boolean selectedIn)
        {
            this.selected = selectedIn;
        }
    }


    class ConfirmButton extends Button
    {
        public int storeIndex;

        public ConfirmButton(int buttonId, int storeIndex, int x, int y)
        {
            super(buttonId, x, y, BEACON_GUI_TEXTURES, 90, 220);

            this.storeIndex = storeIndex;
        }

        public void drawButtonForegroundLayer(int mouseX, int mouseY)
        {
            GuiStore.this.drawHoveringText(I18n.format("gui.done"), mouseX, mouseY);
        }
    }

}
