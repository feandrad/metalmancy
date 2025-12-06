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
        
        // Try blit(res, x, y, w, h, u, v, texW, texH) based on candidate analysis (2 ints, 2 ints, 4 floats)
        // graphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight) -> OLD
        // Candidate: blit(res, i, j, k, l, f, g, h, m)
        // i,j = x,y (int)
        // k,l = width, height (int)
        // f,g = u,v (float)
        // h,m = texW, texH (float)
        
        graphics.blit(TEXTURE, x, y, imageWidth, imageHeight, 0f, 0f, 256f, 256f)

        if (menu.isBurning()) {
            val h = menu.getBurnProgress()
            // graphics.blit(TEXTURE, x + 26, y + 37 + 14 - h, 176, 14 - h, 14, h + 1)
            // u=176, v=14-h, w=14, h=h+1
            graphics.blit(TEXTURE, x + 26, y + 37 + 14 - h, 14, h + 1, 176f, (14 - h).toFloat(), 256f, 256f)
        }

        if (menu.isCrafting()) {
            val w = menu.getCraftProgress()
            // graphics.blit(TEXTURE, x + 49, y + 35, 176, 16, w + 1, 16)
            // u=176, v=16, w=w+1, h=16
            graphics.blit(TEXTURE, x + 49, y + 35, w + 1, 16, 176f, 16f, 256f, 256f)
        }
        
        // TODO: Render Fluid
    }
}
