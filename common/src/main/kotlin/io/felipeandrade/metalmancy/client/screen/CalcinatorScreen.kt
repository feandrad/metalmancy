package io.felipeandrade.metalmancy.client.screen

import io.felipeandrade.metalmancy.Metalmancy
import io.felipeandrade.metalmancy.menu.CalcinatorMenu
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class CalcinatorScreen(menu: CalcinatorMenu, inventory: Inventory, title: Component) : AbstractContainerScreen<CalcinatorMenu>(menu, inventory, title) {
    private val TEXTURE = ResourceLocation.fromNamespaceAndPath(Metalmancy.MOD_ID, "textures/gui/calcinator_gui.png")

    override fun init() {
        super.init()
        // TODO: Add widgets if needed
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBackground(graphics, mouseX, mouseY, partialTick)
        super.render(graphics, mouseX, mouseY, partialTick)
        renderTooltip(graphics, mouseX, mouseY)
    }

    override fun renderBg(graphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val x = (width - imageWidth) / 2
        val y = (height - imageHeight) / 2
        graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight)

        if (menu.isBurning()) {
            val h = menu.getBurnProgress()
            graphics.blit(TEXTURE, x + 26, y + 37 + 14 - h, 176, 14 - h, 14, h + 1)
        }

        if (menu.isCrafting()) {
            val w = menu.getCraftProgress()
            graphics.blit(TEXTURE, x + 49, y + 35, 176, 16, w + 1, 16)
        }
        
        // TODO: Render Fluid
    }
}
