package io.felipeandrade.metalmancy.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.felipeandrade.metalmancy.registry.ModBlocks
import net.minecraft.core.HolderLookup
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class CalcinatingRecipe(
    val ingredient: Ingredient,
    val output: ItemStack,
    val essence: Long
) : Recipe<RecipeInput> {

    override fun matches(input: RecipeInput, level: Level): Boolean {
        return ingredient.test(input.getItem(0))
    }

    override fun assemble(input: RecipeInput, registries: HolderLookup.Provider): ItemStack {
        return output.copy()
    }

    fun canCraftInDimensions(width: Int, height: Int): Boolean = true

    fun getResultItem(registries: HolderLookup.Provider): ItemStack = output

    fun getToastSymbol(): ItemStack = ItemStack(ModBlocks.CALCINATOR.get())
    
    override fun isSpecial(): Boolean = true
    
    override fun recipeBookCategory(): RecipeBookCategory? = null
    
    override fun placementInfo(): PlacementInfo? = null

    override fun getSerializer(): RecipeSerializer<*> = Serializer.INSTANCE as RecipeSerializer<CalcinatingRecipe>

    override fun getType(): RecipeType<*> = Type.INSTANCE as RecipeType<CalcinatingRecipe>

    class Type : RecipeType<CalcinatingRecipe> {
        companion object {
            val INSTANCE = Type()
            const val ID = "calcinating"
        }
    }

    class Serializer : RecipeSerializer<CalcinatingRecipe> {
        companion object {
            val INSTANCE = Serializer()
            val ID = "calcinating"

            val CODEC: MapCodec<CalcinatingRecipe> = RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    Ingredient.CODEC.fieldOf("ingredient").forGetter { it.ingredient },
                    ItemStack.CODEC.fieldOf("output").forGetter { it.output },
                    Codec.LONG.fieldOf("essence").forGetter { it.essence }
                ).apply(instance, ::CalcinatingRecipe)
            }

            val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, CalcinatingRecipe> = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, CalcinatingRecipe::ingredient,
                ItemStack.STREAM_CODEC, CalcinatingRecipe::output,
                StreamCodec.of({ buf, v -> buf.writeLong(v) }, { buf -> buf.readLong() }), CalcinatingRecipe::essence,
                ::CalcinatingRecipe
            )
        }

        override fun codec(): MapCodec<CalcinatingRecipe> = CODEC

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, CalcinatingRecipe> = STREAM_CODEC
    }
}
