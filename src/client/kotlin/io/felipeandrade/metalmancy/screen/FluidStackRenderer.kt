package io.felipeandrade.metalmancy.screen

import com.google.common.base.Preconditions
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.texture.Sprite
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.registry.Registries
import net.minecraft.screen.PlayerScreenHandler
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import java.text.NumberFormat


// CREDIT: https://github.com/mezz/JustEnoughItems by mezz (Forge Version)
// HIGHLY EDITED VERSION FOR FABRIC by Kaupenjoe
// Under MIT-License: https://github.com/mezz/JustEnoughItems/blob/1.18/LICENSE.txt
class FluidStackRenderer(
    val width: Int = 16,
    val height: Int = 16,
    val capacity: Long = 20 * FluidConstants.BUCKET,
    val tooltipMode: TooltipMode = TooltipMode.SHOW_AMOUNT_AND_CAPACITY,
) {

    val RATE = 1000

    enum class TooltipMode {
        SHOW_AMOUNT,
        SHOW_AMOUNT_AND_CAPACITY,
        ITEM_LIST
    }

    init {
        Preconditions.checkArgument(capacity > 0, "capacity must be > 0")
        Preconditions.checkArgument(width > 0, "width must be > 0")
        Preconditions.checkArgument(height > 0, "height must be > 0")
    }

    /*
    * METHOD FROM https://github.com/TechReborn/TechReborn
    * UNDER MIT LICENSE: https://github.com/TechReborn/TechReborn/blob/1.19/LICENSE.md
    */
    fun drawFluid(
        drawContext: DrawContext,
        fluid: FluidVariant,
        amount: Long,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        maxCapacity: Long
    ) {
        var y = y
        if (fluid.fluid === Fluids.EMPTY) return

        RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE)
        y += height
        val sprite: Sprite = FluidVariantRendering.getSprite(fluid)!!
        val color: Int = FluidVariantRendering.getColor(fluid)
        val drawHeight = (amount / (maxCapacity * 1f) * height).toInt()
        val iconHeight: Int = sprite.contents.height
        var offsetHeight = drawHeight
        RenderSystem.setShaderColor(
            (color shr 16 and 255) / 255.0f,
            (color shr 8 and 255).toFloat() / 255.0f,
            (color and 255).toFloat() / 255.0f,
            1f
        )
        var iteration = 0
        while (offsetHeight != 0) {
            val curHeight = if (offsetHeight < iconHeight) offsetHeight else iconHeight
            drawContext.drawSprite(
                x,
                y - offsetHeight,
                0,
                width,
                curHeight,
                sprite
            )

            offsetHeight -= curHeight
            iteration++
            if (iteration > 50) {
                break
            }
        }
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
        RenderSystem.setShaderTexture(
            0, FluidRenderHandlerRegistry.INSTANCE.get(fluid.fluid)
                .getFluidSprites(
                    MinecraftClient.getInstance().world,
                    null,
                    fluid.fluid.defaultState
                ).get(0).atlasId
        )
    }

    fun getTooltip(fluid: Fluid, amount: Long): List<Text> {
        val tooltip: MutableList<Text> = ArrayList()
        val displayName = Text.translatable("block." + Registries.FLUID.getId(fluid).toTranslationKey())
        tooltip.add(displayName)
        if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
            val amountString = Text.translatable(
                "tooltip.metalmancy.liquid.amount.with.capacity",
                nf.format(amount/RATE),
                nf.format(capacity/RATE)
            )
            tooltip.add(amountString.fillStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
        } else if (tooltipMode == TooltipMode.SHOW_AMOUNT) {
            val amountString = Text.translatable("tooltip.metalmancy.liquid.amount", nf.format(amount/RATE))
            tooltip.add(amountString.fillStyle(Style.EMPTY.withColor(Formatting.DARK_GRAY)))
        }
        return tooltip
    }

    companion object {
        private val nf = NumberFormat.getIntegerInstance()
    }
}