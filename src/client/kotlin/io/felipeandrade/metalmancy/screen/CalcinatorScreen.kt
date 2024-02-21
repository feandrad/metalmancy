package io.felipeandrade.metalmancy.screen

import com.mojang.blaze3d.systems.RenderSystem
import io.felipeandrade.metalmancy.Metalmancy.MOD_ID
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.*


class CalcinatorScreen(
    handler: CalcinatorScreenHandler,
    inventory: PlayerInventory,
    title: Text
) : HandledScreen<CalcinatorScreenHandler>(handler, inventory, title) {


    private val fluidRender = FluidStackRenderer(9, 52)

    companion object {
        private val TEXTURE = Identifier(MOD_ID, "textures/gui/calcinator_gui.png")
    }

    override fun drawBackground(context: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader { GameRenderer.getPositionTexProgram() }
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        val x = (width - backgroundWidth) / 2
        val y = (height - backgroundHeight) / 2
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight)

        if (handler.isBurning()) {
            val h = handler.getBurnProgress()
            context.drawTexture(TEXTURE, x + 26, y + 37, 176, 0, 14, h)
        }

        if (handler.isCrafting()) {
            val w = handler.getCraftProgress()
            context.drawTexture(TEXTURE, x + 49, y + 35, 176, 16, w, 16)
        }

        fluidRender.drawFluid(
            context,
            handler.fluid,
            handler.fluidAmount,
            x + 119,
            y + 18,
            9,
            51,
            handler.getFluidCapacity()
        )
    }

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(context, mouseX, mouseY, delta)
        super.render(context, mouseX, mouseY, delta)
        drawMouseoverTooltip(context, mouseX, mouseY)
    }

    override fun drawForeground(context: DrawContext, mouseX: Int, mouseY: Int) {
        super.drawForeground(context, mouseX, mouseY)
        val offsetX = (width - backgroundWidth) / 2
        val offsetY = (height - backgroundHeight) / 2
        // 119, 17 , 127, 68 Exp bar

        if (isMouseOver(mouseX, mouseY, x+119, y+17, fluidRender.width, fluidRender.height)) {
            context.drawTooltip(
                textRenderer,
                fluidRender.getTooltip(handler.fluid.fluid, handler.fluidAmount),
                Optional.empty(),
                mouseX - offsetX,
                mouseY - offsetY
            )
        }
    }
}

fun isMouseOver(mouseX: Int, mouseY: Int, x: Int, y: Int, sizeX: Int, sizeY: Int): Boolean {
    return mouseX >= x && mouseX <= x + sizeX && mouseY >= y && mouseY <= y + sizeY
}