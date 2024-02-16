package io.felipeandrade.metalmancy.datagen

import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Model
import net.minecraft.data.client.ModelIds
import net.minecraft.data.client.TextureMap
import net.minecraft.item.Item

class ModModels {
    companion object {




//        val TOOL = Model(
//            Optional.of(Identifier("minecraft", "item/handheld")),
//            Optional.empty(),
//            ModTextureKeys.HANDLE, TextureKey.LAYER0
//        )

        fun initialize() {}
    }
}

fun ItemModelGenerator.registerTool(item: Item, model: Model) {
    model.upload(
        ModelIds.getItemModelId(item),
        TextureMap()
//            .put(ModTextureKeys.HANDLE, Identifier.of(MOD_ID, "item/${ModTextureKeys.HANDLE}"))
            .put(ModTextureKeys.TOOL, ModelIds.getItemModelId(item)),
        this.writer
    )
}